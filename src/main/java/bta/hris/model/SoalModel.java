package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    @Column(name = "correctAnswer", nullable = false)
    private String correctAnswer;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "paketSoal", referencedColumnName= "idPaket", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private PaketSoalModel paketSoal;

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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

//    public PaketSoalModel getPaketSoal() {
//        return paketSoal;
//    }
//
//    public void setPaketSoal(PaketSoalModel paketSoal) {
//        this.paketSoal = paketSoal;
//    }
}
