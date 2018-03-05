package entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Pays")
public class Pays {

    @Id
    @Column(name="idPays")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "PaysFilm",
            joinColumns = @JoinColumn(name = "PaysFilm", referencedColumnName = "idPays"),
            inverseJoinColumns = @JoinColumn(name = "idPays"))
    private Set<Film> filmpays = new HashSet<Film>();

    private String nom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Film> getFilmpays() {
        return filmpays;
    }

    public void setFilmpays(Set<Film> filmpays) {
        this.filmpays = filmpays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
