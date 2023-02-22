package dao.retrofit.produces;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.ToJson;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeMoshiAdapter {
    @FromJson
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString());
    }

    @ToJson
    public void write(JsonWriter jsonWriter, LocalDateTime localDate) throws IOException {
        if (localDate == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDate.toString());
        }
    }
}
