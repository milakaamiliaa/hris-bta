package bta.hris.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "golongan")
public class GolonganModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGolongan;

    @NotNull
    @Column(name = "nama",nullable = false)
    private String nama;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Long rate;

    @NotNull
    @Column(name = "pajak",nullable = false)
    private Float pajak;

    @NotNull
    @Column(name = "is_active", nullable = false, columnDefinition = "default 'true'")
    private boolean isActive;

    @OneToMany(mappedBy = "golongan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserModel> listPegawai;

    public Long getIdGolongan() {
        return idGolongan;
    }

    public void setIdGolongan(Long idGolongan) {
        this.idGolongan = idGolongan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Float getPajak() {
        return pajak;
    }

    public void setPajak(Float pajak) {
        this.pajak = pajak;
    }

    public List<UserModel> getListPegawai() {
        return listPegawai;
    }

    public void setListPegawai(List<UserModel> listPegawai) {
        this.listPegawai = listPegawai;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
