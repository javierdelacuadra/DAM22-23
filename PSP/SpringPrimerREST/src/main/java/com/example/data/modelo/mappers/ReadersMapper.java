package com.example.data.modelo.mappers;

import com.example.data.modelo.ReaderEntity;
import com.example.domain.modelo.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReadersMapper {
    public Reader toReader(ReaderEntity readerEntity) {
        return new Reader(readerEntity.getId(), readerEntity.getName(), readerEntity.getDateOfBirth());
    }

    public ReaderEntity toReaderEntity(Reader reader) {
        return new ReaderEntity(reader.id(), reader.name(), reader.dateOfBirth());
    }
}