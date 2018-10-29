package com.apap.tugas1.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

import java.util.Optional;

import com.apap.tugas1.model.PegawaiModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	Optional<PegawaiModel> findByNip(String nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	
	List<PegawaiModel> findByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);

	
 	}
