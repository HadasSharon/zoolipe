var BMC = (function(){
	"use strict";
	
	var _overlayHandle = null;
	var _onStateChangeHandler = null;
	
	function showOverlay() {
        if (_overlayHandle == null) {
            _overlayHandle = window.setTimeout(function() {
                $('#overlay').css('opacity', '.3').show();
            }, 300);
        }
    };
	    
    function hideOverlay() {
        if (_overlayHandle != null) {
            window.clearTimeout(_overlayHandle);
            _overlayHandle = null;
            if ($('#overlay').is(':visible')) {
                $('#overlay').fadeOut(300);
            }
        }
     };
     
     function showMessage(message,type){
    	 var className = 'info'; 
			if(type=='error'){
				className='danger';
			}
			else if(type=='success'){
				className='success';
			}
			else if(type=='warn'){
				className='warning';
			}
			
			$('#div-global-message-container').empty().append('<div class="alert alert-'+className+'"><button type="button" class="close" data-dismiss="alert"><i class="ace-icon fa fa-times"></i></button>'+message+'<br /></div>');
     }
	
	return{
		init: function(){
			var url = window.location.href;

			// passes on every "a" tag
			$('.sidebar a').each(function() {
				// checks if its the same on the address bar
				if (url == (this.href)) {
					$(this).closest('li').addClass('active');
					$(this).parent().parent().parent().addClass('open');
					$(this).parent().parent().css('display','block');
					$(this).parent().parent().addClass('nav-show');
				}
			});

			$('#confirm-logout').on('show.bs.modal',function(e) {
				var that = this;
				$(that).find('.danger').attr('href',$(e.relatedTarget).data('href'));
			});

            $(document).bind('ajaxSend', function () {
            	BMC.hideMessage();
                showOverlay();
            }).bind('ajaxComplete', function () {
                hideOverlay();
            });
            
            //EDG: Create trim() function for IE:
	        if(typeof String.prototype.trim !== 'function') {
	        	//alert("Hello inside trim()");
	        	String.prototype.trim = function() {
	        		
	    	    return this.replace(/^\s+|\s+$/g, ''); 
	    	  };
	    	}
		},
		showMessage: function(message,type){
			showMessage(message,type);
		},
		showErrorMessage: function(message){
			showMessage(message,'error');
		},
		hideMessage: function(){
			$('#div-global-message-container').empty();
		},
		resizeMain: function(){
			var size=$('.main-content').css('margin-left');
			if(size=='50px'){
				$('.main-content').css('margin-left',190);
			}
			else{
				$('.main-content').css('margin-left',50);
			}
		},
		noBack: function(){
			window.history.forward();
		},
		registerStateChangeHandler: function(handler){
			_onStateChangeHandler = handler;
		},
		onStateChange: function(newState){
			if(_onStateChangeHandler != null){
				_onStateChangeHandler(newState);
			}
		},
		pushState: function(data,title,url){
			History.pushState(data,title,url);
		},
		replaceState: function(data,title,url){
			History.replaceState(data,title,url);
		},
		getState: function(){
			return History.getState();
		},
		initState: function(){
			BMC.onStateChange(History.getState());
		}
	};
}());

(function(window,undefined){
	History.Adapter.bind(window,'statechange',function(){ 
		var newState = History.getState();
		//History.log('statechange:', newState.data, newState.title, newState.url);
		BMC.onStateChange(newState);
	});	
})(window);

BMC.Reports = {};