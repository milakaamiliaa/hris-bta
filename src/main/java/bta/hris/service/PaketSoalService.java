package bta.hris.service;

import bta.hris.model.PaketSoalModel;

import java.util.List;
import java.util.Optional;

public interface PaketSoalService {
    List<PaketSoalModel> getAllPaketsoal();
    PaketSoalModel addPaketSoal(PaketSoalModel paketSoal);
    PaketSoalModel getPaketSoalByIdPaket (Long idPaket);
    PaketSoalModel updatePaketSoal(PaketSoalModel paketSoal);
    PaketSoalModel deletePaketSoal(PaketSoalModel paketSoal);
}
