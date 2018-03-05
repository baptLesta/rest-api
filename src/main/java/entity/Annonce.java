package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Annonce")
public class Annonce implements Serializable{

    @Id
    @Column(name="idAnnonce")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String video;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idFilm")
    @JsonIgnore
    private Film film;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}
