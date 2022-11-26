package com.heihuli.adapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

/**
 * Json适配器抽象类
 * 直接使用该类Json不会产生任何变动，所以设置为抽象类
 *
 * @author heihuli
 */
public abstract class AbstractJsonAdapter {

    /**
     * invoke when current JsonToken is JsonToken.END_ARRAY
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void endArray(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeEndArray();
    }

    /**
     * invoke when current JsonToken is JsonToken.END_OBJECT
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void endObject(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeEndObject();
    }

    /**
     * invoke when current JsonToken is JsonToken.FIELD_NAME
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void fieldName(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeFieldName(p.getText());
    }

    /**
     * invoke when current JsonToken is JsonToken.NOT_AVAILABLE
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void notAvailable(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        // ignroe
    }

    /**
     * invoke when current JsonToken is JsonToken.START_ARRAY
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void startArray(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeStartArray();
    }

    /**
     * invoke when current JsonToken is JsonToken.START_OBJECT
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void startObject(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeStartObject();
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_EMBEDDED_OBJECT
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueEmbeddedObject(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        // ignroe
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_FALSE
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueFalse(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeBoolean(p.getValueAsBoolean());
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_NULL
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueNull(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeNull();
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_NUMBER_FLOAT
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueNumberFloat(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeNumber(p.getValueAsDouble());
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_NUMBER_INT
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueNumberInt(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeNumber(p.getValueAsLong());
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_STRING
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueString(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeString(p.getValueAsString());
    }

    /**
     * invoke when current JsonToken is JsonToken.VALUE_TRUE
     *
     * @param g JsonGenerator
     * @param p JsonParser
     * @param t current JsonToken
     * @throws IOException
     */
    public void valueTrue(JsonGenerator g, JsonParser p, JsonToken t) throws IOException {
        g.writeBoolean(p.getValueAsBoolean());
    }
}
