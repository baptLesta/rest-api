package repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.Film;
import entity.Genre;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.*;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.*;

@Repository
@Transactional
public class FilmDao implements Dao<Film> {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public void save(Film film) {
    }

    @Override
    public Film load(long id) {
        Film film = this.hibernateTemplate.load(Film.class, id);

        return film;
    }

    @Override
    public List<Film> loadAll() {
        List<Film> filmList = this.hibernateTemplate.loadAll(Film.class);
        for (Film film : filmList) {
            System.out.println(film.getRealisateurs().size());
        }
        return filmList;
    }

    @Override
    public void update(Film film) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {

    }

    public Film loadFilmWithGenres(long id) {
        Film film = this.hibernateTemplate.load(Film.class, id);
        int size = film.getGenres().size();

        return film;
    }

    public List<Film> loadFilmsWithGenres() {
        List<Film> films = this.hibernateTemplate.loadAll(Film.class);
        for (Film film : films) {
            System.out.println(film.getRealisateurs().size());
        }
        return films;
    }

    public List<Film> loadTitreFilmWhereGenreIs(String genre) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
        DetachedCriteria genreCriteria = criteria.createCriteria("genres");
        genreCriteria.add(Restrictions.eq("nom", genre));

//        ProjectionList properties = Projections.projectionList();
////        properties.add(Projections.property("titre"));
////        criteria.setProjection(properties);

        List<Film> films = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
        films.forEach(film -> System.out.println(film.getGenres().size()));

        return films;
    }

    public List<Film> loadFilmWhereTitleDate(String title, int date) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
        Criterion crit1 = Restrictions.and(Restrictions.like("annee", date));
        Criterion crit2 = Restrictions.and(Restrictions.like("titre", title, MatchMode.ANYWHERE));
        criteria.add(Restrictions.and(crit1, crit2));
        List<Film> films = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);

        return films;
    }

    public Page<Film> P_loadTitreAnneFilmWhereDate(int date, Pageable pageable) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
        Criterion crit1 = Restrictions.and(Restrictions.like("annee", date));
        criteria.add(Restrictions.and(crit1));
        hibernateTemplate.setFetchSize(pageable.getPageNumber() * pageable.getPageSize());
        hibernateTemplate.setMaxResults(pageable.getPageSize());

        ProjectionList properties = Projections.projectionList();
        properties.add(Projections.property("titre"));
        properties.add(Projections.property("annee"));

        criteria.setProjection(properties);
        List<Film> films = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);

        int totalRows = films.size();

        Page<Film> page = new PageImpl<Film>(films, pageable, totalRows);
        return page;
    }

    public Page<Film> P_loadAll(Pageable pageable) {
        List<Film> filmList = this.hibernateTemplate.loadAll(Film.class);
        int start = pageable.getOffset();
        int end = (start + pageable.getPageSize()) > filmList.size() ? filmList.size() : (start + pageable.getPageSize());
        Page<Film> pages = new PageImpl<Film>(filmList.subList(start, end), pageable, filmList.size());
        return pages;
    }

    /**
     * Search function
     * @param input
     * @param annee
     * @param genres
     * @param optionss
     * @return
     */
    public List<Film> loadFilms(
            String input,
            int annee,
            String[] genres,
            String[] optionss,
            String langue,
            int dureeInf,
            int dureeSup
    ) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Film.class);
        criteria.createAlias("scenaristes", "scenariste")
                .createAlias("realisateurs", "realisateur")
                .createAlias("roles", "role")
                .createAlias("role.personne","acteur");

        Disjunction disjunction = Restrictions.disjunction();
        List<String> options = Arrays.asList(optionss);

        if (langue != null && !langue.isEmpty()) {
            criteria.add(Restrictions.or(Restrictions.eq("annee", annee)));
        }
        if (input != null && !input.isEmpty()) {
            if (options.contains("titre")) {
                disjunction.add(Restrictions.like("titre", input, MatchMode.ANYWHERE));
            }
            if (options.contains("realisateur")) {
                disjunction.add(Restrictions.like("realisateur.nom", input, MatchMode.ANYWHERE));
            }
            if (options.contains("acteur")) {
                disjunction.add(Restrictions.like("acteur.nom", input, MatchMode.ANYWHERE));
            }
            if (options.contains("scenariste")) {
                disjunction.add(Restrictions.like("scenariste.nom", input, MatchMode.ANYWHERE));
            }
            if (options.contains("resume")) {
                disjunction.add(Restrictions.like("resume", input, MatchMode.ANYWHERE));
            }
        }
        if (genres != null && genres.length != 0) {
            DetachedCriteria genreCriteria = criteria.createCriteria("genres");
            genreCriteria.add(Restrictions.and(Restrictions.in("nom", genres)));
        }
        if (annee != 0) {
            criteria.add(Restrictions.and(Restrictions.eq("annee", annee)));
        }
        if (dureeInf != 0 || dureeSup != 1000) {
            criteria.add(Restrictions.and(Restrictions.between("duree", dureeInf, dureeSup)));
        }
        criteria.add(disjunction);
        List<Film> films = (List<Film>) this.hibernateTemplate.findByCriteria(criteria);
        if (options.contains("realisateur")) {
            films.forEach(film -> System.out.println(film.getRealisateurs().size()));
        }
        if (options.contains("scenaristes")) {
            films.forEach(film -> System.out.println(film.getScenaristes().size()));
        }
        if (options.contains("acteur")) {
            films.forEach(film -> System.out.println(film.getRoles()));
        }
        if (genres != null && genres.length != 0) {
            films.forEach(film -> System.out.println(film.getGenres()));
        }
        List<Film> filmsWithoutDuplicates = new ArrayList<>(new HashSet<>(films));

        return filmsWithoutDuplicates;
    }
}
