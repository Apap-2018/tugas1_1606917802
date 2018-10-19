package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiDetailByNip(String pegawaiNip);
	
	List<PegawaiModel> getListOrderByTanggalLahir(InstansiModel instansi);
	
	List<PegawaiModel> getAll();
	
	void add(PegawaiModel pegawai);
	
	void updatePegawai(PegawaiModel pegawai);
}
