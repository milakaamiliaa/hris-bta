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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "soal", referencedColumnName= "idSoal", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SoalModel soal;

}
