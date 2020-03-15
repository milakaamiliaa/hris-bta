package bta.hris.service;

import bta.hris.model.CabangModel;

import java.util.List;

public interface CabangService {
    List<CabangModel> getAllCabang();

    //di controller, ngeluarin id atau nama cabang biar pas di html pas ngeluarin bentuknya id jadi apss assign variabel cabang, if cabang == 1. get cabang by id.
}
