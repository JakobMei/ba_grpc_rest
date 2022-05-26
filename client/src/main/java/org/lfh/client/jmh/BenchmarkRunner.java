package org.lfh.client.jmh;

import com.google.gson.Gson;
import com.lfh.*;
import com.squareup.okhttp.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.lfh.client.data.LargeObjectPojo;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchmarkRunner {
    //all the channels for different types of benchmark
    //FYI One Channel is equal to One TCP-Connection
    ManagedChannel channel;
    ManagedChannel channelNotWarmedUpLargeObject;
    ManagedChannel channelNotWarmedUpPingPong;

    //Objects for small Objects via gRPC
    //FYI PingPong is equal to small Object
    PingPongServiceGrpc.PingPongServiceBlockingStub pingPongStub;
    PingRequest pingRequest;
    PingPongServiceGrpc.PingPongServiceBlockingStub pingPongStubNotWarmedUp;

    //Objects for large Objects via gRPC
    LargeServiceGrpc.LargeServiceBlockingStub largeObjectStub;
    LargeServiceGrpc.LargeServiceBlockingStub largeObjectStubNotWarmedUp;
    LargeRequest largeRequest;

    String smallRESTRequestPayload;
    String largeRESTRequestPayload;
    RequestBody smallRESTBody;
    RequestBody largeRESTBody;

    OkHttpClient httpClient = new OkHttpClient();

    // not used
    // Gson gson = new Gson();

    Request smallHttpRequest;
    Request largeHttpRequest;

    Response smallRestResponse;
    Response largeRestResponse;

    Call smallRestCall;
    Call largeRestCall;


    @Benchmark
    public void benchmarkRESTSmallObject(BenchmarkRunner runner, Blackhole blackhole) throws IOException {
        runner.smallRestCall = runner.httpClient.newCall(runner.smallHttpRequest);
        blackhole.consume(runner.smallRestCall.execute());
    }
    /* no difference in Performance
    @Benchmark
    public void benchmarkRESTSmallObjectWithJsonParse(BenchmarkRunner runner, Blackhole blackhole) throws IOException {
        runner.smallRestCall = runner.httpClient.newCall(runner.smallHttpRequest);
        smallRestResponse = runner.smallRestCall.execute();
        blackhole.consume(smallRestResponse.body().toString());
    }*/
    /* no difference in Performance
    @Benchmark
    public void benchmarkRESTLargeObjectWithJsonParse(BenchmarkRunner runner, Blackhole blackhole) throws IOException {
        runner.largeRestCall = runner.httpClient.newCall(runner.largeHttpRequest);
        largeRestResponse = runner.largeRestCall.execute();
        blackhole.consume(largeRestResponse.body().toString());
    }*/

    @Benchmark
    public void benchmarkRESTLargeObject(BenchmarkRunner runner, Blackhole blackhole) throws IOException{
        runner.largeRestCall = runner.httpClient.newCall(runner.largeHttpRequest);
        blackhole.consume(runner.largeRestCall.execute());
    }

    @Benchmark
    public void benchmarkGRPCSmallObjectAllWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        blackhole.consume(runner.pingPongStub.unaryPingPong(pingRequest));
    }

    @Benchmark
    public void benchmarkGRPCSmallObjectJustChannelWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        pingPongStubNotWarmedUp = PingPongServiceGrpc.newBlockingStub(channel);
        blackhole.consume(runner.pingPongStubNotWarmedUp.unaryPingPong(pingRequest));
    }

    @Benchmark
    public void benchmarkGRPCSmallObjectNothingWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        channelNotWarmedUpPingPong = ManagedChannelBuilder
                .forAddress("localhost",3000)
                .usePlaintext()
                .build();
        pingPongStubNotWarmedUp = PingPongServiceGrpc.newBlockingStub(channelNotWarmedUpPingPong);
        blackhole.consume(runner.pingPongStubNotWarmedUp.unaryPingPong(pingRequest));
        //TCP Connection needs to be closed after each Invocation
        channelNotWarmedUpPingPong.shutdown();
    }
    @Benchmark
    public void benchmarkGRPCLargeObjectAllWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        blackhole.consume(runner.largeObjectStub.unaryLargeCall(largeRequest));
    }
    @Benchmark
    public void benchmarkGRPCLargeObjectJustChannelWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        largeObjectStubNotWarmedUp = LargeServiceGrpc.newBlockingStub(channel);
        blackhole.consume(runner.largeObjectStubNotWarmedUp.unaryLargeCall(largeRequest));
    }
    @Benchmark
    public void benchmarkGRPCLargeObjectNothingWarmedUp(BenchmarkRunner runner, Blackhole blackhole){
        channelNotWarmedUpLargeObject = ManagedChannelBuilder
                .forAddress("localhost",3000)
                .usePlaintext()
                .build();
        largeObjectStubNotWarmedUp = LargeServiceGrpc.newBlockingStub(channelNotWarmedUpLargeObject);
        blackhole.consume(runner.largeObjectStubNotWarmedUp.unaryLargeCall(largeRequest));
        //TCP Connection needs to be closed after each Invocation
        channelNotWarmedUpLargeObject.shutdown();
    }


    @Setup(Level.Iteration)
    public void setUpBenchmark() {
        //configure gRPC
        //configure Channel aka. open up TCP Connection
        channel = ManagedChannelBuilder
                .forAddress("localhost",3000)
                .usePlaintext()
                .build();
        //configure PingPongService
        //FYI PingPong is equal to SmallObject
        pingPongStub = PingPongServiceGrpc.newBlockingStub(channel);
        pingRequest = PingRequest.newBuilder().setPing("Hi").build();
        //configure LargeObjects and its Service
        largeObjectStub = LargeServiceGrpc.newBlockingStub(channel);
        largeRequest = getLargeRequest();


        //configure REST consume
        //Small REST Request Build Process
        smallRESTRequestPayload = getSmallRequestPojoJsonString();
        smallRESTBody = RequestBody.create(MediaType.parse("application/json"),smallRESTRequestPayload);
        smallHttpRequest = new Request.Builder().url("http://localhost:8080/rest/pingpong").post(smallRESTBody).build();
        //large REST Request Build Process
        largeRESTRequestPayload = getLargeRequestPojoJsonString();
        largeRESTBody = RequestBody.create(MediaType.parse("application/json"),largeRESTRequestPayload);
        largeHttpRequest = new Request.Builder().url("http://localhost:8080/rest/largeObject").post(largeRESTBody).build();


    }
    @TearDown(Level.Invocation)
    public void coolDownAfterBenchmark(){
        //just to make sure there is nothing left from previous invocations which okhttp is messing with
        if(smallRestCall != null)
            smallRestCall.cancel();
        if(largeRestCall != null)
            largeRestCall.cancel();
    }

    @TearDown(Level.Iteration)
    public void coolDowngRPC(){
        if(!channel.isShutdown())
            channel.shutdown();
    /*    if(!channelNotWarmedUpPingPong.isShutdown())
            channelNotWarmedUpPingPong.shutdown();
        if(!channelNotWarmedUpLargeObject.isShutdown())
            channelNotWarmedUpLargeObject.shutdown();*/
    }







    //Helper Methods to create the Request of LargeObject
    private static LargeRequest getLargeRequest() {
        return LargeRequest.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).addListLargeNestedRequest(getLargeNested()).addListOtherLargeNestedRequest(getOtherLargeNested()).build();
    }

    private static OtherLargeNested getOtherLargeNested() {
        return OtherLargeNested.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).setFieldI(12341.12).setFieldJ(1234.123).setFieldK(getLong()).setFieldL(getLong()).setFieldM("test").setFieldN("TestThis").setFieldO(true).setFieldP(false).setFieldQ(getFloat()).setFieldR(getFloat()).setFieldS(1235.12).setFieldT(1332.2).build();
    }

    private static LargeNested getLargeNested() {
        return LargeNested.newBuilder().setFieldA(getLong()).setFieldB(getLong()).setFieldC("TestTest").setFieldD("TestProto").setFieldE(false).setFieldF(true).setFieldG(getFloat()).setFieldH(getFloat()).setFieldI(12341.12).setFieldJ(1234.123).setFieldK(getLong()).setFieldL(getLong()).setFieldM("test").setFieldN("TestThis").setFieldO(true).setFieldP(false).setFieldQ(getFloat()).setFieldR(getFloat()).setFieldS(1235.12).setFieldT(1332.2).build();
    }
    //DEPRECATED METHOD
    /*
    private static LargeObjectPojo.LargeRequestPojo getLargeRequestPojo(){
        return LargeObjectPojo.LargeRequestPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).list_largeNestedRequest(getLargeNestedPojo()).list_otherLargeNestedRequest(getOtherLargeNestedPojo()).build();
    }*/
    //DEPRECATED METHOD
    /*
    private static List<LargeObjectPojo.OtherLargeNestedPojo> getOtherLargeNestedPojo() {
        List<LargeObjectPojo.OtherLargeNestedPojo> oln = new ArrayList<>();
        LargeObjectPojo.OtherLargeNestedPojo obj = LargeObjectPojo.OtherLargeNestedPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).field_i(12341.12).field_j(1234.123).field_k(getLong()).field_l(getLong()).field_m("test").field_n("TestThis").field_o(true).field_p(false).field_q(getFloat()).field_r(getFloat()).field_s(getLong()).field_t(getLong()).build();
        oln.add(obj);
        oln.add(obj);
        oln.add(obj);
        return oln;
    }
    //DEPRECATED METHOD
    private static List<LargeObjectPojo.LargeNestedPojo> getLargeNestedPojo() {
        List<LargeObjectPojo.LargeNestedPojo> ln = new ArrayList<>();
        LargeObjectPojo.LargeNestedPojo obj = LargeObjectPojo.LargeNestedPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).field_i(12341.12).field_j(1234.123).field_k(getLong()).field_l(getLong()).field_m("test").field_n("TestThis").field_o(true).field_p(false).field_q(getFloat()).field_r(getFloat()).field_s(getLong()).field_t(getLong()).build();
        ln.add(obj);
        ln.add(obj);
        ln.add(obj);
        return ln;
    }*/

    private static String getLargeRequestPojoJsonString(){
        return "{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"list_largeNestedRequest\":[{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234},{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234},{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234}],\"list_otherLargeNestedRequest\":[{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234},{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234},{\"field_a\":1234,\"field_b\":1234,\"field_c\":\"TestTest\",\"field_d\":\"TestProto\",\"field_e\":false,\"field_f\":true,\"field_g\":1234.1,\"field_h\":1234.1,\"field_i\":12341.12,\"field_j\":1234.123,\"field_k\":1234,\"field_l\":1234,\"field_m\":\"test\",\"field_n\":\"TestThis\",\"field_o\":true,\"field_p\":false,\"field_q\":1234.1,\"field_r\":1234.1,\"field_s\":1234,\"field_t\":1234}]}";
    }

    private static String getSmallRequestPojoJsonString(){
        return "{\n    \"ping\" : \"Hey\"\n}";
    }


    private static float getFloat() {
        return Float.parseFloat("1234.1");
    }

    private static long getLong() {
        return Long.parseLong("1234");
    }
}
