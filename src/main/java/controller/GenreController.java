package controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import entity.Genre;
import entity.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.Dao;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenreController {
    @Autowired
    Dao<Genre> genreDao;
    @Autowired
    Dao<Personne> personneDao;


    @RequestMapping("/genres")
    public ResponseEntity<Collection<Genre>> getFilmWithGenres() {
        return new ResponseEntity<>(genreDao.loadAll(), HttpStatus.OK);
    }

    @RequestMapping("personnes")
    public List<Personne> getAllPersones() {
        List<Personne> personnes = personneDao.loadAll();
        return personnes;
    }

}
