<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
			<!-- Modal -->
					<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="ModalFeedBackNovato" class="modal fade">
		              <div class="modal-dialog">
		                  <div class="modal-content">
		                      <div class="modal-header-x">
		                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                          <h4 class="modal-title centered"><i class="fa fa-comments-o" aria-hidden="true"></i> Feedback Novatos</h4>
		                      </div>
							  <form class="form-horizontal style-form" action="feedbackNovato" method="get">
		                      <div class="modal-body visiblemobile">	
				<small style="color:red; margin-left:5px;">*Esse feedback é anônimo</small><br>							  
					<div class="form-group">	  
                    <label class="col-sm-5 col-sm-5 control-label" >Você julga o grupo de whatsapp como útil na transmissão de informações e receptividade?</label>
                      <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons">
					  <label for="pergunta1" class="btn btn-primary">
						<input type="radio" name="nota_1" id="option1_1" value="11"autocomplete="off" required> Sim
					  </label>
					  <label for="pergunta1" class="btn btn-primary">
						<input type="radio" name="nota_1" id="option1_2" value="12" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta1" class="btn btn-primary">
						<input type="radio" name="nota_1" id="option1_3" value="13" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo1" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto1" style="resize:none" name="comentario_1" cols="35" rows="3" hidden></textarea><br><br>
					
					<div class="form-group">
                    <label class="col-sm-5 col-sm-5 control-label" >Você recebeu uma explicação clara sobre todos os objetivos e as diretrizes do nosso projeto antes da visita?</label>
                       <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons" >
					  <label for="pergunta2" class="btn btn-primary">
						<input type="radio" name="nota_2" id="option2_1" value="21"autocomplete="off" required> Sim
					  </label>
					  <label for="pergunta2" class="btn btn-primary">
						<input type="radio" name="nota_2" id="option2_2" value="22" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta2" class="btn btn-primary">
						<input type="radio" name="nota_2" id="option2_3" value="23" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo2" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto2" style="resize:none" name="comentario_2" cols="35" rows="3" hidden></textarea><br><br>					
				
					<div class="form-group">
                    <label class="col-sm-5 col-sm-5 control-label" >Os voluntários no dia da visita foram receptivos?</label>
                       <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons" >
					  <label for="pergunta3" class="btn btn-primary">
						<input type="radio" name="nota_3" id="option3_1" value="31"autocomplete="off" > Sim
					  </label>
					  <label for="pergunta3" class="btn btn-primary">
						<input type="radio" name="nota_3" id="option3_2" value="32" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta3" class="btn btn-primary">
						<input type="radio" name="nota_3" id="option3_3" value="33" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo3" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto3" style="resize:none" name="comentario_3" cols="35" rows="3" hidden></textarea>
                       
					<br><br>
					
					<div class="form-group">
                    <label class="col-sm-5 col-sm-5 control-label" >Você se sentiu motivado a voltar e conhecer outras atividades do Laços da Alegria?</label>
                       <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons" >
					  <label for="pergunta4" class="btn btn-primary">
						<input type="radio" name="nota_4" id="option4_1" value="41"autocomplete="off" required> Sim
					  </label>
					  <label for="pergunta4" class="btn btn-primary">
						<input type="radio" name="nota_4" id="option4_2" value="42" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta4" class="btn btn-primary">
						<input type="radio" name="nota_4" id="option4_3" value="43" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo4" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto4" style="resize:none" name="comentario_4" cols="35" rows="3" hidden></textarea>
                       
					<br><br>
					
					<div class="form-group">
                    <label class="col-sm-5 col-sm-5 control-label" >Você sentiu um bom apoio ao longo da visita?</label>
                       <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons" >
					  <label for="pergunta5" class="btn btn-primary">
						<input type="radio" name="nota_5" id="option5_1" value="51"autocomplete="off" required> Sim
					  </label>
					  <label for="pergunta5" class="btn btn-primary">
						<input type="radio" name="nota_5" id="option5_2" value="52" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta5" class="btn btn-primary">
						<input type="radio" name="nota_5" id="option5_3" value="53" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo5" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto5" style="resize:none" name="comentario_5" cols="35" rows="3" hidden></textarea>
                       
					<br><br>
					
					<div class="form-group">
                    <label class="col-sm-5 col-sm-5 control-label" >Em geral, ficou satisfeito com o nosso projeto?</label>
                       <div class="col-sm-6 centered">
                          <div class="btn-group" data-toggle="buttons" >
					  <label for="pergunta6" class="btn btn-primary">
						<input type="radio" name="nota_6" id="option6_1" value="61"autocomplete="off" required> Sim
					  </label>
					  <label for="pergunta6" class="btn btn-primary">
						<input type="radio" name="nota_6" id="option6_2" value="62" autocomplete="off"> Parcialmente
					  </label>
					  <label for="pergunta6" class="btn btn-primary">
						<input type="radio" name="nota_6" id="option6_3" value="63" autocomplete="off"> Não
					  </label>
					</div>
                    </div>
					</div>
					
					<p id="paragrafo6" hidden style="margin-top:15px">Se <b>não</b> ou <b>parcialmente</b>, por favor nos explique o porque:</p>
					<textarea id="texto6" style="resize:none" name="comentario_6" cols="35" rows="3" hidden></textarea>
                       					
					<hr style="border-color:#c9c9c9">
					<input name="responsavelNovato" type="number" value="${id_responsavel}" hidden>
					<input name="primeiraAtividade" type="number" value="${primeira_ativ}" hidden>
					
					<div class="form-group">
                    <label style="margin-left:15px;">Deixe mais sugestões e sua opinião a respeito de algum outro ponto:</label>
                       <div class="col-sm-10">
                          <textarea style="resize:none" name="sugestao" cols="35" rows="3"></textarea>
                       </div>
                    </div>
					                                                       
		                      </div>
		                      <div class="modal-footer">
							  <input type="submit" value="Enviar" class="btn btn-primary"> 
		                         <button data-dismiss="modal" class="btn btn-default" type="button">Fechar</button>
		                      </div>
							  
					</form>
		                  </div>
		              </div>
		          </div>
		          <!-- modal -->