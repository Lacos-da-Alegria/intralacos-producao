package com.lacosdaalegria.intralacos.repository.sustentacao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacosdaalegria.intralacos.model.sustentacao.Promocao;

@Repository
public interface PromocaoRepository extends CrudRepository<Promocao, Long>{

}
