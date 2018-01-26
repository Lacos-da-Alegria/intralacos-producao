 var pagina = 0;
 var total = 0;
 
 function maisDepoimentos(){
 
	carregandoDepoimentos();
	
	var request = $.ajax({
			url: '/diretor/get/feedbacks?page='+pagina,
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
	
	var data = new Date(obj.criacao);
	
	depoimento += '<div class="item-depoimento well item-azul">'
	depoimento +=	'<small class="pull-right" style="color: orange; margin-top: -16px; margin-right: -10px ">'+ $.datepicker.formatDate('dd/mm/yy', data) +'</small>';
	depoimento +=	'<div>';
	depoimento +=		obj.texto; 	
	depoimento +=	'</div>';
	depoimento += '</div>';
	
	return depoimento;
	
 }
 
 function appendDepoimento(html){
	$('#depoimentos').append(html)
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
	 console.log('Feedbakcs carregados!');
 });