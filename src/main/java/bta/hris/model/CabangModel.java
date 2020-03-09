package bta.hris.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "cabang")
public class CabangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCabang;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @NotNull
    @Column(name = "noTelp", nullable = false)
    private String noTelp;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "jumlahSiswa", nullable = false)
    private Long jumlahSiswa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cabang_id", referencedColumnName = "idPegawai", nullable = false)
    private PegawaiModel stafCabang;
    /*Apakah menurut kalian refer ke pegawai atau ke user?*/
}
