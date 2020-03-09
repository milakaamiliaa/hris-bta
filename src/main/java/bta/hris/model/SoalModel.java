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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "soal_id", referencedColumnName= "idPaket", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PaketSoalModel paketSoal;

}
