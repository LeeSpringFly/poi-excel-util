package com.lee.poiexcelutil.pojo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EnumJsonSerialize extends JsonSerializer<StatusEnum> {

    @Override
    public void serialize(StatusEnum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(anEnum.getCnName());
    }
}
