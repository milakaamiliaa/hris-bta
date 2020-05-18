package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "submittedSoal")
public class SubmittedSoalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSoal;

    @NotNull
    @Column(name = "pertanyaan", nullable = false)
    private String pertanyaan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "submittedPaketSoal", referencedColumnName= "idPaket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmittedPaketSoalModel paketSoal;

    @OneToMany(mappedBy = "soal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SubmittedJawabanModel> listJawaban;

    public Long getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(Long idSoal) {
        this.idSoal = idSoal;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public SubmittedPaketSoalModel getPaketSoal() {
        return paketSoal;
    }

    public void setPaketSoal(SubmittedPaketSoalModel paketSoal) {
        this.paketSoal = paketSoal;
    }

    public List<SubmittedJawabanModel> getListJawaban() {
        return listJawaban;
    }

    public void setListJawaban(List<SubmittedJawabanModel> listJawaban) {
        this.listJawaban = listJawaban;
    }
}
