package bta.hris.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "paketSoal")
public class PaketSoalModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaket;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAt;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "paketSoal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SoalModel> listSoal;

}
