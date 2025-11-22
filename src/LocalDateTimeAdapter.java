import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yy-MM-dd HH.mm");
    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime dateTime) throws IOException {
        if (dateTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(dateTime.format(FMT));
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String dateString = jsonReader.nextString();
        if (dateString == null || dateString.isEmpty()) {
            jsonReader.nextNull();
            return null;
        }
        try {
            return LocalDateTime.parse(dateString, FMT);
        } catch (DateTimeParseException e) {
            throw new IOException("Failed to parse date", e);
        }
    }
}
