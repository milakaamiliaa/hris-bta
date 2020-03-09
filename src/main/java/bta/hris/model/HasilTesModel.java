package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
    private Date startedAt;

    @NotNull
    @Column(name = "finishedAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date finishedAt;

    @NotNull
    @Column(name = "chosenAnswer", nullable = false)
    private JawabanModel chosenAnswer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "hasilTes_id", referencedColumnName= "idCalon", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CalonPengajarModel calonPengajar;
}
