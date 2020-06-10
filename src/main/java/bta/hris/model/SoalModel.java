package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "soal")
public class SoalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSoal;

    @NotNull
    @Column(name = "pertanyaan", nullable = false)
    private String pertanyaan;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "paketSoal", referencedColumnName = "idPaket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PaketSoalModel paketSoal;

    @OneToMany(mappedBy = "soal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JawabanModel> listJawaban;

    public Long getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(Long idSoal) {
        this.idSoal = idSoal;
    }

    public List<JawabanModel> getListJawaban() {
        return listJawaban;
    }

    public void setListJawaban(List<JawabanModel> listJawaban) {
        this.listJawaban = listJawaban;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public PaketSoalModel getPaketSoal() {
        return paketSoal;
    }

    public void setPaketSoal(PaketSoalModel paketSoal) {
        this.paketSoal = paketSoal;
    }
}
