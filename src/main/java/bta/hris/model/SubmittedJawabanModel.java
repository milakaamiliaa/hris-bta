package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "submittedJawaban")
public class SubmittedJawabanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJawaban;

    @NotNull
    @Column(name = "jawaban", nullable = false)
    private String jawaban;

    @NotNull
    @Column(name = "is_correct", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean isCorrect;

    @NotNull
    @Column(name = "is_chosen", nullable = false, columnDefinition = "tinyint(1) default 1")
    private boolean isChosen;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "submittedSoal", referencedColumnName= "idSoal", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubmittedSoalModel soal;

    public Long getIdJawaban() {
        return idJawaban;
    }

    public void setIdJawaban(Long idJawaban) {
        this.idJawaban = idJawaban;
    }

    public String getJawaban() {
        return jawaban;
    }

    public void setJawaban(String jawaban) {
        this.jawaban = jawaban;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isChosen() {
        return isChosen;
    }

    public boolean setIsChosen() {
        return isChosen;
    }

    public void setChosen(boolean chosen) {
        isChosen = chosen;
    }

    public SubmittedSoalModel getSoal() {
        return soal;
    }

    public void setSoal(SubmittedSoalModel soal) {
        this.soal = soal;
    }
}
