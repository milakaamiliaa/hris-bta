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
    @JoinColumn(name = "stafCabang", referencedColumnName = "idUser", nullable = true)
    private UserModel stafCabang;

    public Long getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(Long idCabang) {
        this.idCabang = idCabang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
}
