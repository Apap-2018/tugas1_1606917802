package com.apap.tugas1.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiDb extends JpaRepository<ProvinsiModel, Long>{
	

}
