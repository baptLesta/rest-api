package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
public class Genre {
    @Id
    @Column(name="idGenre")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "GenreFilm",
            joinColumns = @JoinColumn(name = "GenreFilm", referencedColumnName = "idGenre"),
            inverseJoinColumns = @JoinColumn(name = "idGenre"))
    private Set<Film> filmo = new HashSet<Film>();

    private String nom;

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

    public Set<Film> getFilmo() {
        return filmo;
    }

    public void setFilmo(Set<Film> filmo) {
        this.filmo = filmo;
    }
}
