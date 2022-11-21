package com.heihuli.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.heihuli.adapter.AbstractJsonAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author heihuli
 *
 */
public class CommonJacksonUtil {

    /**
     * ObjectMapper<br>
     * <br>
     * 拥有以下配置，请勿修改
     * <ul>
     * <li>反序列化：忽略不存在的属性</li>
     * </ul>
     */
    public static final ObjectMapper OM = new ObjectMapper();

    static {
        // 反序列化：忽略不存在的属性
        OM.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // ISO8601DateFormat 在后面被 deprecated 了，换成 StdDateFormat。但是这个版本中（2.8.11），
        // StdDateFormat 只能处理 3 位小数的 microsecond 部分： "yyyy-MM-dd HH:mm:ss.SSSZ"（注意 SSS
        // 的数量），如果使用更多位
        // 会有 bug，会使得 SSSSSSS 部分整体除以 1000 后被加进前面秒 / 分的时间中。这个 bug 在 2.9.1 版本中被修复：
        // https://github.com/FasterXML/jackson-databind/issues/1745
        //
        // ISO8601DateFormat 则不会有此问题，可以兼容任意位 microsecond。
        OM.setDateFormat(new ISO8601DateFormat());
    }

    /**
     * JsonFactory
     */
    public static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * 将输入流[in]通过指定的Json适配器[adapter]转换为输出流[out]
     *
     * @param in 字节数组
     * @param out 输出流
     * @param adapter 使用的Json适配器
     * @throws IOException 解析/生成出现异常时抛出
     */
    public static void convert(byte[] in, OutputStream out, AbstractJsonAdapter adapter) throws IOException {
        JsonParser p = CommonJacksonUtil.JSON_FACTORY.createParser(in);
        JsonGenerator g = CommonJacksonUtil.JSON_FACTORY.createGenerator(out);
        CommonJacksonUtil.convert(p, g, adapter);
    }

    /**
     * 将输入流[in]通过指定的Json适配器[adapter]转换为输出流[out]
     *
     * @param in 输入流，autoClosed
     * @param out 输出流
     * @param adapter 使用的Json适配器
     * @throws IOException 解析/生成出现异常时抛出
     */
    public static void convert(InputStream in, OutputStream out, AbstractJsonAdapter adapter) throws IOException {
        JsonParser p = CommonJacksonUtil.JSON_FACTORY.createParser(in);
        JsonGenerator g = CommonJacksonUtil.JSON_FACTORY.createGenerator(out);
        CommonJacksonUtil.convert(p, g, adapter);
    }

    /**
     * 将输入JsonParser[p]通过指定的Json适配器[adapter]转换为输出JsonGenerator[g]
     *
     * @param p JsonParser
     * @param g JsonGenerator
     * @param adapter 使用的Json适配器
     * @throws IOException 解析/生成出现异常时抛出
     */
    public static void convert(JsonParser p, JsonGenerator g, AbstractJsonAdapter adapter) throws IOException {
        convert(p, g, adapter, true);
    }

    /**
     * 将输入JsonParser[p]通过指定的Json适配器[adapter]转换为输出JsonGenerator[g]
     *
     * @param p JsonParser
     * @param g JsonGenerator
     * @param adapter 使用的Json适配器
     * @param autoFlush 自动flush JsonGenerator
     * @throws IOException 解析/生成出现异常时抛出
     */
    public static void convert(JsonParser p, JsonGenerator g, AbstractJsonAdapter adapter, Boolean autoFlush) throws IOException {
        g.setCodec(OM);
        // ====遍历
        while (!p.isClosed()) {
            JsonToken t = p.nextToken();
            if (null == t) {
                break;
            }
            if (t == JsonToken.END_ARRAY) {
                adapter.endArray(g, p, t);
            }
            if (t == JsonToken.END_OBJECT) {
                adapter.endObject(g, p, t);
            }
            if (t == JsonToken.FIELD_NAME) {
                adapter.fieldName(g, p, t);
            }
            if (t == JsonToken.NOT_AVAILABLE) {
                adapter.notAvailable(g, p, t);
            }
            if (t == JsonToken.START_ARRAY) {
                adapter.startArray(g, p, t);
            }
            if (t == JsonToken.START_OBJECT) {
                adapter.startObject(g, p, t);
            }
            if (t == JsonToken.VALUE_EMBEDDED_OBJECT) {
                adapter.valueEmbeddedObject(g, p, t);
            }
            if (t == JsonToken.VALUE_FALSE) {
                adapter.valueFalse(g, p, t);
            }
            if (t == JsonToken.VALUE_NULL) {
                adapter.valueNull(g, p, t);
            }
            if (t == JsonToken.VALUE_NUMBER_FLOAT) {
                adapter.valueNumberFloat(g, p, t);
            }
            if (t == JsonToken.VALUE_NUMBER_INT) {
                adapter.valueNumberInt(g, p, t);
            }
            if (t == JsonToken.VALUE_STRING) {
                adapter.valueString(g, p, t);
            }
            if (t == JsonToken.VALUE_TRUE) {
                adapter.valueTrue(g, p, t);
            }
        }
        // 是否自动flush
        if (autoFlush) {
            g.flush();
        }
    }
}
