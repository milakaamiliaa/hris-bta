package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @Column(name = "rateGaji", nullable = false)
    private Long rateGaji;

    @NotNull
    @Column(name = "pajakGaji", nullable = false)
    private Float pajakGaji;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pegawai", referencedColumnName= "idUser", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel pegawai;

}
