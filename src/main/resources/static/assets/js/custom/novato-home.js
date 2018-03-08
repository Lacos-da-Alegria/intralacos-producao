$(document).ready(function() {
	$("#hidden_link").fancybox().trigger('click');
});

$( document ).ready(function() {
	$('#ModalCancelar').modal('show');
});

function reativarConta(){
	$('#ModalCancelar').modal('hide');
	$('#reativarConta').modal('show');
}

