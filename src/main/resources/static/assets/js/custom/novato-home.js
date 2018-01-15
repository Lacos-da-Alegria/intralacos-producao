$(document).ready(function() {
	$("#hidden_link").fancybox().trigger('click');
});

$(document).ready(function () {
    $("#date-popover").popover({html: true, trigger: "manual"});
    $("#date-popover").hide();
    $("#date-popover").click(function (e) {
        $(this).hide();
    });

    $("#my-calendar").zabuto_calendar({
        action: function () {
            return myDateFunction(this.id, false);
        },
        action_nav: function () {
            return myNavFunction(this.id);
        },
        legend: [
            {type: "text", label: "Special event", badge: "00"},
            {type: "block", label: "Regular event", }
        ]
    });
});

$("a.more").click(function() {
	$.fancybox({
			'padding'		: 0,
			'autoScale'		: false,
			'transitionIn'	: 'none',
			'transitionOut'	: 'none',
			'title'			: this.title,
			'width'			: 680,
			'height'		: 495,
			'href'			: this.href.replace(new RegExp("watch\\?v=", "i"), 'v/'),
			'type'			: 'swf',
			'swf'			: {
			'wmode'		: 'transparent',
			'allowfullscreen'	: true
			}
		});

	return false;
});

 $(function() {
  jQuery(".fancybox").fancybox();
 });

 $( document ).ready(function() {
		$('#ModalCancelar').modal('show');
	});


function myNavFunction(id) {
    $("#date-popover").hide();
    var nav = $("#" + id).data("navigation");
    var to = $("#" + id).data("to");
    console.log('nav ' + nav + ' to: ' + to.month + '/' + to.year);
};


$('select[name^="preferencia"] option[value="${voluntario.preferencia.id}"]').attr("selected","selected");