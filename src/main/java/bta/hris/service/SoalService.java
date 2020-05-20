package bta.hris.service;

import bta.hris.model.PaketSoalModel;
import bta.hris.model.SoalModel;

import java.util.List;

public interface SoalService {
    List<SoalModel> getSoalByPaketSoal(PaketSoalModel paketSoal);
    SoalModel addSoal(SoalModel soal);
    SoalModel getSoalById(Long idSoal);
    SoalModel editSoal(SoalModel soal);
    SoalModel deleteSoal(SoalModel soal);
}
