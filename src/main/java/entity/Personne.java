package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Personne")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Personne {

    @Id
    @Column(name="idPersonne")
    @JsonIgnore
    private int id;
    private String nom;
    @JsonIgnore
    private String photo;
    @JsonIgnore
    private String biographie;
    @JsonIgnore
    private String anniversaire;
    @JsonIgnore
    private String lieu;

    @OneToMany(mappedBy = "personne")
    @JsonIgnore
    private Set<Role> roles = new HashSet<Role>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinTable(name = "RealisateurFilm",
            joinColumns = @JoinColumn(name = "RealisateurFilm", referencedColumnName = "idPersonne"),
            inverseJoinColumns = @JoinColumn(name = "idPersonne"))
    private Set<Film> filmo = new HashSet<Film>();

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public String getAnniversaire() {
        return anniversaire;
    }

    public void setAnniversaire(String anniversaire) {
        this.anniversaire = anniversaire;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}