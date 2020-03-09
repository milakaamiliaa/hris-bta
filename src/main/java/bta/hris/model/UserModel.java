package bta.hris.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel implements Serializable{
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private Long idUser;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "username", nullable = false)
    private String username;

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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id",referencedColumnName = "idRole", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoleModel role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private CalonPengajarModel calonPengajar;
}