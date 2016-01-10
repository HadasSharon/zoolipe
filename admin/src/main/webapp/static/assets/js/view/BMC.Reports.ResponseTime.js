BMC.Reports.ResponseTime = (function(){
	"use strict";

	function getSeriesData(result){
		return parseInt(result.avgResponseMs);
	}
	
	return{
		init: function(options){
			options.getSeriesData = getSeriesData;
			options.chartYTitle = 'Avg. Response Time';
			BMC.Reports.ServiceCommon.init(options);
		}
	};
}());