package sockets;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket")
public class ChatWebSocketServer {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private static final ConcurrentHashMap<String, Session> userSessions = new ConcurrentHashMap<>(); // ID пользователя -> Session

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("New connection opened: " + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        userSessions.entrySet().removeIf(entry -> entry.getValue() == session); //удаление из userSessions
        System.out.println("Connection closed: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        //Обработка сообщений от клиента (в данном примере, не используется)
    }

    @OnError
    public void onError(Throwable throwable) {
        System.err.println("Error: " + throwable.getMessage());
    }

    public static void sendMessageToAll(String message) throws IOException {
        for (Session session : sessions) {
            session.getBasicRemote().sendText(message);
        }
    }

    // Метод для отправки группового сообщения
    public static void sendGroupMessage(String message) throws IOException{
        for (Session session : sessions) {
            session.getBasicRemote().sendText(message);
        }
    }


    public static void registerUser(String userId, Session session){
        userSessions.put(userId, session);
    }

    public static void unregisterUser(String userId){
        userSessions.remove(userId);
    }
}