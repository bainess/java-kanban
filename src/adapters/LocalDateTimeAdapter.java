package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yy-MM-dd HH.mm");

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime dateTime) throws IOException {
        if (dateTime == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(dateTime.format(fmt));
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
            return LocalDateTime.parse(dateString, fmt);
        } catch (DateTimeParseException e) {
            throw new IOException("Failed to parse date", e);
        }
    }
}
