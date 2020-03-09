package bta.hris.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "gaji")
public class GajiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGaji;

    @NotNull
    @Column(name = "periode", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date periode;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "total", nullable = false)
    private Long total;

    @NotNull
    @Column(name = "golongan", nullable = false)
    private GolonganModel golongan;
    /*Apakah kita simpan objeknya atau rate & pajaknya saja?*/

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gaji_id", referencedColumnName = "idPegawai", nullable = false)
    private PegawaiModel pegawai;

}
