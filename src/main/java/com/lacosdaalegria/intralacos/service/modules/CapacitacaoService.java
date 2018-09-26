package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lacosdaalegria.intralacos.model.capacitacao.Aula;
import com.lacosdaalegria.intralacos.model.capacitacao.Educador;
import com.lacosdaalegria.intralacos.model.capacitacao.Materia;
import com.lacosdaalegria.intralacos.model.usuario.RoleEnum;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.capacitacao.AulaRepository;
import com.lacosdaalegria.intralacos.repository.capacitacao.EducadorRepository;
import com.lacosdaalegria.intralacos.repository.capacitacao.MateriaRepository;
import com.lacosdaalegria.intralacos.repository.s3.S3;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CapacitacaoService {

	private @NonNull AulaRepository aula;
	private @NonNull MateriaRepository materia;
	private @NonNull EducadorRepository educador;
	private @NonNull VoluntarioService vService;
	private @NonNull S3 s3;
	
	public void addEducador(String email) {
		Voluntario voluntario = vService.findByEmail(email);
		if(voluntario != null) {
			if(educador.findByVoluntario(voluntario) == null) {
				vService.addRole(voluntario, RoleEnum.CAPACITACAO);
				this.educador.save(initEducador(voluntario));
			}
		}
	}
	
	public Iterable<Educador> allEducadores(){
		return this.educador.findAll();
	}
	
	public void removaEducador(Educador educador) {
		vService.removeRole(educador.getVoluntario(), RoleEnum.CAPACITACAO);
		this.educador.delete(educador);
	}
	
	private Educador initEducador(Voluntario voluntario) {
		Educador educador = new Educador();
		educador.setVoluntario(voluntario);
		return educador;
	}
	
	public Iterable<Materia> allMaterias(){
		return this.materia.findAll();
	}
	
	public Iterable<Materia> materiasAtivas(){
		return this.materia.findByStatus(1);
	}
	
	public void createMateria(Materia materia) {
		this.materia.save(materia);
	}
	
	public void atualizaStatus(Materia materia) {
		if(materia.getStatus().equals(1))
			materia.setStatus(2);
		else 
			materia.setStatus(1);
		this.materia.save(materia);
	}
	
	public void atualizaStatus(Aula aula) {
		if(aula.getStatus().equals(1))
			aula.setStatus(2);
		else 
			aula.setStatus(1);
		this.aula.save(aula);
	}
	
	public void updateImage(MultipartFile file, Materia materia) {
		String imagem = s3.carregaImagem("mat", materia.getId().toString(), file);
		materia.setImagem("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + imagem);
		this.materia.save(materia);
	}
	
	public void updateImage(MultipartFile file, Aula aula) {
		String imagem = s3.carregaImagem("aula", aula.getId().toString(), file);
		aula.setImagem("https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-318693850464/" + imagem);
		this.aula.save(aula);
	}
	
	public Iterable<Aula> aulasMateria(Materia materia){
		return this.aula.findByMateria(materia);
	}
	
	public Iterable<Aula> aulasAtivas(Materia materia){
		return this.aula.findByMateriaAndStatus(materia, 1);
	}
	
	public void createAula(Aula aula) {
		this.aula.save(aula);
	}
	
	
}
