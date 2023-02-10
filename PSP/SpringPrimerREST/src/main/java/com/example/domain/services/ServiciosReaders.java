package com.example.domain.services;

import com.example.common.Constantes;
import com.example.data.ReadersRepository;
import com.example.data.modelo.ReaderEntity;
import com.example.data.modelo.mappers.ReadersMapper;
import com.example.domain.exceptions.ObjectDoesntExistException;
import com.example.domain.modelo.Reader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiciosReaders {

    private final ReadersRepository readersRepository;
    private final ReadersMapper readerMapper;

    public ServiciosReaders(ReadersRepository readersRepository, ReadersMapper readerMapper) {
        this.readersRepository = readersRepository;
        this.readerMapper = readerMapper;
    }

    public List<Reader> getReaders() {
        return readersRepository.findAll()
                .stream()
                .map(readerMapper::toReader)
                .collect(Collectors.toList());
    }

    public Reader getReaderById(Long id) {
        return readersRepository.findById(id.intValue())
                .map(readerMapper::toReader)
                .orElseThrow(() -> new ObjectDoesntExistException(Constantes.READER_NOT_FOUND));
    }

    public Reader createReader(Reader reader) {
        return readerMapper.toReader(readersRepository.save(readerMapper.toReaderEntity(reader)));
    }

    public int updateReader(Reader reader) {
        ReaderEntity readerEntity = readerMapper.toReaderEntity(reader);
        return readersRepository.updateReader(readerEntity.getName(), readerEntity.getDateOfBirth(), String.valueOf(readerEntity.getId()));
    }

    public void deleteReader(Long id) {
        try {
            readersRepository.deleteById(id.intValue());
        } catch (Exception e) {
            throw new ObjectDoesntExistException(Constantes.READER_NOT_FOUND);
        }
    }
}