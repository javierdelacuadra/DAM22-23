package dao;

import model.ReadArticle;

public interface ReadArticlesDAO {


    int add(ReadArticle ra);

    int delete(int id);

}
