package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cabangData")
public class CabangDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCabangData;

    @NotNull
    @Column(name = "rasio", nullable = false)
    private Float rasio;

    @NotNull
    @Column(name = "periode", nullable = false)
    private String periode;

    @NotNull
    @Column(name = "jumlahSiswa", nullable = false)
    private Long jumlahSiswa;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAt;

    @OneToMany(mappedBy = "cabangData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PresensiModel> listPresensi;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang", referencedColumnName = "idCabang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    public Long getIdCabangData() {
        return idCabangData;
    }

    public void setIdCabangData(Long idCabangData) {
        this.idCabangData = idCabangData;
    }

    public Float getRasio() {
        return rasio;
    }

    public void setRasio(Float rasio) {
        this.rasio = rasio;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public Long getJumlahSiswa() {
        return jumlahSiswa;
    }

    public void setJumlahSiswa(Long jumlahSiswa) {
        this.jumlahSiswa = jumlahSiswa;
    }

    public List<PresensiModel> getListPresensi() {
        return listPresensi;
    }

    public void setListPresensi(List<PresensiModel> listPresensi) {
        this.listPresensi = listPresensi;
    }

    public CabangModel getCabang() {
        return cabang;
    }

    public void setCabang(CabangModel cabang) {
        this.cabang = cabang;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
