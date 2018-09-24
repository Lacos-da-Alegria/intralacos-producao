$(document).ready(function() {
	$("#hidden_link").fancybox().trigger('click');
});

$( document ).ready(function() {
	$('#MensagemBad').modal('show');
});

function reativarConta(){
	$('#MensagemBad').modal('hide');
	$('#reativarConta').modal('show');
}

function getAgendaInfo(id){
	
	var request = $.ajax({
		url: "/info/agenda?agenda=" + id,
		method: "GET",
		dataType: "json"
	});
	
	request.done(function(agenda){
		console.log(agenda);
		$('#InfoAcao').modal('show');
		$('#aNome').html(agenda.nome);
		$('#aDescricao').html(agenda.instituicao.descricao);
		$('#aEndereco').html(agenda.instituicao.endereco);
		$('#aHorario').html(getDate(new Date(agenda.horario)));
		$('#aDuracao').html(agenda.duracao);
		$('#aVoluntarios').html(agenda.voluntarios);
		
		$('#aRegiao').html(agenda.instituicao.regiao.nome);
		$('#aResponsavel').html(agenda.criador.nome);
		$('#aContatoResp').html('(' + agenda.criador.ddd + ') ' + agenda.criador.whatsapp);
		$('#aInformacoes').html(agenda.informacoes);
		
		$("#grupoWhats").attr("href", agenda.grupoWhats);
	
	});
	
	request.fail(function(jqXHR, textStatus) {
		redirectLogin(jqXHR);
	});
	
}

function getDate(date){
		var data = '';
		data = data + date.getDate()+'/';
		data = data + (parseInt(date.getMonth())+1)+'/';
		data = data + date.getFullYear()+' ';
		data = data + date.getHours()+':';
		data = data + date.getMinutes();
		
		return data;
	}


