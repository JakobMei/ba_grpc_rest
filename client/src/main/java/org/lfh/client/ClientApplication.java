package org.lfh.client;


import com.google.gson.Gson;
import com.squareup.okhttp.*;
import org.lfh.client.data.LargeObjectPojo;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ClientApplication {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        //Gson gson = new Gson();
        //System.out.println(gson.toJson(getLargeRequestPojo()));

        //run benchmark
        org.openjdk.jmh.Main.main(args);

        /*
        OkHttpClient client = new OkHttpClient();

        String json = "{\n    \"ping\" : \"Hey\"\n}";
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);

        Request request = new Request.Builder().url("http://localhost:8080/rest/pingpong").post(body).build();

        Call call = client.newCall(request);

        Response resp = call.execute();

        System.out.println(resp.body());
        */

        /*

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost("http://localhost:8080/rest/pingpong");
        StringEntity body = new StringEntity("{\n" +
                "    \"ping\" : \"Hey\"\n" +
                "}");
        request.addHeader("content-type","application/json");
        request.addHeader("Accept","**");
        request.setEntity(body);
        HttpResponse resp = client.execute(request);
        HttpEntity ent = resp.getEntity();
        String respBody = EntityUtils.toString(ent);
        System.out.println(respBody);*/


    }

    /*
    private static LargeObjectPojo.LargeRequestPojo getLargeRequestPojo(){
        return LargeObjectPojo.LargeRequestPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).list_largeNestedRequest(getLargeNestedPojo()).list_otherLargeNestedRequest(getOtherLargeNestedPojo()).build();
    }

    private static List<LargeObjectPojo.OtherLargeNestedPojo> getOtherLargeNestedPojo() {
        List<LargeObjectPojo.OtherLargeNestedPojo> oln = new ArrayList<>();
        LargeObjectPojo.OtherLargeNestedPojo obj = LargeObjectPojo.OtherLargeNestedPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).field_i(12341.12).field_j(1234.123).field_k(getLong()).field_l(getLong()).field_m("test").field_n("TestThis").field_o(true).field_p(false).field_q(getFloat()).field_r(getFloat()).field_s(getLong()).field_t(getLong()).build();
        oln.add(obj);
        oln.add(obj);
        oln.add(obj);
        return oln;
    }

    private static List<LargeObjectPojo.LargeNestedPojo> getLargeNestedPojo() {
        List<LargeObjectPojo.LargeNestedPojo> ln = new ArrayList<>();
        LargeObjectPojo.LargeNestedPojo obj = LargeObjectPojo.LargeNestedPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).field_i(12341.12).field_j(1234.123).field_k(getLong()).field_l(getLong()).field_m("test").field_n("TestThis").field_o(true).field_p(false).field_q(getFloat()).field_r(getFloat()).field_s(getLong()).field_t(getLong()).build();
        ln.add(obj);
        ln.add(obj);
        ln.add(obj);
        return ln;
    }



    private static float getFloat() {
        return Float.parseFloat("1234.1");
    }

    private static long getLong() {
        return Long.parseLong("1234");
    }*/
}
