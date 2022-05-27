package org.lfh.client;


import com.google.gson.Gson;
import com.lfh.LargeNested;
import com.lfh.LargeRequest;
import com.lfh.OtherLargeNested;
import com.lfh.PingRequest;
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

        //run benchmark
        org.openjdk.jmh.Main.main(args);
    }
}
