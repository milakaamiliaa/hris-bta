package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "jawaban")
public class JawabanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJawaban;

    @NotNull
    @Column(name = "jawaban", nullable = false)
    private String jawaban;

    @NotNull
    @Column(name = "is_correct", nullable = false, columnDefinition = "boolean default true")
    private boolean isCorrect;

    @NotNull
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "soal", referencedColumnName= "idSoal", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SoalModel soal;

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

    public SoalModel getSoal() {
        return soal;
    }

    public void setSoal(SoalModel soal) {
        this.soal = soal;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
