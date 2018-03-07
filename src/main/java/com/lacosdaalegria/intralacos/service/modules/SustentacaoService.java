package com.lacosdaalegria.intralacos.service.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lacosdaalegria.intralacos.model.sustentacao.Analista;
import com.lacosdaalegria.intralacos.model.sustentacao.Promocao;
import com.lacosdaalegria.intralacos.model.usuario.Voluntario;
import com.lacosdaalegria.intralacos.repository.sustentacao.AnalistaRepository;
import com.lacosdaalegria.intralacos.repository.sustentacao.PromocaoRepository;
import com.lacosdaalegria.intralacos.session.UserInfo;

@Service
public class SustentacaoService {
	
	@Autowired
	private AnalistaRepository repository;
	@Autowired
	private VoluntarioService vService;
	@Autowired
	private PromocaoRepository promocao;
	
	public void addAnalista(String email) {
		Voluntario voluntario = vService.findByEmail(email);
		vService.addRole(voluntario, "ROLE_SUSTENTA");
		repository.save(initAnalista(voluntario));
	}
	
	public void removeAnalista(Voluntario voluntario) {
		Analista analista = repository.findByVoluntario(voluntario);
		repository.delete(analista);
	}
	
	public Promocao registraPromocao(Promocao promocao, UserInfo info) {
		promocao.setAnalista(info.getVoluntario());
		promocao.setCoordenador(promocao.getCoordenador_().getVoluntario());
		promocao.setPrimeiraAtividade(promocao.getCoordenador_().getHospital());
		return this.promocao.save(promocao);
	}
	
	private Analista initAnalista(Voluntario voluntario) {
		Analista analista = new Analista();
		analista.setVoluntario(voluntario);
		return analista;
	}
	
	public Iterable<Analista> allAnalistas(){
		return repository.findAll();
	}

}
