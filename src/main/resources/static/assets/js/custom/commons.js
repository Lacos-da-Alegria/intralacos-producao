function redirectLogin(jqXHR) {
	
	var responseText = jqXHR.responseText.match(/0c6f5469-e1b5-44af-8973-e0092174af85/);
	
	if(responseText == '0c6f5469-e1b5-44af-8973-e0092174af85')
		window.location.href = '/logout';
		
}