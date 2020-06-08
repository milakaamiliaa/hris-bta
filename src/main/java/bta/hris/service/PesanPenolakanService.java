package bta.hris.service;

import bta.hris.model.PesanPenolakanModel;
import bta.hris.model.PresensiModel;

import java.util.List;

public interface PesanPenolakanService {
    PesanPenolakanModel addPesanPenolakan(PesanPenolakanModel pesan);

    List<PesanPenolakanModel> getAllPesanPenolakanByPresensi(PresensiModel presensi);
}
