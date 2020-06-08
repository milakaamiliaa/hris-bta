package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;

import java.util.List;

public interface SoalService {
    List<SoalModel> getAllSoalByNamaPaketSoal(String nama);

    List<SoalModel> getAllSoalByIdPaketSoal(Long idPaket);

    SoalModel getSoalById(Long idSoal);

    List<SoalModel> getSoalByPaketSoal(PaketSoalModel paketSoal);

    SoalModel addSoal(SoalModel soal);

    SoalModel editSoal(SoalModel soal);

    SoalModel deleteSoal(SoalModel soal);
}
