package me.lordierclaw.todoserver.controller.socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.inject.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@ServerEndpoint("/livedata")
public class LiveDataWebSocket {

    private final LiveDataSessionHandler liveDataSessionHandler = LiveDataSessionHandler.getInstance();

    @OnOpen
    public void open(Session session) {
        liveDataSessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        liveDataSessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        Logger.getLogger(LiveDataWebSocket.class.getName()).log(Level.SEVERE, null, error);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        Gson gson = new Gson();
        JsonObject jsonMessage = gson.fromJson(message, JsonObject.class);
        String action = jsonMessage.get("action").getAsString();
        String table = jsonMessage.get("table").getAsString();
        if (action.equals("track")) {
            liveDataSessionHandler.sessionAddTable(session, table);
        } else if (action.equals("remove")) {
            liveDataSessionHandler.sessionRemoveTable(session, table);
        }
        session.getAsyncRemote().sendText("Server says hello to " + session.getId());
    }
}
