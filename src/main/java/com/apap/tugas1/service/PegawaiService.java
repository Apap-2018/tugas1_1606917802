package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface PegawaiService {
	Optional<PegawaiModel> getPegawaiDetailByNip(String pegawaiNip);
	
	List<PegawaiModel> getListOrderByTanggalLahir(InstansiModel instansi);
	
	List<PegawaiModel> getAll();
	
	void add(PegawaiModel pegawai);
	
	void updatePegawai(PegawaiModel pegawai);
		
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	
	void updatePegawai(String nip, PegawaiModel pegawai);
}
