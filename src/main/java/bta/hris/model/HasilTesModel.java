package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "hasilTes")
public class HasilTesModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHasil;

    @NotNull
    @Column(name = "answerResult", nullable = false)
    private boolean answerResult;

    @NotNull
    @Column(name = "startedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startedAt;

    @NotNull
    @Column(name = "finishedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate finishedAt;

    @NotNull
    @Column(name = "chosenAnswer", nullable = false)
    private JawabanModel chosenAnswer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "calonPengajar", referencedColumnName= "idCalon", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CalonPengajarModel calonPengajar;

    public Long getIdHasil() {
        return idHasil;
    }

    public void setIdHasil(Long idHasil) {
        this.idHasil = idHasil;
    }

    public boolean isAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(boolean answerResult) {
        this.answerResult = answerResult;
    }

    public LocalDate getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDate startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDate getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDate finishedAt) {
        this.finishedAt = finishedAt;
    }

    public JawabanModel getChosenAnswer() {
        return chosenAnswer;
    }

    public void setChosenAnswer(JawabanModel chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public CalonPengajarModel getCalonPengajar() {
        return calonPengajar;
    }

    public void setCalonPengajar(CalonPengajarModel calonPengajar) {
        this.calonPengajar = calonPengajar;
    }
}
