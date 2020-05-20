package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "hasilTes")
public class HasilTesModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHasil;

    @NotNull
    @Column(name = "startedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "submittedPaketSoalId", referencedColumnName = "idPaket")
    private SubmittedPaketSoalModel submittedPaketSoal;

    @Column(name = "finishedAt", nullable = true)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate finishedAt;

    @Column(name = "nilai", nullable = true)
    private Float nilai;

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

    public Float getNilai() {
        return nilai;
    }

    public void setNilai(Float nilai) {
        this.nilai = nilai;
    }

    public CalonPengajarModel getCalonPengajar() {
        return calonPengajar;
    }

    public void setCalonPengajar(CalonPengajarModel calonPengajar) {
        this.calonPengajar = calonPengajar;
    }
//
//    public List<JawabanModel> getListJawaban(){
//        return listJawaban;
//    }
//
//    public void setListJawaban(List<JawabanModel> listJawaban){
//        this.listJawaban = listJawaban;
//    }
//
//    public void addJawaban(JawabanModel jawaban){
//        this.listJawaban.add(jawaban);
//    }


    public SubmittedPaketSoalModel getSubmittedPaketSoal() {
        return submittedPaketSoal;
    }

    public void setSubmittedPaketSoal(SubmittedPaketSoalModel submittedPaketSoal) {
        this.submittedPaketSoal = submittedPaketSoal;
    }
}
