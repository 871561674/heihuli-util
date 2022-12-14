package com.heihuli.test;

import com.heihuli.adapter.AbstractJsonAdapter;
import com.heihuli.domain.JacksonObj;
import com.heihuli.util.CommonJacksonUtil;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @author heihuli
 */
public class CommonJacksonUtilTest {

    @Test
    public void omTest() throws IOException {
        JacksonObj o1 = new JacksonObj();
        o1.setId("1");
        o1.setName("heihuli");
        o1.setDate(new Date());
        System.out.println(CommonJacksonUtil.OM.valueToTree(o1).toString());
        // {"id":"1","name":"heihuli","date":"2022-12-12 19:09:40.049+0800"}
        System.out.println(CommonJacksonUtil.OM.writeValueAsString(o1));
        // {"id":"1","name":"heihuli","date":"2022-12-12 19:09:40.049+0800"}

        String json = "{\n" +
                "  \"id\": null,\n" +
                "  \"name\": \"heihuli\",\n" +
                "  \"date\": \"2022-11-05 15:24:40.153+0800\"\n" +
                "}";
        JacksonObj o2 = CommonJacksonUtil.OM.readValue(json, JacksonObj.class);
        System.out.println(CommonJacksonUtil.OM.valueToTree(o2));
        // {"id":null,"name":"heihuli","date":"2022-11-05 15:24:40.153+0800"}

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        FileOutputStream ot = new FileOutputStream("src/test/java/com/heihuli/file/json.txt");
        CommonJacksonUtil.convert(bytes, ot, new AbstractJsonAdapter() {});


//        HeihuliJacksonUtil.OM.writeValue(ot, json);
    }

}
