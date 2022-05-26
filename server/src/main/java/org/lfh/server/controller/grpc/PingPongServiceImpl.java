package org.lfh.server.controller.grpc;

import com.lfh.PingPongServiceGrpc;
import com.lfh.PingRequest;
import com.lfh.PongResponse;
import io.grpc.stub.StreamObserver;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {

    //private int i = 0;

    @Override
    public void unaryPingPong(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        //System.out.println(i);
        PongResponse response = PongResponse.newBuilder().setPong("Hi, worked.").build();
        responseObserver.onNext(response);
        //i++;
        responseObserver.onCompleted();
    }
}
