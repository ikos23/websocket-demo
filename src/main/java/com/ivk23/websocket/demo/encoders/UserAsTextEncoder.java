package com.ivk23.websocket.demo.encoders;

import com.ivk23.websocket.demo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * The Java API for WebSocket provides support for converting
 * between WebSocket messages and custom Java types using
 * encoders and decoders. An encoder takes a Java object and
 * produces a representation that can be transmitted as a
 * WebSocket representations. A decoder performs the reverse
 * function.
 *
 * Created by ivk23 on 2016-07-15.
 */
public class UserAsTextEncoder implements Encoder.Text<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAsTextEncoder.class);

    @Override
    public String encode(User user) throws EncodeException {
        //return User as JSON
        LOGGER.debug("[IVK23-WS] >>> " + user.toString() + " encoding...");
        return "{ \"firstName\":\"" + user.getFirstName() + "\","
                 + "\"lastName\":\"" + user.getLastName() + "\","
                 + "\"email\":\"" + user.getEmail() + "\" }";
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        LOGGER.debug("[IVK23-WS] UserAsTextEncoder.init has been called !");
    }

    @Override
    public void destroy() {
        LOGGER.debug("[IVK23-WS] UserAsTextEncoder.destroy has been called !");
    }
}
