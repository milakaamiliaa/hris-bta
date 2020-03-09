package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@Entity
@Table(name = "pegawai")
public class PegawaiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPegawai;

    @NotNull
    @Column(name = "nip", nullable = false)
    private String nip;

    @NotNull
    @Column(name = "mataPelajaran", nullable = false)
    private String mataPelajaran;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @NotNull
    @Column(name = "noRekening", nullable = false)
    private Long noRekening;

    @NotNull
    @Column(name = "namaBank", nullable = false)
    private String namaBank;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pegawai_id", referencedColumnName = "idUser", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "golongan_pegawai", referencedColumnName= "idGolongan", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GolonganModel golongan;

    @OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GajiModel> listGaji;

}
