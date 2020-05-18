package bta.hris.controller;

import bta.hris.model.JawabanModel;
import bta.hris.model.SoalModel;

import java.util.ArrayList;
import java.util.List;

public class SoalAndJawaban {
    SoalModel soal;
    List<JawabanModel> jawabanList;

    public SoalAndJawaban(int jumlahJawaban) {
        soal = new SoalModel();

        jawabanList = new ArrayList<JawabanModel>();
        for (int i = 0; i < jumlahJawaban; i++) {
            jawabanList.add(new JawabanModel());
        }
    }

    public SoalModel getSoal() {
        return soal;
    }

    public void setSoal(SoalModel soal) {
        this.soal = soal;
    }

    public List<JawabanModel> getJawabanList() {
        return jawabanList;
    }

    public void setJawabanList(List<JawabanModel> jawabanList) {
        this.jawabanList = jawabanList;
    }
}
