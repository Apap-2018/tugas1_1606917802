package com.apap.tugas1.model;
import java.util.List;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.apap.tugas1.model.InstansiModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="jabatan")
public class JabatanModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name= "nama", nullable= false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name= "deskripsi", nullable= false)
	private String deskripsi;
	
	@NotNull
	@Column(name= "gaji_pokok", nullable= false)
	private Double gaji_pokok;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="jabatan_pegawai", joinColumns = {@JoinColumn (name = "id_jabatan", referencedColumnName="id", nullable = false)},
	inverseJoinColumns = {@JoinColumn (name="id_pegawai", referencedColumnName="id", nullable = false)})
	private List<PegawaiModel> pegawai;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PegawaiModel> getPegawai() {
		return pegawai;
	}

	public void setPegawai(List<PegawaiModel> pegawai) {
		this.pegawai = pegawai;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public Double getGaji_pokok() {
		return gaji_pokok;
	}

	public void setGaji_pokok(Double gaji_pokok) {
		this.gaji_pokok = gaji_pokok;
	}
	
}
