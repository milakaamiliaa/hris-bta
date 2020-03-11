package bta.hris.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @NotNull
    @Column(name = "noTelp", nullable = false)
    private String noTelp;

    @NotNull
    @Column(name = "tglLahir",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tglLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

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

    @OneToMany(mappedBy = "calonPengajar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HasilTesModel> listHasilTes;

}
