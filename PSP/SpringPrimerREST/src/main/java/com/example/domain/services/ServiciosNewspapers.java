package com.example.domain.services;

import com.example.common.Constantes;
import com.example.data.NewspapersRepository;
import com.example.data.modelo.NewspaperEntity;
import com.example.data.modelo.mappers.NewspaperMapper;
import com.example.domain.exceptions.ObjectDoesntExistException;
import com.example.domain.modelo.Newspaper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiciosNewspapers {

    private final NewspapersRepository newspapersRepository;
    private final NewspaperMapper newspaperMapper;

    public ServiciosNewspapers(NewspapersRepository newspapersRepository, NewspaperMapper newspaperMapper) {
        this.newspapersRepository = newspapersRepository;
        this.newspaperMapper = newspaperMapper;
    }

    public List<Newspaper> getNewspapers() {
        return newspapersRepository.findAll()
                .stream()
                .map(newspaperMapper::toNewspaper)
                .collect(Collectors.toList());
    }

    public Newspaper getNewspaperById(Long id) {
        return newspapersRepository.findById(String.valueOf(id))
                .map(newspaperMapper::toNewspaper)
                .orElseThrow(() -> new ObjectDoesntExistException(Constantes.NEWSPAPER_NOT_FOUND));
    }

    public Newspaper createNewspaper(Newspaper newspaper) {
        return newspaperMapper.toNewspaper(newspapersRepository.save(newspaperMapper.toNewspaperEntity(newspaper)));
    }

    public int updateNewspaper(Newspaper newspaper) {
        NewspaperEntity newspaperEntity = newspaperMapper.toNewspaperEntity(newspaper);
        return newspapersRepository.updateNewspaper(newspaperEntity.getName(), newspaperEntity.getRelease_date(), String.valueOf(newspaperEntity.getId()));
    }

    public void deleteNewspaper(Long id) {
        try {
            newspapersRepository.deleteById(String.valueOf(id));
        } catch (Exception e) {
            throw new ObjectDoesntExistException(Constantes.NEWSPAPER_NOT_FOUND);
        }
    }
}