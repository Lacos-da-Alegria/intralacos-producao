 var pagina = 0;
 var total = 0;
 var caronas = [];
 
 function novaCarona(){
	 
	$('#novoDepoimento').modal('hide');
	
	tinymce.triggerSave();
	
	var atividade = $("#atividade-select option:selected");
	
	var carona = {vagas: $('#vagas-carona').val(), rota: $('#rota-carona').val()};
	
	if(atividade.data('tipo') == 'acao')
		carona.agenda = parseInt(atividade.val());
	else 
		carona.hospital = parseInt(atividade.val());
		
	console.log(carona);

	if(!isEmpty(carona.rota)){
	
		var request = $.ajax({
			url: '/voluntario/create/carona',
			type: 'POST',
			data: carona,
			dataType: "json"
		});
		
		request.done(function(carona){
			console.log(carona);
			prependCarona(constroiCarona(carona));
			$('#vagas-carona').val('');
			$('#atividade-select').val('');
			tinymce.activeEditor.setContent('');
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
	
	}
	
 }
 
 function isEmpty(str) {
	 return (!str || 0 === str.length);
 }
 
 var idCarona;
 
 function modalDelete(id){
	 idCarona = id;
	$('#deletarDepoimento').modal('show');
 }
 
 function cancelarCarona(){
		
	 $('#deletarDepoimento').modal('hide');
	 
		var request = $.ajax({
			url: '/voluntario/cancelar/carona?carona='+idCarona,
			type: 'GET',
			dataType: "json"
		});
		
		request.done(function(test){
			if(test){
				$('#carona-'+idCarona).remove();
			}
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
 }
 
 function maisCaronas(){
 
	carregandoCaronas();
	
	var request = $.ajax({
			url: '/voluntario/get/caronas?page='+pagina,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(pageable){
			caronas.push(pageable.content);
			$.each(pageable.content, function(index){
				appendCarona(constroiCarona(pageable.content[index]));
				total+=1;
			});
			pagina+=1;
			caronasCarregados();
			if(total < pageable.totalElements){
				$('#carrega-mais').show();
			} else {
				$('#carrega-mais').hide();
			}
			
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
 
 
 }
 
 function constroiCarona(obj){
	
	var carona = "";
	
	carona += '<div id="carona-'+obj.id+'"class="item-depoimento well '+ corRandomica() +'">'
	carona +=	'<div class="item-header centered">';
	carona +=		'<p class="centered"><img src="'+ obj.criador.profile +'" class="img-circle" width="60"></p>';
	carona +=		'<h5 class="centered">'+ obj.criador.nome +'</h5>';
	carona +=		'<h6 class="centered">'+ nomeAtividade(obj) +' - ' + textoVagas(obj.vagas, obj.viajantes) + '</h6>';
	carona +=	'<div id="botao-carona-'+ obj.id +'">';
	if(ehMinhaCarona(obj.criador)){
		carona +=	'<button type="button" onclick="modalDelete('+obj.id+')"class="btn btn-danger btn-sm"><i class="fa fa-trash-o" aria-hidden="true"></i> <span class="hidden-phone">Cancelar Carona</span></button>';
	} else {
		if(estouNaCarona(obj.viajantes)){
			carona +=	'<button type="button" onclick="modalDelete('+obj.id+')"class="btn btn-danger btn-sm"><i class="fa fa-trash-o" aria-hidden="true"></i> <span class="hidden-phone">Não Quero Mais</span></button>';
		} else {
			if(temVagas(obj.vagas, obj.viajantes))
				carona +=	'<button type="button" onclick="queroCarona('+obj.id+')"class="btn btn-success btn-sm"><i class="fa fa-car" aria-hidden="true"></i> <span class="hidden-phone">Quero Carona</span></button>';
		}
	}	
	carona +=	'</div>';
	carona +=	'</div>';
	carona +=	'<div class="item-content">';
	carona +=		obj.rota; 	
	carona +=	'</div>';
	carona += '</div>';
	
	return carona;
	
 }
 
 function ehMinhaCarona(criador){
	 return criador.id == getId();
 }
 
 function estouNaCarona(viajantes){
	 for(i=0;i < viajantes.length; i++){
		 if(viajantes[i].id == getId())
			 return true;
	 }
	 return false;	
 }
 
 function queroCarona(id){
	 
	 var request = $.ajax({
		url: '/voluntario/quero/carona?carona='+id,
		type: 'GET',
		dataType: "json"
	});
	
	request.done(function(carona){
		$('#botao-carona-'+carona.id).html('<button type="button" onclick="modalDelete('+carona.id+')"class="btn btn-danger btn-sm"><i class="fa fa-trash-o" aria-hidden="true"></i> <span class="hidden-phone">Não Quero Mais</span></button>');
		console.log(carona);
	});
	
	request.fail(function(jqXHR, textStatus) {
		redirectLogin(jqXHR);
	});
	 
 }
 
 function textoVagas(vagas, viajantes){
	 var n = vagas - viajantes.length;
	 if(n == 0)
		 return 'Sem vagas';
	 else
		 if(n > 1)
			 return n + ' vagas';
		 else 
			 return '1 vaga'
 }
 
 function temVagas(vagas, viajantes){
	 var n = vagas - viajantes.length;
	 return n > 0;
 }
 
 function getId(){
	 return parseInt($('#id-voluntario').val());
 }
 
 function nomeAtividade(carona){
	 if(carona.hospital == null)
		 return carona.agenda.nome;
	 else
		 return carona.hospital.tag;
 }
 
 function corRandomica(){
	 var cor = Math.floor(Math.random() * 7) + 1;
	 if(cor == 1)
		 return 'item-verde';
	 if(cor == 2)
		 return 'item-vermelho';
	 if(cor == 3)
		 return 'item-roxo';
	 if(cor == 4)
		 return 'item-laranja';
	 if(cor == 5)
		 return 'item-amarelo';
	 if(cor == 6)
		 return 'item-azul';
	 if(cor == 7)
		 return 'item-rosa';
 }
 
 function appendCarona(html){
	$('#caronas').append(html)
 }
 
 function prependCarona(html){
	$('#caronas').prepend(html)
 }
 
 function carregandoCaronas(){
		$('#carrega-mais').html('<i class="fa fa-spinner fa-pulse fa-fw"></i> Carregando');
		$('#carrega-mais').attr("onclick","");
 }
 
 function caronasCarregados(){
		$('#carrega-mais').html('Carregar Mais');
		$('#carrega-mais').attr("onclick","maisDepoimentos()");
 }
 
 $.when(maisCaronas()).then(function(x) {
	 console.log('Caronas carregadas!');
 });
 
 $('.select2').select2();
 