function clickAndDisable(link) {
    // disable subsequent clicks
    link.onclick = function(event) {
       event.preventDefault();
    }
  };
  
 //Logica para inscrição de atividades em ongs 

	var acoes = [];
	var registros = [];
	
	function registroAcao(id){
		var a = getAgenda(id);
		
		switch(a.situacao) {
	    case 0:
	    	inscreverAcao(a);
	        break;
	    case 1:
	    	cancelarModal(a);
	        break;
	    case 2:
	    	bloqueada(a);
	        break;
	    default:
	    	fechada(a);
			
		}
	}
	
	function inscreverAcao(agenda){
		
		agenda.situacao = 1;
		
		var request = $.ajax({
			url: "/info/inscrever/acao?agenda="+agenda.id,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(response){
			registros.push(response);
			console.log(registros);
			initPage();
			
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
	}
	
	function cancelarModal(hospital){
		$('#ativCancelar').val(hospital.id);
		$("#ModalCancelar").modal('show');
	}
	
	function cancelar(){
		
		var r = getRegistroHospital($('#ativCancelar').val());
		
		var request = $.ajax({
			url: "/info/cancelar?registro="+r.id,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(response){
			if(response.status == 2 ){
				removeRegistro(response);
				atualizaHospitais();
				initPage();
			}
			$("#ModalCancelar").modal('hide');
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
	}
	
	function bloqueada(hospital){
		$('#Modalbloqueada').modal('show');
	}
	
	function fechada(hospital){
		$('#Modalfechada').modal('show');
	}
	
	function getAgenda(id){
		for(i=0;i < acoes.length;i++){
			if(acoes[i].id == id)
				return acoes[i];
		}
	}
	
	function atualizaHospitais(){
		if(registros.length == 0){
			for(i=0;i < acoes.length;i++){
				acoes[i].situacao = 0;
				initCard(acoes[i]);
			}
		}
	}
	
	function getRegistroHospital(id){
		for(i=0;i < registros.length;i++){
			if((registros[i].hospital != null && registros[i].hospital.id == id) ||
					( registros[i].agenda != null && registros[i].agenda.id == id))
				return registros[i];
		}
	}
	
	function removeRegistro(registro){
		for(i=0;i < registros.length;i++){
			if(registros[i].id == registro.id){
				registros.splice(i,1);;
				break;
			}
				
		}
	}
	
	function initPage(){
		console.log('Pagina sendo inicializada!');
		$.each(acoes, function(i, acao){
			initAtividade(acao);
			console.log(acao);
		});
	}
	
	function initAtividade(atividade){
		getSituacao(atividade);
		initCard(atividade);
	}
	
	function getSituacao(atividade){
		if(!atividade.inscricao || atividade.chamada){
			atividade.situacao = 3;
		} else {
			for(i=0;i < registros.length;i++){
				var h = registros[i].hospital;
				var a = registros[i].agenda;
				
				if(h == null)
					h = {};
				
				if(a == null)
					a = {};
				
				if(h.id == atividade.id || a.id == atividade.id){
					atividade.situacao = 1;
					break;
				} else {
					if(h.dia == atividade.dia && h.periodo == atividade.periodo || a.dia == atividade.dia && a.periodo == atividade.periodo){
						atividade.situacao = 2;
						break;
					} else {
						atividade.situacao = 0;
					}
				}
			}
		}
	}
	
	function initCard(atividade){
		var s = atividade.situacao;
		if(s == 0){
			setCardColor(atividade, "profile-01");
			setCardText(atividade, "Participar");
			$("#iconA-"+atividade.id).html('<i class="fa li_paperplane"></i>');
		} else {
			if(s == 1){
				setCardColor(atividade, "profile-01c");
				setCardText(atividade, "Cancelar Participação");
				initializaInfoPosicao(atividade);
			} else {
				if(s == 2){
					setCardColor(atividade, "profile-01b");
					setCardText(atividade, "Atividade Bloqueada");
				} else {
					setCardColor(atividade, "profile-01b");
					setCardText(atividade, "Inscrições Fechadas");
				}
			}
		}
	}
	
	function setCardColor(atividade, cor){
		$('#corH-'+atividade.id).attr('class', 'centered ' + cor);
	}
	
	function setCardText(atividade, text){
		$('#textoH-'+atividade.id).html(text)
	}
	
	function initializaInfoPosicao(atividade){
		colocarAtividadePrimeiro(atividade);
		if(verificaRegistro(atividade)){
			$("#iconA-"+atividade.id).html('<i class="fa fa-thumbs-o-up"  style="color: #14c10e; font-size: 1.2em;">&nbsp&nbspEsperamos você lá</i>');
		} else {
			if($('#novato_ongs').val())
				$("#iconA-"+atividade.id).html('<a onclick="descobrirPosicaoAcao('+atividade.id+')"style="color: #5373e7;font-size: 1.2em; cursor:pointer;"><i class="fa fa-hand-o-right">&nbsp&nbspClique para ver sua posição</i></a>');
			else
				$("#iconA-"+atividade.id).html('<a onclick="descobrirPosicaoAcaoNovato('+atividade.id+')"style="color: #5373e7;font-size: 1.2em; cursor:pointer;"><i class="fa fa-hand-o-right">&nbsp&nbspClique para ver sua posição</i></a>');
		}
	}
	
	function colocarAtividadePrimeiro(atividade){
		var card;
		if(atividade.hasOwnProperty('tag')){
			card = $('#div-hospital-'+atividade.id);
			$('#div-hospital-'+atividade.id).remove();
			$('#hospitais').prepend(card);
		} else {
			card = $('#div-acao-'+atividade.id);
			$('#div-acao-'+atividade.id).remove();
			$('#acoes').prepend(card);
		}
	}
	
	function verificaRegistro(atividade){
		var registro = getRegistroHospital(atividade.id);
		if(registro.tipo == 2 || registro.tipo == 3){
			return true;
		} else {
			return false;
		}
	}
	
	function descobrirPosicaoAcao(id){
		doPosicaoAjax("/info/posicao/acao?agenda=", id);
	}
	
	function doPosicaoAjax(url, id){
		$("#iconA-"+id).html('<span style="color: #5373e7;font-size: 1.2em;"><i class="fa fa-spinner fa-pulse fa-fw"></i> Carregando<span>');			
		var request = $.ajax({
			url: url+id,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(posicao){
			if(posicao !=  null){
				if(posicao < 0){
					$("#iconA-"+id).html('<i class="fa fa-thumbs-o-up"  style="color: #14c10e; font-size: 1.2em;">&nbsp&nbspEsperamos você lá</i>');
				} else {
					$("#iconA-"+id).html('<i class="fa fa-hand-o-right"  style="color: #5373e7;font-size: 1.2em;">&nbsp&nbsp'+posicao+'º na Fila de Espera</i>');
				}
			}
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
	}	
	
	function descobrirPosicaoAcaoNovato(id){
		doPosicaoNovatoAjax("/novato/posicao/acao?agenda=", id);
	}
	
	function doPosicaoNovatoAjax(url, id){
		$("#iconA-"+id).html('<span style="color: #5373e7;font-size: 1.2em;"><i class="fa fa-spinner fa-pulse fa-fw"></i> Carregando<span>');			
		var request = $.ajax({
			url: url+id,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(posicao){
			if(posicao !=  null){
				if(posicao < 0){
					$("#iconA-"+id).html('<i class="fa fa-thumbs-o-down"  style="color: #5373e7; font-size: 1.2em;">&nbsp&nbspNão foi dessa vez</i>');
				} else {
					$("#iconA-"+id).html('<i class="fa fa-thumbs-o-up"  style="color: #14c10e; font-size: 1.2em;">&nbsp&nbspEsperamos você lá</i>');
				}
			}
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
	}	
	
	$.when(initAcoes()).then(function(x) {
		$.when(initRegistros()).then(function(x) {
			initPage();
		});
	});
	
	function initAcoes(){
		
		var request = $.ajax({
			url: "/info/acoes",
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(response){
			console.log(response);
			acoes = response;
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
		return request;
	
	}
	
	function initRegistros(){
		
		var request = $.ajax({
			url: "/info/registros",
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(response){
			registros = response;
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
		return request;
	}