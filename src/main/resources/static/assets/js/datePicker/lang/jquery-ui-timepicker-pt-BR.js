/* Brazilian Portuguese translation for the jQuery Timepicker Addon */
/* Written by Diogo Damiani (diogodamiani@gmail.com) */
(function ($) {
	$.timepicker.regional['pt-BR'] = {
		monthNames: ['Janeiro','Fevereiro',"Mar&ccedil;o",'Abril','Maio','Junho',
		'Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
		monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun',
		'Jul','Ago','Set','Out','Nov','Dez'],
		dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
		dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
		dayNamesMin: ['Dom','Seg','Ter','Qua','Qui','Sex','Sab'],
		timeOnlyTitle: 'Escolha o horário',
		timeText: "Hor&aacute;rio",
		hourText: 'Hora',
		minuteText: 'Minutos',
		secondText: 'Segundos',
		millisecText: 'Milissegundos',
		microsecText: 'Microssegundos',
		timezoneText: 'Fuso horario',
		currentText: 'Agora',
		closeText: 'Fechar',
		timeFormat: 'HH:mm',
		dateFormat: 'dd/mm/yy',
		timeSuffix: '',
		amNames: ['a.m.', 'AM', 'A'],
		pmNames: ['p.m.', 'PM', 'P'],
		isRTL: false
	};
	$.timepicker.setDefaults($.timepicker.regional['pt-BR']);
})(jQuery);
