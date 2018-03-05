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
public class FilmDao {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public List<Film> loadAll() {
        List<Film> filmList = this.hibernateTemplate.loadAll(Film.class);
        for (Film film : filmList) {
            System.out.println(film.getRealisateurs().size());
        }
        return filmList;
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
