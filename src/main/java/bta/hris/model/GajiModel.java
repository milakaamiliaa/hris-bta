package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "gaji")
public class GajiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGaji;

    @NotNull
    @Column(name = "periode", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate periode;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "totalSesi", nullable = false)
    private Long totalSesi;

    @Column(name = "totalGaji", nullable = false)
    private Float totalGaji;

    public Long getTotalSesi() {
        return totalSesi;
    }

    public void setTotalSesi(Long totalSesi) {
        this.totalSesi = totalSesi;
    }

    public Float getTotalGaji() {
        return totalGaji;
    }

    public void setTotalGaji(Float totalGaji) {
        this.totalGaji = rateGaji*pajakGaji*totalSesi;
    }

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

    public Long getIdGaji() {
        return idGaji;
    }

    public void setIdGaji(Long idGaji) {
        this.idGaji = idGaji;
    }

    public LocalDate getPeriode() {
        return periode;
    }

    public void setPeriode(LocalDate periode) {
        this.periode = periode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Long getRateGaji() {
        return rateGaji;
    }

    public void setRateGaji(Long rateGaji) {
        this.rateGaji = rateGaji;
    }

    public Float getPajakGaji() {
        return pajakGaji;
    }

    public void setPajakGaji(Float pajakGaji) {
        this.pajakGaji = pajakGaji;
    }

    public UserModel getPegawai() {
        return pegawai;
    }

    public void setPegawai(UserModel pegawai) {
        this.pegawai = pegawai;
    }
}
