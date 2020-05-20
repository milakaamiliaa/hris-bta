package bta.hris.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "calonPengajar")
public class CalonPengajarModel implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy="uuid")
    private String idCalon;

    @NotNull
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "alamat", nullable = false)
    private String alamat;

    @NotNull
    @Column(name = "noTelp", nullable = false)
    private String noTelp;

    @NotNull
    @Column(name = "tglLahir",nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tglLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "createdAt", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "cv", nullable = false)
    private String cv;

    @NotNull
    @Column(name = "mataPelajaran", nullable = false)
    private String mataPelajaran;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "universitas", nullable = false)
    private String universitas;

    @Column(name = "nilaiPsikotes",nullable = true)
    private Float nilaiPsikotes;

    @Column(name = "nilaiMataPelajaran",nullable = true)
    private Float nilaiMataPelajaran;

    @NotNull
    @Column(name = "updatedAt",nullable = true)
    private LocalDate updatedAt;

    @NotNull
    @Column(name = "tesDeadline", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tesDeadline;

    @OneToMany(mappedBy = "calonPengajar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<HasilTesModel> listHasilTes;

    public String getIdCalon() {
        return idCalon;
    }

    public void setIdCalon(String idCalon) {
        this.idCalon = idCalon;
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

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getMataPelajaran() {
        return mataPelajaran;
    }

    public void setMataPelajaran(String mataPelajaran) {
        this.mataPelajaran = mataPelajaran;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getNilaiPsikotes() {
        return nilaiPsikotes;
    }

    public void setNilaiPsikotes(Float nilaiPsikotes) {
        this.nilaiPsikotes = nilaiPsikotes;
    }

    public Float getNilaiMataPelajaran() {
        return nilaiMataPelajaran;
    }

    public void setNilaiMataPelajaran(Float nilaiMataPelajaran) {
        this.nilaiMataPelajaran = nilaiMataPelajaran;
    }

    public LocalDate getTesDeadline() {
        return tesDeadline;
    }

    public void setTesDeadline(LocalDate tesDeadline) {
        this.tesDeadline = tesDeadline;
    }

    public List<HasilTesModel> getListHasilTes() {
        return listHasilTes;
    }

    public void setListHasilTes(List<HasilTesModel> listHasilTes) {
        this.listHasilTes = listHasilTes;
    }

    public LocalDate getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt){
        this.updatedAt = updatedAt;
    }
  
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUniversitas() {
        return universitas;
    }

    public void setUniversitas(String universitas) {
        this.universitas = universitas;
    }
}
