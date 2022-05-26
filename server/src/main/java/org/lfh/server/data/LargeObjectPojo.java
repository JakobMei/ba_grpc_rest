package org.lfh.server.data;

import java.util.List;
import lombok.Builder;
import lombok.Data;

public class LargeObjectPojo {

    @Data
    @Builder
    public static class LargeRequestPojo{
        long field_a;
        long field_b;
        String field_c;
        String field_d;
        boolean field_e;
        boolean field_f;
        float field_g;
        float field_h;
        List<LargeNestedPojo> list_largeNestedRequest;
        List<OtherLargeNestedPojo> list_otherLargeNestedRequest;
    }
    @Data
    @Builder
    public static class LargeNestedPojo{
        long field_a;
        long field_b;
        String field_c;
        String field_d;
        boolean field_e;
        boolean field_f;
        float field_g;
        float field_h;
        double field_i;
        double field_j;
        long field_k;
        long field_l;
        String field_m;
        String field_n;
        boolean field_o;
        boolean field_p;
        float field_q;
        float field_r;
        long field_s;
        long field_t;
    }
    @Data
    @Builder
    public static class OtherLargeNestedPojo{
        long field_a;
        long field_b;
        String field_c;
        String field_d;
        boolean field_e;
        boolean field_f;
        float field_g;
        float field_h;
        double field_i;
        double field_j;
        long field_k;
        long field_l;
        String field_m;
        String field_n;
        boolean field_o;
        boolean field_p;
        float field_q;
        float field_r;
        long field_s;
        long field_t;
    }
    @Data
    @Builder
    public static class LargeResponsePojo{
        long field_a;
        long field_b;
        String field_c;
        String field_d;
        boolean field_e;
        boolean field_f;
        float field_g;
        float field_h;
        List<LargeNestedPojo> list_largeNestedResponse;
        List<OtherLargeNestedPojo> list_otherLargeNestedResponse;
    }
}
