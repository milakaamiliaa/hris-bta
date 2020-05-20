package bta.hris.service;

import bta.hris.model.CabangModel;
import bta.hris.model.GajiModel;
import bta.hris.model.GolonganModel;
import bta.hris.model.PresensiModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GajiService {
   GajiModel addGaji(GajiModel gaji);
   Optional<GajiModel> getGajiByIdGaji(Long idGaji);
   List<GajiModel> getAllGajiByNip(String nip);
   GajiModel updateGaji(GajiModel gaji);
   List<GajiModel> getAllGaji();
   GajiModel approveGaji(GajiModel gaji);
   GajiModel rejectGaji(GajiModel gaji);
   GajiModel paidGaji(GajiModel gaji);
   GajiModel getGajiCabangMonthly(CabangModel cabang, LocalDate periode);
   List<GajiModel> getAllGajiPengajarCabangMonthly(CabangModel cabang, LocalDate periode);
   ArrayList<GajiModel> listSortedByTotalGaji(ArrayList<GajiModel> gajiModelList);


}
