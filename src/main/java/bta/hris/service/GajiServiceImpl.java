package bta.hris.service;

import bta.hris.model.*;
import bta.hris.repository.GajiDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GajiServiceImpl implements GajiService {

    @Autowired
    private GajiDB gajiDB;

    @Autowired
    private UserService userService;

    @Autowired
    private PresensiService presensiService;

    @Override
    public GajiModel addGaji(GajiModel gaji) {
        return gajiDB.save(gaji);
    }

    @Override
    public Optional<GajiModel> getGajiByIdGaji(Long idGaji) {
        return gajiDB.findById(idGaji);
    }

    @Override
    public List<GajiModel> getAllGajiByNip(String nip) {
        UserModel user = userService.getByNip(nip);
        return gajiDB.findAllByPegawai(user);
    }

    @Override
    public GajiModel updateGaji(GajiModel gaji) {
        return gajiDB.save(gaji);
    }

    @Override
    public List<GajiModel> getAllGaji() {
        return gajiDB.findAll();
    }

    @Override
    public GajiModel approveGaji(GajiModel gaji) {
        GajiModel newGaji = gajiDB.findById(gaji.getIdGaji()).get();

        newGaji.setTotalGaji(gaji.getTotalGaji());
        newGaji.setTotalSesi(gaji.getTotalSesi());
        newGaji.setRateGaji(gaji.getRateGaji());
        newGaji.setPajakGaji(gaji.getPajakGaji());
        newGaji.setStatus(gaji.getStatus());

        gajiDB.save(newGaji);

        return newGaji;
    }

    @Override
    public GajiModel rejectGaji(GajiModel gaji) {
        GajiModel targetGaji = gajiDB.findById(gaji.getIdGaji()).get();
        try {
            targetGaji.setStatus("ditolak");
            gajiDB.save(targetGaji);
            return targetGaji;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public GajiModel paidGaji(GajiModel gaji) {
        GajiModel targetGaji = gajiDB.findById(gaji.getIdGaji()).get();
        try {
            targetGaji.setStatus("sudah dibayar");
            gajiDB.save(targetGaji);
            return targetGaji;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public GajiModel getGajiCabangMonthly(CabangModel cabang, LocalDate periode) {
        GajiModel gajiCabang = new GajiModel();
        Long totalSesi = Long.valueOf(0);
        Float totalGaji = (float) 0;
        List<PresensiModel> presensiCabang = presensiService.getAllPresensiByCabang(cabang);
        if (presensiCabang.size() == 0) {
            gajiCabang.setStatus("sudah dibayar");
            gajiCabang.setPeriode(periode);
            gajiCabang.setTotalGaji(totalGaji);
            gajiCabang.setTotalSesi(totalSesi);
            gajiCabang.setPajakGaji((float) 0);
            gajiCabang.setRateGaji(Long.valueOf(1));
            gajiCabang.setPegawai(userService.createDummyUser());
            gajiDB.save(gajiCabang);

        } else {
            for (PresensiModel presensi : presensiCabang) {
                if (presensi.getStatus().equalsIgnoreCase("disetujui")) {
                    if (presensi.getTanggalPresensi().getMonthValue() == periode.getMonthValue()
                            && presensi.getTanggalPresensi().getYear() == periode.getYear()) {
                        if (presensi.getSesiTambahan() != null) {
                            totalSesi = totalSesi + presensi.getSesiMengajar() + presensi.getSesiTambahan();
                        } else {
                            totalSesi = totalSesi + presensi.getSesiMengajar();
                        }
                        totalGaji = totalGaji + presensi.getNominal();
                    }

                }

            }
            gajiCabang.setStatus("sudah dibayar");
            gajiCabang.setPeriode(periode);
            gajiCabang.setTotalGaji(totalGaji);
            gajiCabang.setTotalSesi(totalSesi);
            gajiCabang.setPajakGaji((float) 0);
            gajiCabang.setRateGaji(Long.valueOf(1));
            gajiCabang.setPegawai(userService.createDummyUser());
            gajiDB.save(gajiCabang);

        }
        return gajiCabang;

    }

    @Override
    public List<GajiModel> getAllGajiPengajarCabangMonthly(CabangModel cabang, LocalDate periode) {
        List<PresensiModel> presensiCabang = presensiService.getAllPresensiByCabangAndStatus(cabang, "disetujui");
        ArrayList<GajiModel> gajiPengajarList = new ArrayList<>();
        ArrayList<GajiModel> gajiPengajarListSorted = new ArrayList<>();
        if (presensiCabang.size() != 0) {
            UserModel pengajarPertama = new UserModel();
            for (int i = 0; i < presensiCabang.size(); i++) {
                if (presensiCabang.get(i).getPegawai().getNip() != pengajarPertama.getNip()) {
                    GajiModel gaji = new GajiModel();
                    pengajarPertama = presensiCabang.get(i).getPegawai();
                    gaji.setPegawai(userService.getUserById(pengajarPertama.getIdUser()));
                    gaji.setStatus("sudah dibayar");
                    gaji.setPeriode(periode);
                    gaji.setTotalGaji(presensiCabang.get(i).getNominal());
                    gaji.setRateGaji(Long.valueOf(1));
                    gaji.setPajakGaji((float) 0);
                    gaji.setTotalSesi(Long.valueOf(0));

                    if (presensiCabang.get(i).getSesiTambahan() == null) {
                        gaji.setTotalSesi(gaji.getTotalSesi() + presensiCabang.get(i).getSesiMengajar());
                    } else {
                        gaji.setTotalSesi(gaji.getTotalSesi() + presensiCabang.get(i).getSesiMengajar()
                                + presensiCabang.get(i).getSesiTambahan());
                    }

                    gajiDB.save(gaji);
                    GajiModel gajiAdd = getGajiByIdGaji(gaji.getIdGaji()).get();
                    gajiPengajarList.add(getGajiByIdGaji(gaji.getIdGaji()).get());

                }

                else if (presensiCabang.get(i).getPegawai().getNip() == pengajarPertama.getNip()) {
                    GajiModel gaji = gajiPengajarList.get(gajiPengajarList.size() - 1);
                    gaji.setTotalGaji(gaji.getTotalGaji() + presensiCabang.get(i).getNominal());
                    if (presensiCabang.get(i).getSesiTambahan() == null) {
                        gaji.setTotalSesi(gaji.getTotalSesi() + presensiCabang.get(i).getSesiMengajar());
                    } else {
                        gaji.setTotalSesi(gaji.getTotalSesi() + presensiCabang.get(i).getSesiMengajar()
                                + presensiCabang.get(i).getSesiTambahan());
                    }

                }

            }
            gajiPengajarListSorted = listSortedByTotalGaji(gajiPengajarList);

        }

        return gajiPengajarListSorted;
    }

    @Override
    public ArrayList<GajiModel> listSortedByTotalGaji(ArrayList<GajiModel> gajiModelList) {
        ArrayList<GajiModel> sortedList = new ArrayList<>();
        GajiModel first = gajiModelList.get(0);
        for (int i = 1; i < gajiModelList.size(); i++) {
            if (gajiModelList.get(i).getTotalGaji() > first.getTotalGaji()) {
                sortedList.add(gajiModelList.get(i));
            } else {
                sortedList.add(first);
                first = gajiModelList.get(i);
            }

        }
        sortedList.add(first);
        return sortedList;

    }

}
