package com.ivk23.websocket.demo.endpoints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class represents WebSocket endpoint.
 *
 * Client uses endpoint's URI to connect to the server.
 *
 * The WebSocket protocol is symmetrical after the connection
 * has been established; the client and the server can send
 * messages to each other at any time the connection is open
 * and they can close the connection at any time.
 *
 * The annotated endpoint (this class is an example :)) is
 * deployed automatically with the application to the relative
 * path defined in the ServerEndpoint annotation.
 *
 * The container creates an instance of the endpoint class
 * for every connection, you can define and use instance
 * variables to store client information.
 *
 * Created by ivk23 on 2016-07-15.
 */
@ServerEndpoint("/mychat")
public class MyChatEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyChatEndpoint.class);
    /*
    * This queue is used to store all sessions for all connected peers.
    */
    private static final Queue<Session> sessionsQueue = new ConcurrentLinkedQueue<>();

    /**
     * [Connection opened] WebSocket Endpoint lifecycle event handler.
     *
     * Another possible signature is method(Session s, EndpointConfig c);
     *
     * @param session
     */
    @OnOpen
    public void openConnection(Session session) {
        LOGGER.debug("[IVK23-WS] New connection to /mychat WebSocket opened :: "
                + session.getId());
        sessionsQueue.add(session);
    }

    /**
     * [Connection closed] WebSocket Endpoint lifecycle event handler.
     *
     * @param session
     * @param reason
     */
    @OnClose
    public void closedConnection(Session session, CloseReason reason) {
        sessionsQueue.remove(session);
        LOGGER.debug("[IVK23-WS] Connection closed for #"
                + session.getId() + "; reason " + reason.getReasonPhrase());
    }

    /**
     * [Connection error] WebSocket Endpoint lifecycle event handler.
     *
     * @param session
     * @param tr
     */
    @OnError
    public void error(Session session, Throwable tr) {
        sessionsQueue.remove(session);
        LOGGER.debug("[IVK23-WS] ERROR ERROR ERROR !");
        tr.printStackTrace();
    }

    /**
     * [Message received] WebSocket Endpoint lifecycle event handler.
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            for (Session sessionFromQueue : sessionsQueue) {
                if (sessionFromQueue.isOpen()) {
                    sessionFromQueue.getBasicRemote().sendText(message);
                    LOGGER.debug("[IVK23-WS] Message has been sent to #"
                            + sessionFromQueue.getId());
                }
            }
        } catch (IOException e) {
            LOGGER.debug("[IVK23-WS] ERROR ERROR ERROR from onMessage() !");
            e.printStackTrace();
        }
    }
}
