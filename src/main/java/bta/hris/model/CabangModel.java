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
    private Long idCabang;

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
    @JoinColumn(name = "stafCabang", referencedColumnName = "idUser", nullable = false)
    private UserModel stafCabang;

    @JsonIgnore
    @OneToMany(mappedBy = "cabang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PresensiModel> listPresensi;

    public Long getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(Long idCabang) {
        this.idCabang = idCabang;
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
}
