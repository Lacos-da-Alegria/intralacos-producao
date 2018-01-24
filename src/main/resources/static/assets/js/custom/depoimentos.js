var pagina = 0;
 var total = 0;
 
 function novoDepoimento(){
	 
	$('#novoDepoimento').modal('hide');
	
	tinymce.triggerSave();
	
	var depoimento = {cor: $('#cor-depoimento').val(), conteudo: $('#conteudo-depoimento').val()};
	
	if(!isEmpty(depoimento.conteudo)){
	
		var request = $.ajax({
			url: '/voluntario/add/depoimento',
			type: 'POST',
			data: depoimento,
			dataType: "json"
		});
		
		request.done(function(depoimento){
			console.log(depoimento);
			prependDepoimento(constroiDepoimento(depoimento));
			$('#cor-depoimento').val('');
			tinymce.activeEditor.setContent('');
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
	
	}
	
 }
 
 var options = {
		  success: function(depoimento) {
			 prependDepoimento(constroiDepoimento(depoimento));
			 $('#cor-depoimento').val('');
			 tinymce.activeEditor.setContent('');
		  },
		  beforeSubmit: function(depoimento) {
			 tinymce.triggerSave();
			 $('#novoDepoimento').modal('hide');
		  }
		};
 
 $(function() {
   $('#form-depoimento').ajaxForm(options);
 });
 
 function replaceNbsps(str) {
	  return str.replace(/&nbsp;/g, "");
	}
 
 function isEmpty(str) {
	    return (!str || 0 === str.length);
	}
 
 function modalDelete(id){
	$('#idDepoimentoDelete').val(id);
	$('#deletarDepoimento').modal('show');
 }
 
 function deleteDepoimento(){
		
	 var id = $('#idDepoimentoDelete').val();
	 
	 $('#deletarDepoimento').modal('hide');
	 
		var request = $.ajax({
			url: '/diretor/delete/depoimento?depoimento='+id,
			type: 'GET',
			dataType: "json"
		});
		
		request.done(function(depoimento){
			console.log("Depoimento deletado com sucesso ! - " + id);
			$('#depoimento'+id).remove()
		});
		
		request.fail(function(jqXHR, textStatus) {
			redirectLogin(jqXHR);
		});
		
 }
 
 function maisDepoimentos(){
 
	carregandoDepoimentos();
	
	var request = $.ajax({
			url: '/info/get/depoimentos?page='+pagina,
			method: "GET",
			dataType: "json"
		});
		
		request.done(function(pageable){
			$.each(pageable.content, function(index){
				appendDepoimento(constroiDepoimento(pageable.content[index]));
				total+=1;
			});
			pagina+=1;
			depoimentosCarregados();
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
 
 function constroiDepoimento(obj){
	
	var depoimento = "";
	
	depoimento += '<div id="depoimento'+obj.id+'"class="item-depoimento well '+ obj.cor +'">'
	depoimento +=	'<div class="item-header">';
	depoimento +=		'<p class="centered"><img src="'+ obj.voluntario.profile +'" class="img-circle" width="60"></p>';
	depoimento +=		'<h5 class="centered">'+ obj.voluntario.nome +'</h5>';
	depoimento +=		'<h6 class="centered">'+ obj.voluntario.nomeDoutor +'</h6>';
	depoimento +=	'</div>';
	depoimento +=	'<div class="item-content">';
	depoimento +=		obj.conteudo; 	
	depoimento +=	'</div>';
	if($('#diretor-validador').val() != null)
		depoimento +=	'<button type="button" onclick="modalDelete('+obj.id+')"class="btn btn-danger btn-sm pull-right"><i class="fa fa-trash-o" aria-hidden="true"></i> <span class="hidden-phone">Deletar Depoimento</span></button>';
	depoimento += '</div>';
	
	return depoimento;
	
 }
 
 function appendDepoimento(html){
	$('#depoimentos').append(html)
 }
 
 function prependDepoimento(html){
	$('#depoimentos').prepend(html)
 }
 
 function carregandoDepoimentos(){
		$('#carrega-mais').html('<i class="fa fa-spinner fa-pulse fa-fw"></i> Carregando');
		$('#carrega-mais').attr("onclick","");
 }
 
 function depoimentosCarregados(){
		$('#carrega-mais').html('Carregar Mais');
		$('#carrega-mais').attr("onclick","maisDepoimentos()");
 }
 
 $.when(maisDepoimentos()).then(function(x) {
	 console.log('Depoimentos carregados!');
 });