package com.savvant.ksql;

import com.arakelian.jq.JqRequest;
import com.arakelian.jq.JqResponse;
import com.arakelian.jq.JqLibrary;
import com.arakelian.jq.ImmutableJqLibrary;
import com.arakelian.jq.ImmutableJqRequest;



import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;


@UdfDescription(name = "colToJson", description = "Compose JSON output from a single column")
public class KsqlJqUdf {

    private static final Logger LOGGER = Logger.getLogger(KsqlJqUdf.class.getName());

    private static final JqLibrary library = ImmutableJqLibrary.of();





    @Udf(description = "Take a single column and apply a Jq filter to it.")
    public String colToJson(String jsonInput, String jqFilter) {
        LOGGER.info("jsonInput was: " + jsonInput);
        LOGGER.info("jqFilter was : " + jqFilter);
        String res;

        LOGGER.info("Building request...");
        JqRequest req = ImmutableJqRequest.builder()
                .lib(library)
                .input(jsonInput)
                .indent(JqRequest.Indent.NONE)
                .filter(jqFilter)
                .build();

        LOGGER.info("Request built.");

        try {
            LOGGER.fine("About to execute request...");


            JqResponse resp = req.execute();

            //JqResponse resp = req.execute();
            LOGGER.fine("Request executed.");

            LOGGER.fine("Request object is: " + req.toString());
            LOGGER.fine("Response object is: " + resp);


            if (resp.hasErrors()) {
                LOGGER.severe("JQ threw an error: " + resp.getErrors());
                res = "";
            } else {
                res = resp.getOutput();
                LOGGER.fine("JQ output: " + res);
            }

        } catch (Exception e) {
            LOGGER.severe("Caught an error: " + e.toString());
            res = "";
        }

        return res;
    }
}
