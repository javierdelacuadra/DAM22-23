package com.basicrest.springrestbasic.domain.servicios.impl;


import com.basicrest.springrestbasic.dao.ArticlesDao;
import com.basicrest.springrestbasic.dao.NewspapersDao;
import com.basicrest.springrestbasic.dao.modelo.DataMapper;
import com.basicrest.springrestbasic.dao.modelo.NewspaperDTO;
import com.basicrest.springrestbasic.domain.excepciones.DataIntegrityException;
import com.basicrest.springrestbasic.domain.excepciones.NotFoundException;
import com.basicrest.springrestbasic.domain.modelo.Newspaper;
import com.basicrest.springrestbasic.domain.servicios.NewspaperServices;
import com.basicrest.springrestbasic.domain.servicios.common.ServicesConstants;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewspaperServicesImpl implements NewspaperServices {

    private final NewspapersDao newspapersDao;

    private final ArticlesDao articlesDao;

    private final DataMapper dataMapper;

    public NewspaperServicesImpl(NewspapersDao newspapersDao, ArticlesDao articlesDao, DataMapper dataMapper) {
        this.newspapersDao = newspapersDao;
        this.articlesDao = articlesDao;
        this.dataMapper = dataMapper;
    }


    @Override
    @Transactional
    public List<Newspaper> getAllNewspapers() {
        return newspapersDao.findAll()
                .stream()
                .map(dataMapper::newspaperDTOToNewspaper)
                .toList();
    }

    @Override
    @Transactional
    public Newspaper getNewsById(Long id) {
        return newspapersDao.findByIdComplete(id)
                .map(dataMapper::newspaperDTOToNewspaper)
                .orElseThrow(() -> new NotFoundException(ServicesConstants.EL_NEWSPAPER_NO_EXISTE));

    }


    @Override
    @Transactional
    public Newspaper createNewspaper(Newspaper newspaper) {
        NewspaperDTO  newspaperDTO = dataMapper.newspaperToNewspaperDTO(newspaper);
        //Para añadirlo
        newspaperDTO.setId(0);
        newspaperDTO = newspapersDao.save(newspaperDTO);
        NewspaperDTO finalNewspaperDTO = newspaperDTO;
        // Guardamos artículos con id 0 para que se autoincrementen y el id del newspaper
        newspaperDTO.getArticles().forEach(articleDTO ->{
                articleDTO.setId(0);
                articleDTO.setNewspaper(finalNewspaperDTO);
                articleDTO.setId(articlesDao.save(articleDTO).getId());
        }
        );
        newspaper = dataMapper.newspaperDTOToNewspaper(newspaperDTO);
        return newspaper;
    }

    @Override
    public Newspaper updateNewspaper(Newspaper newspaper) {

        int numeroFilasUpdate = newspapersDao.updateById(newspaper.getName(), newspaper.getReleaseDate(), newspaper.getId());
        if (numeroFilasUpdate == 0) {
            throw new NotFoundException(ServicesConstants.EL_NEWSPAPER_NO_EXISTE);
        }
        return newspaper;
    }


    @Override
    public void deleteNewspaper(Long id) {

        try {
            newspapersDao.deleteById(id.intValue());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ServicesConstants.EL_NEWSPAPER_NO_EXISTE);
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(ServicesConstants.NO_SE_PUEDE_ELIMINAR_EL_NEWSPAPER_PORQUE_TIENE_ARTICULOS_ASOCIADOS);
        }


    }

}
