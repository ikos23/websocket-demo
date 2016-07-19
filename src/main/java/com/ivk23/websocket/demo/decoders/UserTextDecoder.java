package com.ivk23.websocket.demo.decoders;

import com.ivk23.websocket.demo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Decoder is used to convert WebSocket message to Java object.
 *
 *   Decoder.Text<T> - for text messages
 *   Decoder.Binary<T> - for binary messages
 *
 * Created by ivk23 on 2016-07-15.
 */
public class UserTextDecoder implements Decoder.Text<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTextDecoder.class);

    @Override
    public User decode(String msg) throws DecodeException {

        if (willDecode(msg)) {
            LOGGER.debug("[IVK23-WS] Message >>> " + msg + " <<< decoding...");
            String[] data = msg.split(",");
            return new User(data[0].split(":")[1],
                            data[1].split(":")[1],
                            data[2].split(":")[1]);
        }

        throw new DecodeException(msg, "Invalid msg format. Decode Error !");
    }

    /**
     * Determine if the message can be converted into
     * a User object.
     * @param message The received message.
     * @return <tt>true</tt> if <code>message</code> can be decoded.
     */
    @Override
    public boolean willDecode(String message) {
        return message.contains("firstName")
                && message.contains("lastName")
                && message.contains("email");
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        LOGGER.debug("[IVK23-WS] UserTextDecoder.init has been called !");
    }

    @Override
    public void destroy() {
        LOGGER.debug("[IVK23-WS] UserTextDecoder.destroy has been called !");
    }
}
