package com.pathfinder.net;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.pathfinder.Tank;
import com.pathfinder.handler.TankHandler;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.json.JSONException;
import org.json.*;

// TODO: Error handling
public class SocketClientHandler implements Disposable
{
    private static final String TAG = "NET.SOCKET-IO";
    private static final String SERVER_URL = "http://localhost:8080";
    private static final float UPDATE_TIME = 1 / 60f;

    private final Tank player;
    private final TankHandler tankHandler;
    private float timer = 0;

    private Socket socket;

    public SocketClientHandler(Tank player, TankHandler tankHandler)
    {
        this.player = player;
        this.tankHandler = tankHandler;
        connectAndConfigure();
    }

    public void update(float delta)
    {
        timer += delta;
        if(player.hasMovedInRelationToTheServer() && timer >= UPDATE_TIME)
        {
            try
            {
                var data = new JSONObject();
                data.put("x", player.position.x);
                data.put("y", player.position.y);
                socket.emit(SocketEvents.PLAYER_MOVED_EVENT, data);
                player.setLastServerPosition();
                timer = 0;
            }
            catch (JSONException e)
            {
                Gdx.app.error(TAG, "Error sending player position");
            }
        }
    }

    public void emitEvent(String event, Object... args)
    {
        socket.emit(event, args);
    }

    private void connectAndConfigure()
    {
        try
        {
            socket = IO.socket(SERVER_URL).connect();
            configureSocket();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void configureSocket()
    {
        if(socket == null)
        {
            Gdx.app.error(TAG, "Not connected yet to the socket");
            return;
        }

        socket.on(Socket.EVENT_CONNECT, args -> Gdx.app.log(TAG, "Connection made with the server, waiting for the first event"))
                .on(SocketEvents.MY_SOCKET_ID_EVENT, this::handleMySocketId)
                .on(SocketEvents.NEW_PLAYER_EVENT, this::handleNewPlayer)
                .on(SocketEvents.PLAYER_DISCONNECTED_EVENT, this::handlePlayerDisconnected)
                .on(SocketEvents.GET_PLAYERS_EVENT, this::handleGetPlayers)
                .on(SocketEvents.PLAYER_MOVED_EVENT, this::handlePlayerMoved);
    }

    private void handleMySocketId(Object... args)
    {
        var data = (JSONObject) args[0];
        try
        {
            var id = data.getString("id");
            player.id = id;
            Gdx.app.log(TAG, "My player ID: " + id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void handleNewPlayer(Object... args)
    {
        var data = (JSONObject) args[0];
        try
        {
            var id = data.getString("id");
            tankHandler.addTank(id);
            Gdx.app.log(TAG, "New player connected </ ID: " + id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void handlePlayerDisconnected(Object... args)
    {
        var data = (JSONObject) args[0];
        try
        {
            var id = data.getString("id");
            tankHandler.removeTank(id);
            Gdx.app.log(TAG, "Player removed </ ID: " + id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void handleGetPlayers(Object... args)
    {
        var data = (JSONArray) args[0];
        try
        {
            for(int i = 0; i < data.length(); i++)
            {
                var playerData = data.getJSONObject(i);
                var id = playerData.getString("id");
                var x = (float) playerData.getDouble("x");
                var y = (float) playerData.getDouble("y");

                tankHandler.addTank(id, new Vector2(x, y));
            }

            tankHandler.removeTank(player.id);
            tankHandler.checkIfIsServer();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void handlePlayerMoved(Object... args)
    {
        var data = (JSONObject) args[0];
        try
        {
            var id = data.getString("id");
            var x = (float) data.getDouble("x");
            var y = (float) data.getDouble("y");

            tankHandler.setPositionById(id, new Vector2(x, y));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose()
    {
        if(socket != null && socket.connected())
            socket.disconnect();
    }
}
