package com.example.demovaadin.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.val;

/**
 * Parsing data yg dikirim ke client dari date menjadi long JsonDateTimeSerializer
 */
public class JsonDateTimeSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        // gen.writeNumber(date.getTime()); // diubah ke epoch
        // https://mincong.io/2017/02/16/convert-date-to-string-in-java/
        val sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        gen.writeString(sdf.format(date));// ubah ke iso
    }
}
