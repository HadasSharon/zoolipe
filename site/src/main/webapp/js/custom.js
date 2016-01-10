// MOBILE MENU
$(function() {
	$('nav#mobile-menu').mmenu();
	$("#open-menu").click(function() {
		$(this).attr('id','close-menu');
		$("nav#mobile-menu").trigger("open.mm");
	});
	$("#close-menu").click(function() {
	$("nav#mobile-menu").trigger("close.mm");
	});
});

// CAROUSEL DEAL
$(window).load(function() {
	$("#flexiselDemo1").flexisel();
	$("#flexiselDemo2").flexisel({
		enableResponsiveBreakpoints: true,
		responsiveBreakpoints: { 
			portrait: { 
				changePoint:480,
				visibleItems: 1
			}, 
			landscape: { 
				changePoint:640,
				visibleItems: 2
			},
            tablet: { 
            	changePoint:768,
            	visibleItems: 3
			}
		}
	});
});

$(function () {
	$("#slider1").responsiveSlides({
		maxwidth: 1200,
        speed: 800
	});
});
