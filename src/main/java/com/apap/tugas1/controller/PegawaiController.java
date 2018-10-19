package com.apap.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;


@Controller
public class PegawaiController {
	
	@Autowired
    private PegawaiService pegawaiService;
	
	@Autowired
    private JabatanService jabatanService;

	@Autowired
    private InstansiService instansiService;
	
	@Autowired
    private ProvinsiService provinsiService;
	 
	    @RequestMapping("/")
	    private String home(Model model) {
	    	List<JabatanModel> listJabatan = jabatanService.allJabatan();
	    	List<InstansiModel> listInstansi = instansiService.allInstansi();
	    	ArrayList<String> listProvinsi = new ArrayList<String>();
	    	for (InstansiModel e:listInstansi) {
	    		listProvinsi.add(e.getProvinsi().getNama());
	    	}
	    	model.addAttribute("jabatan", listJabatan);
	    	model.addAttribute("instansi", listInstansi);
	        return "home";
	    }
	 
	    @RequestMapping(value = "/pegawai/", method = RequestMethod.GET)
	    private String view(@RequestParam(value = "nip") String pegawaiNip, Model model) {
			if(pegawaiService.getPegawaiDetailByNip(pegawaiNip).isPresent()) {
		        PegawaiModel archivePegawai = pegawaiService.getPegawaiDetailByNip(pegawaiNip).get();
		        model.addAttribute("pegawai", archivePegawai);
		        model.addAttribute("instansi", archivePegawai.getInstansi().getNama());
		        model.addAttribute("provinsi", archivePegawai.getInstansi().getProvinsi().getNama());
		        model.addAttribute("jabatan", archivePegawai.getListJabatan());
		        model.addAttribute("gaji", archivePegawai.getGaji());
		        return "view-pegawai";
			}
			else {
			return "pegawai-not-found";
			}
	    }
	    
	    @RequestMapping("/pegawai/cari")
		private String cari(Model model) {
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			List<InstansiModel> listInstansi = instansiService.allInstansi();
			List<ProvinsiModel> listProvinsi = provinsiService.allProvinsi();
			model.addAttribute("instansi",listInstansi);
			model.addAttribute("provinsi",listProvinsi);
			model.addAttribute("jabatan",listJabatan);
			return "cari-pegawai";
	    }
	    
	    @RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	    private String update(@RequestParam(value = "nipPegawai") String pegawaiNip, Model model) {
	        PegawaiModel temp = pegawaiService.getPegawaiDetailByNip(pegawaiNip).get();
	        PegawaiModel baru = new PegawaiModel();
	        baru.setId(temp.getId());
	        List<JabatanModel> listJabatan = jabatanService.allJabatan();
	    	List<ProvinsiModel> listProvinsi = provinsiService.allProvinsi();
	        List<InstansiModel> listInstansi = instansiService.allInstansi();
//	        InstansiModel a = new InstansiModel();
//	        a.setNama("hahahahahahhahahahhxadashdasjdwdhahaha");
//	        listInstansi.add(a);
//	        for(InstansiModel e: listInstansi) {
//	        	System.out.println(e.getNama());
//	        }
	       
	    	model.addAttribute("provinsi", listProvinsi);
	    	model.addAttribute("jabatan", listJabatan);
	    	model.addAttribute("instansi", listInstansi);
	        model.addAttribute("pegawailama", temp);
	        model.addAttribute("pegawaibaru", baru);
	        return "update-pegawai";
	    }
	 
	 @RequestMapping(value = "/pegawai/update", method = RequestMethod.POST)
	    private String updateSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		 	PegawaiModel temp = pegawaiService.getPegawaiDetailByNip(pegawai.getNip()).get();
		 	temp.setNama(pegawai.getNama());
			temp.setTempat_lahir(pegawai.getTempat_lahir());
			temp.setTanggalLahir(pegawai.getTanggalLahir());
			temp.setTahun_masuk(pegawai.getTahun_masuk());
			temp.setInstansi(pegawai.getInstansi());
			temp.setListJabatan(pegawai.getListJabatan());
	        pegawaiService.updatePegawai(temp);
	        return "update-jabatan-success";
	    }
	 
	    @RequestMapping(value="/pegawai/tambah", method = RequestMethod.POST, params= {"addRow"})
	    	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
	    	if (pegawai.getListJabatan()==null) {
	    		pegawai.setListJabatan(new ArrayList());
	    	}
	    	pegawai.getListJabatan().add(new JabatanModel());
	    	List<JabatanModel> listJabatan = jabatanService.allJabatan();
	    	List<ProvinsiModel> listProvinsi = provinsiService.allProvinsi();
	    	model.addAttribute("listProvinsi", listProvinsi);
	    	model.addAttribute("listJataban", listJabatan);
	    	model.addAttribute("pegawai", pegawai);
	    	return "add-pegawai";
	    	}
	    
	    @RequestMapping(value = "/pegawai/tambah")
		private String tambahPegawai(Model model) {
			PegawaiModel peg = new PegawaiModel();
			if (peg.getListJabatan()==null) {
				peg.setListJabatan(new ArrayList());
			}
			peg.getListJabatan().add(new JabatanModel());
			List<ProvinsiModel> prov = provinsiService.allProvinsi();
			List<JabatanModel> jab = jabatanService.allJabatan();
			model.addAttribute("listJabatan",jab);
			model.addAttribute("pegawai", peg);
			model.addAttribute("listProvinsi", prov);
			return "add-pegawai";
		}
	    
	    @RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
		private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
	    	String nipPegawai = generateNip(pegawai);
	    	pegawai.setNip(nipPegawai);
	    	pegawaiService.add(pegawai);
	    	return "add-pegawai-success";
	    }
	    
	    @RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
		private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="idProvinsi") long provinsiId) {
			ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(provinsiId).get();
			return provinsi.getListInstansi();
		}
	    
	    private String generateNip(PegawaiModel pegawai) {
			DateFormat df = new SimpleDateFormat("ddMMYY");
			Date tglLahir = pegawai.getTanggalLahir();
			String formatted = df.format(tglLahir);			
			Long kodeInstansi = pegawai.getInstansi().getId();			
			int idAkhir = 0;
			for (PegawaiModel peg : pegawaiService.getAll()) {
				if (peg.getTanggalLahir().equals(pegawai.getTanggalLahir()) && peg.getTahun_masuk().equals(pegawai.getTahun_masuk())) {
					idAkhir+=1;
				}
			}
			idAkhir+=1;
			
			String kodeMasuk = "";
			if (idAkhir<10) {
				kodeMasuk = "0"+idAkhir;
			}
			else {
				kodeMasuk = Integer.toString(idAkhir);
			}	
			return kodeInstansi+formatted+pegawai.getTahun_masuk()+kodeMasuk;
			
		}
	
}

