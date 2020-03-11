package bta.hris.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "golongan")
public class GolonganModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGolongan;

    @NotNull
    @Column(name = "nama",nullable = false)
    private String nama;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Long rate;

    @NotNull
    @Column(name = "pajak",nullable = false)
    private Float pajak;

    @OneToMany(mappedBy = "golongan", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<UserModel> listPegawai;

}
