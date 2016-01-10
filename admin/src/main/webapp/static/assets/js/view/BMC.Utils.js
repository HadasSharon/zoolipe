BMC.Utils = (function(){
	"use strict";
	return{
		queryStringToJson: function(url){
			var qs = url.split('?');
			if(qs.length < 2){
				return null;
			}
			return $.deparam(qs[1]);
		}
	};
}());