package entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import view.View;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Film")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Film {

    @Id
    @Column(name="idfilm")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titre;
    private int annee;
    private String langue;
    private int duree;
    private String resume;
    private String poster;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany
    @JoinTable(name = "GenreFilm", joinColumns = @JoinColumn(name = "idFilm"),
            inverseJoinColumns = @JoinColumn(name = "idGenre"))
    private Set<Genre> genres = new HashSet<Genre>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany
    @JoinTable(name = "PaysFilm", joinColumns = @JoinColumn(name = "idFilm"),
            inverseJoinColumns = @JoinColumn(name = "idPays"))
    private Set<Pays> pays = new HashSet<Pays>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany
    @JoinTable(name = "ScenaristeFilm", joinColumns = @JoinColumn(name = "idFilm"),
            inverseJoinColumns = @JoinColumn(name = "idScenariste"))
    private Set<Scenariste> scenaristes = new HashSet<Scenariste>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "film")
    private Set<Annonce> annonces;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles = new HashSet<Role>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany
    @JoinTable(name = "RealisateurFilm", joinColumns = @JoinColumn(name = "idFilm"),
            inverseJoinColumns = @JoinColumn(name = "idPersonne"))
    private Set<Personne> realisateurs = new HashSet<Personne>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Pays> getPays() {
        return pays;
    }

    public void setPays(Set<Pays> pays) {
        this.pays = pays;
    }

    public Set<Scenariste> getScenaristes() {
        return scenaristes;
    }

    public void setScenaristes(Set<Scenariste> scenaristes) {
        this.scenaristes = scenaristes;
    }

    public Set<Annonce> getAnnonces() {
        return annonces;
    }

    public void setAnnonces(Set<Annonce> annonces) {
        this.annonces = annonces;
    }

    public Set<Personne> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(Set<Personne> realisateurs) {
        this.realisateurs = realisateurs;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id = " + id +
                ", titre = " + titre + "\'" +
                ", annne = " + annee + "\'" +
                ", langue = " + langue + "\'" +
                ", duree = " + duree + "\'" +
                ", resume = " + resume + "\'" +
                ", poster = " + poster + "}";
    }
}
