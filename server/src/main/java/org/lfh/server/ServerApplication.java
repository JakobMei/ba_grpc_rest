package org.lfh.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
//import io.grpc.protobuf.services.ProtoReflectionService;
import org.lfh.server.controller.grpc.LargeObjectServiceImpl;
import org.lfh.server.controller.grpc.PingPongServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ServerApplication {

    /*@Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }*/



    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(3000)
                .addService(new PingPongServiceImpl())
                .addService(new LargeObjectServiceImpl())
                //.addService(ProtoReflectionService.newInstance()) wanted to add server reflection but apparently that isnt supported on m1 macs lol
                .build();

        server.start();
        System.out.println("GRPC Server started - awaiting requests or termination");
        SpringApplication.run(ServerApplication.class, args);
        server.awaitTermination();


    }
}
