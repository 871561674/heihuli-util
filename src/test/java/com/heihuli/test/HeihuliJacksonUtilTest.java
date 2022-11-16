package com.heihuli.test;

import com.heihuli.adapter.AbstractJsonAdapter;
import com.heihuli.domain.JacksonObj;
import com.heihuli.util.HeihuliJacksonUtil;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author heihuli
 */
public class HeihuliJacksonUtilTest {

    @Test
    public void omTest() throws IOException {
        JacksonObj o1 = new JacksonObj();
        o1.setId("1");
        o1.setName("heihuli");
        o1.setDate(new Date());
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(o1));
        // {"id":"1","name":"heihuli","date":"2022-11-05 15:26:24.958+0800"}

        String json = "{\n" +
                "  \"id\": null,\n" +
                "  \"name\": \"heihuli\",\n" +
                "  \"date\": \"2022-11-05 15:24:40.153+0800\"\n" +
                "}";
        JacksonObj o2 = HeihuliJacksonUtil.OM.readValue(json, JacksonObj.class);
        System.out.println(HeihuliJacksonUtil.OM.valueToTree(o2));
        // {"id":null,"name":"heihuli","date":"2022-11-05 15:24:40.153+0800"}

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        FileOutputStream ot = new FileOutputStream("src/test/java/com/heihuli/file/json.txt");
        HeihuliJacksonUtil.convert(bytes, ot, new AbstractJsonAdapter() {});

//        HeihuliJacksonUtil.OM.writeValue(ot, json);
    }

}
