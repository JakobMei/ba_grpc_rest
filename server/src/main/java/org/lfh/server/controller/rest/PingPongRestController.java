package org.lfh.server.controller.rest;

import com.lfh.PongResponse;
import org.lfh.server.data.PingPongPojo;
import org.springframework.web.bind.annotation.*;

@RestController
public class PingPongRestController {

    @RequestMapping("/rest/pingpong")
    PingPongPojo.PongResponsePojo pingPong(@RequestBody PingPongPojo.PingRequestPojo req){
        return getPongResponse();
    }
    private static PingPongPojo.PongResponsePojo getPongResponse(){
        PingPongPojo.PongResponsePojo ret = new PingPongPojo.PongResponsePojo();
        ret.setPong("Ho");
        return ret;
    }
}
