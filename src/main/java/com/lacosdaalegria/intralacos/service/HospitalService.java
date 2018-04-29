package com.lacosdaalegria.intralacos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lacosdaalegria.intralacos.model.atividade.Hospital;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.atividade.HospitalRepository;
import com.lacosdaalegria.intralacos.repository.s3.S3;
import com.lacosdaalegria.intralacos.repository.usuario.VoluntarioRepository;

@Service
@Transactional
public class HospitalService {
	
	@Autowired
	HospitalRepository repository;
	@Autowired
	VoluntarioRepository voluntario;
	@Autowired
	private S3 s3;
	
	public Iterable<Hospital> getAllActive(){
		return repository.findByStatus(1);
	}
	public Iterable<Hospital> getAll(){
		return repository.findAll();
	}
	public Iterable<Hospital> getHospitalNovatos(){
		return repository.findByNovatosTrue();
	}
	public Iterable<Voluntario> top30Novatos(Hospital hospital){
		return voluntario.findTop30ByPreferenciaAndStatusAndPromovidoFalseOrderByDtCriacao(hospital, 1);
	}
	
	public void updateHospitalCoordenador(Hospital hospital) {
		Hospital hospital_ = repository.findById(hospital.getId()).get();
		hospital_.updateCoordenador(hospital);
		repository.save(hospital_);
	}
	
	public void updateHospitalDiretoria(Hospital hospital) {
		Hospital hospital_ = repository.findById(hospital.getId()).get();
		hospital_.updateDiretor(hospital);
		repository.save(hospital_);
	}
	
	public void updateImage(MultipartFile file, Hospital hospital) {
		String profile = s3.carregaImagem("pic", hospital.getId().toString(), file);
		hospital.setImagem("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + profile);
		repository.save(hospital);
	}
	
	public Hospital saveHospital(Hospital hospital) {
		return repository.save(hospital);
	}

}
