package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Scenariste")
public class Scenariste {

    @Id
    @Column(name="idScenariste")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "ScenaristeFilm",
            joinColumns = @JoinColumn(name = "ScenaristeFilm", referencedColumnName = "idScenariste"),
            inverseJoinColumns = @JoinColumn(name = "idScenariste"))
    private Set<Film> scenaristeFilm = new HashSet<Film>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Film> getScenaristeFilm() {
        return scenaristeFilm;
    }

    public void setScenaristeFilm(Set<Film> scenaristefilm) {
        this.scenaristeFilm = scenaristefilm;
    }
}
