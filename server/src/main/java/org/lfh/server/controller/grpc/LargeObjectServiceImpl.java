package org.lfh.server.controller.grpc;

import com.lfh.*;
import io.grpc.stub.StreamObserver;

public class LargeObjectServiceImpl extends LargeServiceGrpc.LargeServiceImplBase {

    //int i = 0;

    @Override
    public void unaryLargeCall(LargeRequest request, StreamObserver<LargeResponse> responseObserver) {
        //System.out.println(i);
        //i++;
        responseObserver.onNext(largeResponse());
        responseObserver.onCompleted();
    }


    private LargeResponse largeResponse(){
            return LargeResponse.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).addListLargeNestedResponse(getLargeNested()).addListOtherLargeNestedResponse(getOtherLargeNested()).addListLargeNestedResponse(getLargeNested()).addListOtherLargeNestedResponse(getOtherLargeNested()).addListLargeNestedResponse(getLargeNested()).addListOtherLargeNestedResponse(getOtherLargeNested()).build();
    }

    private OtherLargeNested getOtherLargeNested() {
        return OtherLargeNested.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).setFieldI(12341.12).setFieldJ(1234.123).setFieldK(getLong()).setFieldL(getLong()).setFieldM("test").setFieldN("TestThis").setFieldO(true).setFieldP(false).setFieldQ(getFloat()).setFieldR(getFloat()).setFieldS(1235.12).setFieldT(1332.2).build();
    }

    private LargeNested getLargeNested() {
        return LargeNested.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).setFieldI(12341.12).setFieldJ(1234.123).setFieldK(getLong()).setFieldL(getLong()).setFieldM("test").setFieldN("TestThis").setFieldO(true).setFieldP(false).setFieldQ(getFloat()).setFieldR(getFloat()).setFieldS(1235.12).setFieldT(1332.2).build();
    }

    private float getFloat() {
        return Float.parseFloat("1234.1");
    }

    private long getLong() {
        return Long.parseLong("1234");
    }
}
