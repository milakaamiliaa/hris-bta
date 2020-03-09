package bta.hris.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "calonPengajar")
public class CalonPengajarModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCalon;

    @NotNull
    @Column(name = "cv", nullable = false)
    private String cv;

    @NotNull
    @Column(name = "mataPelajaran", nullable = false)
    private String mataPelajaran;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "nilaiPsikotes",nullable = true)
    private Long nilaiPsikotes;

    @NotNull
    @Column(name = "nilaiMataPelajaran",nullable = true)
    private Long nilaiMataPelajaran;

    @NotNull
    @Column(name = "tesDeadline", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tesDeadline;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "calonPengajar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HasilTesModel> listHasilTes;

}
