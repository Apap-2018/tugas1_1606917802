package com.apap.tugas1.service;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
    @Autowired
    private PegawaiDb pegawaiDb;
 
    @Override
    public Optional<PegawaiModel> getPegawaiDetailByNip(String nip) {
        return pegawaiDb.findByNip(nip);
    }

	@Override
	public List<PegawaiModel> getListOrderByTanggalLahir(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public List<PegawaiModel> getAll() {
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}

	@Override
	public void add(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
	}


}