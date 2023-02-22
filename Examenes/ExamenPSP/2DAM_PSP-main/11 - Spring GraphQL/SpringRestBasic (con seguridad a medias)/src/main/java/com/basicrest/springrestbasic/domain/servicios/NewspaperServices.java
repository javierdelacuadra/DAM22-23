package com.basicrest.springrestbasic.domain.servicios;

import com.basicrest.springrestbasic.domain.modelo.Newspaper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewspaperServices {
    @Transactional
    List<Newspaper> getAllNewspapers();

    @Transactional
    Newspaper getNewsById(Long id);

    @Transactional
    Newspaper createNewspaper(Newspaper newspaper);

    Newspaper updateNewspaper(Newspaper newspaper);

    void deleteNewspaper(Long id);
}
