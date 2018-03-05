package controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.Dao;
import repository.FilmDao;
import view.View;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
public class FilmController {
    @Autowired
    FilmDao filmDao;
    // Ok
    @RequestMapping(value = "/film")
    public Film loadFilm(@RequestParam(value="id", defaultValue = "338564") int id)
    {
        Film film = filmDao.load(id);
        return film;
    }
    // Ok
    @RequestMapping(value= "/films")
    public List<Film> getFilms(
            @RequestParam(value="annee", defaultValue = "0") int annee,
            @RequestParam(value="genre", defaultValue = "") String [] genres,
            @RequestParam(value="option", defaultValue = "titre") String [] options,
            @RequestParam(value="input", defaultValue = "") String input,
            @RequestParam(value="langue", defaultValue = "") String langue,
            @RequestParam(value="dureeInf", defaultValue = "0") int dureeInf,
            @RequestParam(value="dureeSup", defaultValue = "1000") int dureeSup
    ) {
        List<Film> filmList = filmDao.loadFilms(input, annee, genres, options, langue, dureeInf, dureeSup);
        return filmList;
    }

    @RequestMapping(value= "/films-page")
    public Page<Film> getFilms(@RequestParam(value="index", defaultValue = "1") int index) {
        Page<Film> filmList = filmDao.P_loadAll(new PageRequest(index,10));
        return filmList;
    }
}
