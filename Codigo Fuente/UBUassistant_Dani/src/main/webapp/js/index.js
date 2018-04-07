$(document).ready(function(){
   $("#btn-minimize").click(function(){
	   
    if($("#btn-minimize").hasClass( "btn-minimize" )) {
    	$("#btn-minimize").removeClass('btn-minimize');
    	$("#btn-minimize").addClass('btn-maximize');
    	$("#btn-minimize").attr("src","./img/max.png");
    	
    } 
    else {
    	$("#btn-minimize").attr("src","./img/min.png");
    	$("#btn-minimize").removeClass('btn-maximize');
    	$("#btn-minimize").addClass('btn-minimize');
    }
    
    $(".divchat").slideToggle();
    
  });
});
		
$(document).ready(function(){
	   $(".btn-close").click(function(){
	    $(".divchat-window").slideToggle();
	    $('.pringuinoDiv').slideToggle();
	  });
	});
		
		
$(document).ready(function(){
	   $(".pinguino").click(function(){
		   	var e = $('.iframe');
		   	e.attr("src", e.attr("src"));
			$('.pringuinoDiv').slideToggle();
	    	$(".divchat-window").slideToggle();
	  });
	});

$(document).ready(function(){
	   $(".btn-close-pinguino").click(function(){
	    $('.pringuinoDiv').slideToggle();
	  });
	});