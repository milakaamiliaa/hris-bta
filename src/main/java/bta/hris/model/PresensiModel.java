package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "presensi")
public class PresensiModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPresensi;

    @NotNull
    @Column(name = "tanggalPresensi", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tanggalPresensi;

    @NotNull
    @Column(name = "sesiMengajar", nullable = false)
    private Long sesiMengajar;

    @Column(name = "sesiTambahan", nullable = true)
    private Long sesiTambahan;

//    @NotNull
//    @Size(max = 255)
//    @Column(name = "kodeGaji", nullable = true)
//    private String kodeGaji;


    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "uangKonsum", nullable = true)
    private Long uangKonsum;

    @Column(name = "kodeGaji", nullable = true)
    private String kodeGaji;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cabang", referencedColumnName= "idCabang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CabangModel cabang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pengaju", referencedColumnName= "idUser", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel pegawai;

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

    public Long getIdPresensi() {
        return idPresensi;
    }

    public void setIdPresensi(Long idPresensi) {
        this.idPresensi = idPresensi;
    }

    public LocalDate getTanggalPresensi() {
        return tanggalPresensi;
    }

    public void setTanggalPresensi(LocalDate tanggalPresensi) {
        this.tanggalPresensi = tanggalPresensi;
    }

//    public Long getJumlahSesi() {
//        return sesiMengajar + sesiTambahan;
//    }
//
//    public void setJumlahSesi(Long jumlahSesi) {
//        this.jumlahSesi = jumlahSesi;
//    }

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

    public String getKodeGaji() {
        return kodeGaji;
    }

    public void setKodeGaji(String kodeGaji) {
        this.kodeGaji = kodeGaji;
    }
}
