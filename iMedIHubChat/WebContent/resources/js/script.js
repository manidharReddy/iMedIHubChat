$(document).ready(function() {

	$(".myFavBlock").hide();
	$(".showHideFav button").click(function() {
		console.log("sneha");
		$(".myFavBlock").slideToggle();
	});

});
function handleDrop(event, ui) {
	var selectedServices = ui.draggable;
	selectedServices.fadeOut('fast');
}
function scrollToBottom() {
    $('.ui-datatable-scrollable-body').scrollTop(100000)
}
/*Start of jQuery for token management

$(document).on("click",".fa-ticket", function (e) {
	if(!$(this).hasClass('Extend')){
		$(".tokenBlock").show();
		$(this).addClass('Extend');
	}
	else{
		$(this).removeClass('Extend');
		$(".tokenBlock").hide();
	}
});*/

/*End of jQuery for token management*/