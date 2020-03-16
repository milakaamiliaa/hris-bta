package bta.hris.service;

import bta.hris.model.PresensiModel;

import java.util.List;

public interface PresensiService {
    PresensiModel getPresensiById(Long id);
    List<PresensiModel> getPresensiList();
    void addPresensi(PresensiModel presensi);
}
