package repository;

import entity.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PersonneDao implements Dao<Personne> {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public void save(Personne personne) {

    }

    @Override
    public Personne load(long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void update(Personne personne) {

    }

    @Override
    public List<Personne> loadAll() {
        List<Personne> personnes = hibernateTemplate.loadAll(Personne.class);
        return personnes;
    }
}