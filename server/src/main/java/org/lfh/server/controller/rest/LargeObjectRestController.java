package org.lfh.server.controller.rest;

import com.lfh.LargeRequest;
import com.lfh.LargeResponse;
import org.lfh.server.data.LargeObjectPojo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LargeObjectRestController {
    @RequestMapping("/rest/largeObject")
    LargeObjectPojo.LargeResponsePojo largeObject(@RequestBody LargeObjectPojo.LargeRequestPojo req){
        return getLargeResponse();
    }
    private static LargeObjectPojo.LargeResponsePojo getLargeResponse(){
        return LargeObjectPojo.LargeResponsePojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).list_largeNestedResponse(getLargeNested()).list_otherLargeNestedResponse(getOtherLargeNested()).build();
    }

    private static List<LargeObjectPojo.OtherLargeNestedPojo> getOtherLargeNested() {
        List<LargeObjectPojo.OtherLargeNestedPojo> oln = new ArrayList<>();
        LargeObjectPojo.OtherLargeNestedPojo obj = LargeObjectPojo.OtherLargeNestedPojo.builder().field_a(getLong()).field_b(getLong()).field_c("TestTest").field_d("TestProto").field_e(false).field_f(true).field_g(getFloat()).field_h(getFloat()).field_i(12341.12).field_j(1234.123).field_k(getLong()).field_l(getLong()).field_m("test").field_n("TestThis").field_o(true).field_p(false).field_q(getFloat()).field_r(getFloat()).field_s(getLong()).field_t(getLong()).build();
        oln.add(obj);
        oln.add(obj);
        oln.add(obj);
        return oln;
    }

    private static List<LargeObjectPojo.LargeNestedPojo> getLargeNested() {
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
    }
}
