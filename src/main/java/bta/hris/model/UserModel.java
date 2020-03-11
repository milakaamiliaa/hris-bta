package bta.hris.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotNull
    @Size(max = 255)
    @Column(name = "nip", nullable = false)
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @NotNull
    @Size(max = 255)
    @Column(name = "noTelp", nullable = false)
    private String noTelp;

    @NotNull
    @Column(name = "tglLahir",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date tglLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @NotNull
    @Column(name = "isActive", nullable = false)
    private boolean isActive;

    @Size(max = 255)
    @Column(name = "mataPelajaran", nullable = true)
    private String mataPelajaran;

    @Column(name = "noRekening", nullable = true)
    private Long noRekening;

    @Column(name = "namaBank", nullable = true)
    private String namaBank;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role",referencedColumnName = "idRole", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoleModel role;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "golonganPegawai", referencedColumnName= "idGolongan", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GolonganModel golongan;

    @OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GajiModel> listGaji;
}