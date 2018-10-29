package com.apap.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
	    
	    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
		private String cari(@RequestParam(value="idProvinsi", required=false) String idProvinsi, @RequestParam(value="idInstansi", required=false) String idInstansi, @RequestParam(value="idJabatan", required= false) String idJabatan, Model model) {
			List<JabatanModel> allJabatan = jabatanService.allJabatan();
			List<ProvinsiModel> allProvinsi = provinsiService.allProvinsi();
			List<InstansiModel> allInstansi = instansiService.allInstansi();
			List<PegawaiModel> selectedPegawai = new ArrayList<PegawaiModel>();
			model.addAttribute("instansi", allInstansi);
			model.addAttribute("pegawai", selectedPegawai);
			model.addAttribute("provinsi",allProvinsi);
			model.addAttribute("jabatan",allJabatan);
			
			
			if(idInstansi != null && idJabatan != null && idProvinsi != null){
				//provinsi aja
				if((idProvinsi != "" && idInstansi == "" && idJabatan == "")) {
					ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(Long.parseLong(idProvinsi)).get();
					List<InstansiModel> selectedInstansi = provinsi.getListInstansi();
					for(InstansiModel e: selectedInstansi) {
						for(PegawaiModel p: e.getListPegawai()) {
							selectedPegawai.add(p);
						}
					}
				}
				//instansi atau instansi + provinsi
				else if((idProvinsi == "" && idInstansi != "" && idJabatan == "") || (idProvinsi != "" && idInstansi != "" && idJabatan == "")) {
					InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(idInstansi)).get();
					for (PegawaiModel e : instansi.getListPegawai()) {
						selectedPegawai.add(e);
					}
				}
				//jabatan aja
				else if(idProvinsi == "" && idInstansi == "" && idJabatan != "") {
					JabatanModel jabatan = jabatanService.getjabatanDetailById(Long.parseLong(idJabatan)).get();
					for (PegawaiModel e: jabatan.getPegawai()) {
						selectedPegawai.add(e);
					}
				}
				//jabatan + instansi
				else if(idProvinsi == "" && idInstansi != "" && idJabatan != "") {
					InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(idInstansi)).get();
					List<PegawaiModel> listpegawaiInstansi = pegawaiService.getPegawaiByInstansi(instansi);
					for(PegawaiModel e: listpegawaiInstansi) {
						for(JabatanModel j: e.getListJabatan()) {
							if(j.getId() == (Long.parseLong(idJabatan))) {
								selectedPegawai.add(e);
							}
						}
					}
				}
				//jabatan + provinsi
				else if(idProvinsi != "" && idInstansi == "" && idJabatan != "") {
					ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(Long.parseLong(idProvinsi)).get();
					List<InstansiModel> instansi = provinsi.getListInstansi();
					ArrayList<PegawaiModel> pegawai = new ArrayList<PegawaiModel>();
					for(InstansiModel i: instansi) {
						for(PegawaiModel p: i.getListPegawai()) {
							pegawai.add(p);
						}
					}
					for(PegawaiModel p: pegawai) {
						for(JabatanModel j: p.getListJabatan()) {
							if(j.getId() == (Long.parseLong(idJabatan))) {
								selectedPegawai.add(p);
							}
						}
					}
				}
				//tiga2nya
				else {
					InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(idInstansi)).get();
					List<PegawaiModel> listpegawai = pegawaiService.getPegawaiByInstansi(instansi);
					for(PegawaiModel p: listpegawai) {
						for(JabatanModel j: p.getListJabatan()) {
							if(j.getId() == (Long.parseLong(idJabatan))) {
								selectedPegawai.add(p);
							}
						}
					}
				}
				model.addAttribute("pegawai", selectedPegawai);
				return "cari-pegawai";
			}
			
			else {
			model.addAttribute("pegawai", selectedPegawai);
			return "cari-pegawai";
			}
	    }
	    
		@RequestMapping (value = "/pegawai/cekInstansi", method = RequestMethod.GET)
		public @ResponseBody Object coba (@RequestParam ("id") String idProvinsi, Model model) {
			List <InstansiModel> instansi = new ArrayList <InstansiModel>();
			System.out.println(idProvinsi);
			if (idProvinsi.equalsIgnoreCase("0")) {
				//System.out.println("masuk 0");
				instansi = instansiService.allInstansi();	
			}
			else {
				ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(Long.parseLong(idProvinsi)).get();
				instansi = provinsi.getListInstansi();
			}
			Object inst = instansi;
			return inst;
		}
		
		@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
		private String add(Model model) {
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			//default
			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProv.get(0));
			
			PegawaiModel pegawai = new PegawaiModel();
			pegawai.setListJabatan(new ArrayList<JabatanModel>());
			pegawai.getListJabatan().add(new JabatanModel());
			
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("listInstansi", listInstansi);
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			return "add-pegawai";
		}
	
		@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
		public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
			
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			
			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("listInstansi", listInstansi);
			pegawai.getListJabatan().add(new JabatanModel());
		    model.addAttribute("pegawai", pegawai);
		    return "add-pegawai";
		}
		
		@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
		public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
			
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			

			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("listInstansi", listInstansi);
			
			Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
			System.out.println(rowId);
			pegawai.getListJabatan().remove(rowId.intValue());
		    model.addAttribute("pegawai", pegawai);
		    return "add-pegawai";
		}
		
		@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
		private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
			String kode = Long.toString((pegawai.getInstansi().getId()));
			
			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
			System.out.println(pegawai.getTanggalLahir());
			String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
			
			String tahunKerja = pegawai.getTahunMasuk();

			int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
			
			String strUrutan;
			if(urutan<10) strUrutan="0"+urutan;
			else strUrutan=""+urutan;
			
			String nip = kode + tanggalLahir + tahunKerja + strUrutan;
			
			pegawai.setNip(nip);
			
			pegawaiService.add(pegawai);
			
			String msg = "Pegawai dengan NIP "+ nip +" berhasil ditambahkan";
			model.addAttribute("message", msg);
			return "result";
		}
		
		@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
		public String updatePegawai(@RequestParam("nip") String nip, Model model) {
			
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			
			PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip).get();

			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("listInstansi", listInstansi);
			
		    model.addAttribute("pegawai", pegawai);
		    return "update-pegawai";
		}
		
		@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
		public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
			
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			
			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("listInstansi", listInstansi);
			
			ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
			model.addAttribute("selectedItem", provinsi);
			
			
			pegawai.getListJabatan().add(new JabatanModel());
		    model.addAttribute("pegawai", pegawai);
		    return "update-pegawai";
		}
		
		@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
		public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
			
			List<ProvinsiModel> listProv = provinsiService.allProvinsi();
			List<JabatanModel> listJabatan = jabatanService.allJabatan();
			
			model.addAttribute("listJabatan", listJabatan);
			model.addAttribute("listProvinsi", listProv);
			
			List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
			model.addAttribute("listInstansi", listInstansi);
			
			Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
			pegawai.getListJabatan().remove(rowId.intValue());
		    model.addAttribute("pegawai", pegawai);
		    return "update-pegawai";
		}
		
		@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
		private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
			String oldNip = pegawai.getNip();
			PegawaiModel oldPegawai = pegawaiService.getPegawaiDetailByNip(oldNip).get();
			
			String newNip;
			if((!oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk())) || 
					(!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir())) || 
					(!oldPegawai.getInstansi().equals(pegawai.getInstansi()))) {
				
				String kode = Long.toString(pegawai.getInstansi().getId());
				
				SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
				String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
				
				String tahunKerja = pegawai.getTahunMasuk();
				
				int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
				
				String strUrutan;
				if(urutan<10) strUrutan="0"+urutan;
				else strUrutan=""+urutan;
				
				newNip = kode + tanggalLahir + tahunKerja + strUrutan;
				pegawai.setNip(newNip);
			}
			else {
				 newNip = oldNip;
				 pegawai.setNip(oldNip);
			}
			
			
			pegawaiService.updatePegawai(oldNip, pegawai);
			
			String msg = "Pegawai dengan NIP "+ newNip +" berhasil diubah";
			model.addAttribute("message", msg);
			return "result";
		}
}


