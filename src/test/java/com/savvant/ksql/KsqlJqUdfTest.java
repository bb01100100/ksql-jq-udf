package com.savvant.ksql;

import org.junit.Test;

import static org.junit.Assert.*;

public class KsqlJqUdfTest {

    @Test
    public void colToJson() {
        String myJsonInput = "{\"key\": \"1234\", \"value\": { \"customFields\": { \"customer\": { \"AccountNumber\": \"1234\"}}}}";

        KsqlJqUdf udf = new KsqlJqUdf();

        String res = udf.colToJson(myJsonInput,"{\"user\": .value.customFields.customer.AccountNumber}");

        //System.out.println("Got response: " + res);

        assertEquals("{\"user\":\"1234\"}", res);

    }
}