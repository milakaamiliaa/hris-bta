package bta.hris.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "paketSoal")
public class PaketSoalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaket;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "mataPelajaran", nullable = true)
    private String mataPelajaran;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "paketSoal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SoalModel> listSoal;

    public Long getIdPaket() {
        return idPaket;
    }

    public void setIdPaket(Long idPaket) {
        this.idPaket = idPaket;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<SoalModel> getListSoal() {
        return listSoal;
    }

    public void setListSoal(List<SoalModel> listSoal) {
        this.listSoal = listSoal;
    }
    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }
}
