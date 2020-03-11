package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "presensi")
public class PresensiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPresensi;

    @NotNull
    @Column(name = "tanggalPresensi", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tanggalPresensi;

    @NotNull
    @Column(name = "jumlahSesi", nullable = false)
    private Long jumlahSesi;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "uangKonsum", nullable = true)
    private Long uangKonsum;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang", referencedColumnName= "idCabang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pengaju", referencedColumnName= "idUser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel pegawai;

}
