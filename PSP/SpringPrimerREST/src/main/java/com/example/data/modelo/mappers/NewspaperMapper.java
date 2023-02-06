package com.example.data.modelo.mappers;

import com.example.data.modelo.NewspaperEntity;
import com.example.domain.modelo.Newspaper;
import org.springframework.stereotype.Component;

@Component
public class NewspaperMapper {
    public Newspaper toNewspaper(NewspaperEntity newspaperEntity) {
        return new Newspaper(newspaperEntity.getId(), newspaperEntity.getName(), newspaperEntity.getRelease_date());
    }

    public NewspaperEntity toNewspaperEntity(Newspaper newspaper) {
        return new NewspaperEntity(newspaper.id(), newspaper.name(), newspaper.release_date());
    }
}
