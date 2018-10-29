package com.apap.tugas1.service;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.PegawaiDb;

import java.util.Date;
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

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
	}


	@Override
	public void updatePegawai(String nip, PegawaiModel pegawai) {
		PegawaiModel updatePegawai = pegawaiDb.findByNip(nip).get();
		updatePegawai.setNama(pegawai.getNama());
		updatePegawai.setNip(pegawai.getNip());
		updatePegawai.setTanggalLahir(pegawai.getTanggalLahir());
		updatePegawai.setTempatLahir(pegawai.getTempatLahir());
		updatePegawai.setTahunMasuk(pegawai.getTahunMasuk());
			updatePegawai.setInstansi(pegawai.getInstansi());
			updatePegawai.setListJabatan(pegawai.getListJabatan());
			pegawaiDb.save(updatePegawai);
		}

}