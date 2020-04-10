package bta.hris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cabang")
public class CabangModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idCabang;

    @NotNull
    @Column(name = "cabang", nullable = false)
    private String cabang;

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
    @JoinColumn(name = "stafCabang", referencedColumnName = "idUser", nullable = true)
    private UserModel stafCabang;

    @JsonIgnore
    @OneToMany(mappedBy = "cabang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PresensiModel> listPresensi;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    public String getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(String idCabang) {
        this.idCabang = idCabang;
    }

    public String getNama() {
        return cabang;
    }

    public void setNama(String cabang) {
        this.cabang = cabang;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getJumlahSiswa() {
        return jumlahSiswa;
    }

    public void setJumlahSiswa(Long jumlahSiswa) {
        this.jumlahSiswa = jumlahSiswa;
    }

    public UserModel getStafCabang() {
        return stafCabang;
    }

    public void setStafCabang(UserModel stafCabang) {
        this.stafCabang = stafCabang;
    }

      public List<PresensiModel> getListPresensi() {
        return listPresensi;
    }

    public void setListPresensi(List<PresensiModel> listPresensi) {
        this.listPresensi = listPresensi;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
