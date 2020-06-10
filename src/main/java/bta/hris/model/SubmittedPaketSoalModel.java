package bta.hris.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "submittedPaketSoal")
public class SubmittedPaketSoalModel implements Serializable {
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

    @OneToMany(mappedBy = "paketSoal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubmittedSoalModel> listSoal;

    @OneToOne(mappedBy = "submittedPaketSoal")
    private HasilTesModel hasilTes;

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

    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public List<SubmittedSoalModel> getListSoal() {
        return listSoal;
    }

    public void setListSoal(List<SubmittedSoalModel> listSoal) {
        this.listSoal = listSoal;
    }

    public HasilTesModel getHasilTes() {
        return hasilTes;
    }

    public void setHasilTes(HasilTesModel hasilTes) {
        this.hasilTes = hasilTes;
    }
}
