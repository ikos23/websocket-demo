package com.ivk23.websocket.demo.endpoints;

import com.ivk23.websocket.demo.decoders.UserTextDecoder;
import com.ivk23.websocket.demo.encoders.UserAsTextEncoder;
import com.ivk23.websocket.demo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * Simple WebSocket endpoint. Is used to show how
 * encoders/decoders work.
 *
 * Created by ivk23 on 2016-07-15.
 */
@ServerEndpoint(value = "/user",
        encoders = { UserAsTextEncoder.class },
        decoders = { UserTextDecoder.class }
)
public class UserEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @OnMessage
    public void message(Session session, User user) {
        try {
            LOGGER.debug("[IVK23-WS] NEW MESSAGE :: " + user.toString());
            session.getBasicRemote().sendObject(user);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
}
