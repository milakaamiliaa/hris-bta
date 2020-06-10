package bta.hris.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pesanPenolakan")
public class PesanPenolakanModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPesanPenolakan;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

    @NotNull
    @Column(name = "pesan", nullable = false)
    private String pesan;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "presensi", referencedColumnName = "idPresensi", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PresensiModel presensi;

    public Long getIdPesanPenolakan() {
        return idPesanPenolakan;
    }

    public void setIdPesanPenolakan(Long idPesanPenolakan) {
        this.idPesanPenolakan = idPesanPenolakan;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public PresensiModel getPresensi() {
        return presensi;
    }

    public void setPresensi(PresensiModel presensi) {
        this.presensi = presensi;
    }
}
