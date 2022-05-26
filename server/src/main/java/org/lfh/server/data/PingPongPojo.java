package org.lfh.server.data;

import lombok.Data;


public class PingPongPojo {
    @Data
    public static class PingRequestPojo{
        String ping;
    }
    @Data
    public static class PongResponsePojo{
        String pong;
    }
}
