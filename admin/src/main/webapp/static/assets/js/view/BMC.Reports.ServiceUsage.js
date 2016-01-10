BMC.Reports.ServiceUsage = (function(){
	"use strict";
	
	function getSeriesData(result){
		return parseInt(result.numRequests);
	}
	
	return{
		init: function(options){
			options.getSeriesData = getSeriesData;
			options.chartYTitle = '# Requests';
			BMC.Reports.ServiceCommon.init(options);
		}
	};
}());