package bta.hris.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class UserModel implements Serializable{


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String idUser;

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

//    public Long getIdUser() {
//        return idUser;
//    }
//
//    public void setIdUser(Long idUser) {
//        this.idUser = idUser;
//    }

    @NotNull
    @Size(max = 255)
    @Column(name = "noTelp", nullable = false)
    private String noTelp;

    @NotNull
    @Column(name = "tglLahir",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tglLahir;

    @NotNull
    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

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
    @JsonIgnore
    private RoleModel role;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "golonganPegawai", referencedColumnName= "idGolongan", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GolonganModel golongan;

    @OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GajiModel> listGaji;

    @OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PresensiModel> listPresensi;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }


    public LocalDate getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(LocalDate tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public Long getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(Long noRekening) {
        this.noRekening = noRekening;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public GolonganModel getGolongan() {
        return golongan;
    }

    public void setGolongan(GolonganModel golongan) {
        this.golongan = golongan;
    }

    public List<GajiModel> getListGaji() {
        return listGaji;
    }

    public void setListGaji(List<GajiModel> listGaji) {
        this.listGaji = listGaji;
    }

    public List<PresensiModel> getListPresensi() {
        return listPresensi;
    }

    public void setListPresensi(List<PresensiModel> listPresensi) {
        this.listPresensi = listPresensi;
    }
}