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
    @Column(name = "sesiMengajar", nullable = false)
    private Long sesiMengajar;

    @NotNull
    @Column(name = "sesiTambahan", nullable = false)
    private Long sesiTambahan;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;


    @Column(name = "uangKonsum", nullable = true)
    private Long uangKonsum;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang", referencedColumnName= "idCabang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    public Long getIdPresensi() {
        return idPresensi;
    }

    public void setIdPresensi(Long idPresensi) {
        this.idPresensi = idPresensi;
    }

    public Date getTanggalPresensi() {
        return tanggalPresensi;
    }

    public void setTanggalPresensi(Date tanggalPresensi) {
        this.tanggalPresensi = tanggalPresensi;
    }

    public Long getSesiMengajar() {
        return sesiMengajar;
    }

    public void setSesiMengajar(Long sesiMengajar) {
        this.sesiMengajar = sesiMengajar;
    }

    public Long getSesiTambahan() {
        return sesiTambahan;
    }

    public void setSesiTambahan(Long sesiTambahan) {
        this.sesiTambahan = sesiTambahan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUangKonsum() {
        return uangKonsum;
    }

    public void setUangKonsum(Long uangKonsum) {
        this.uangKonsum = uangKonsum;
    }

    public CabangModel getCabang() {
        return cabang;
    }

    public void setCabang(CabangModel cabang) {
        this.cabang = cabang;
    }

    public UserModel getPegawai() {
        return pegawai;
    }

    public void setPegawai(UserModel pegawai) {
        this.pegawai = pegawai;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pengaju", referencedColumnName= "idUser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel pegawai;

}
