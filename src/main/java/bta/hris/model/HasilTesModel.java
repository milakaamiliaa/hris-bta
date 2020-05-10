package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "hasilTes")
public class HasilTesModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHasil;

    @NotNull
    @Column(name = "jawabanTerpilih", nullable = false)
    private Map<Long, Long> jawabanTerpilih = new HashMap<Long, Long>();

    @NotNull
    @Column(name = "startedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startedAt;

    @NotNull
    @Column(name = "finishedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate finishedAt;

    @NotNull
    @Column(name = "nilai", nullable = false)
    private Long nilai;

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

    public Map<Long,Long> getJawabanTerpilih() {
        return jawabanTerpilih;
    }

    public void setJawabanTerpilih(Map<Long,Long> jawabanTerpilih) {
        this.jawabanTerpilih = jawabanTerpilih;
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

    public Long getNilai() {
        return nilai;
    }

    public void setNilai(Long nilai) {
        this.nilai = nilai;
    }

    public CalonPengajarModel getCalonPengajar() {
        return calonPengajar;
    }

    public void setCalonPengajar(CalonPengajarModel calonPengajar) {
        this.calonPengajar = calonPengajar;
    }
}
