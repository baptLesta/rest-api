package repository;

import entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class GenreDao implements Dao<Genre>{

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void save(Genre genre) {

    }

    @Override
    public Genre load(long id) {
        Genre genre = hibernateTemplate.load(Genre.class, 2);
        return genre;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Genre genre) {

    }

    @Override
    public List<Genre> loadAll() {
        List<Genre> genres = hibernateTemplate.loadAll(Genre.class);
        System.out.println(genres);
        return genres;
    }
}
