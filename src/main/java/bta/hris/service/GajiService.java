package bta.hris.service;

import bta.hris.model.GajiModel;
import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;

import java.util.List;

public interface GajiService {
   GajiModel addGaji(GajiModel gaji);
   List<GajiModel> getAllGajiByNip(String nip);
   GajiModel updateGaji(GajiModel gaji);
   List<GajiModel> getAllGaji();

}
