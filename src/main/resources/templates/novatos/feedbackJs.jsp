<script>
	$( document ).ready(function() {
		$('#ModalFeedBackNovato').modal('show');
	});

  $(".btn-group input:radio").change(function() {
		
		var pergunta = (''+this.value)[0];
		
		if(this.value == pergunta+1){
		$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-success');
		$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-warning');
		$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-danger');
		
		$('label[for="pergunta'+ pergunta +'"]').addClass('btn-success');
		
		$('#paragrafo'+pergunta).attr("hidden", "true");
		$('#texto'+pergunta).attr("hidden", "true");
		$('#texto'+pergunta).removeAttr("required");
		
		} else {
			if(this.value == pergunta+2){
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-success');
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-warning');
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-danger');
				
				$('label[for="pergunta'+ pergunta +'"]').addClass('btn-warning');
				
				$('#paragrafo'+pergunta).removeAttr("hidden");
				$('#texto'+pergunta).removeAttr("hidden");
				$('#texto'+pergunta).attr("required", "true");
				
			} else {
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-success');
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-warning');
				$('label[for="pergunta'+ pergunta +'"]').removeClass('btn-danger');
				
				$('label[for="pergunta'+ pergunta +'"]').addClass('btn-danger');
				
				$('#paragrafo'+pergunta).removeAttr("hidden");
				$('#texto'+pergunta).removeAttr("hidden");
				$('#texto'+pergunta).attr("required", "true");
			}
		}
	});
  </script>