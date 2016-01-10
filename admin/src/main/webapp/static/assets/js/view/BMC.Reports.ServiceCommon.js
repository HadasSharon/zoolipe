BMC.Reports.ServiceCommon = (function(){
	"use strict";
	
	var _options = null;
	
	function renderChart(request,response,target){
		var chartCategories = [];
		var chartSeries = [];
		var plotOptions = {};
		var resultCt = response.results.length;
		
		var startDate = moment(request.startDate);
		var endDate = moment(request.endDate);
		
		if(response.resultRange == BMC.Reports.ServiceCommon.reportResultRanges.DAILY){
			var dayDifference = endDate.diff(startDate,'days');
			chartCategories.push(startDate.format('MM/DD'));
			for(var i = 0; i < dayDifference; i++){
				chartCategories.push(startDate.add(1,'days').format('MM/DD'));
			}
			
			plotOptions.series = {
				cursor: 'pointer',
				point:{
					events:{
						click: function(e){
							var submitJson = {
								timePeriod: request.timePeriod,
								serviceIds: request.serviceIds,
								channelIds: request.channelIds
							};
							
							var startDateYear = startDate.year();
							if(startDateYear == endDate.year())
							{
								submitJson.startDate = moment(this.category+'/'+startDateYear).startOf('day').format();
								submitJson.endDate = moment(this.category+'/'+startDateYear).endOf('day').format();
							}
							
							console.log(submitJson);
							
							BMC.pushState(null,null,'?'+$.param(submitJson));
						}
					}
				}
			};
		}
		else if(response.resultRange == BMC.Reports.ServiceCommon.reportResultRanges.MONTHLY){
			var monthDifference = endDate.diff(startDate,'months');
			chartCategories.push(startDate.format('MM/YYYY'));
			for(var i = 0; i < monthDifference; i++){
				chartCategories.push(startDate.add(1,'months').format('MM/YYYY'));
			}
			
			plotOptions.series = {
					cursor: 'pointer',
					point:{
						events:{
							click: function(e){
								var dates = this.category.split('/');
								var monthStartDate = moment(dates[0]+'/1/'+dates[1]);
								
								var submitJson = {
									timePeriod: request.timePeriod,
									serviceIds: request.serviceIds,
									channelIds: request.channelIds,
									startDate: monthStartDate.startOf('month').format(),
									endDate: monthStartDate.endOf('month').format()
								};

								console.log(submitJson);
								
								BMC.pushState(null,null,'?'+$.param(submitJson));
							}
						}
					}
				};
		}
		else{
			for(var i=0; i<24; i++){
				var text = null;
				if(i > 12 ){
					text = (i-12)+'PM';
				}
				else if (i > 0 && i < 12){
					text = i+'AM';
				}
				else if(i == 12){
					text = '12PM';
				}
				else{
					text = '12AM';
				}
				
				chartCategories.push(text);
			}
		}
		
		var lastServiceResult = null;
		var chartCategoriesCt = chartCategories.length;
		
		for(var i=0; i<resultCt; i++){
			var result = response.results[i];

			if(lastServiceResult == null || result.serviceId != lastServiceResult.serviceId){
				lastServiceResult = {
					name: $('#ddl-services option[value="'+result.serviceId+'"]').text(),
					data: [],
					serviceId: result.serviceId
				};

				
				for(var j=0; j<chartCategoriesCt; j++){
					lastServiceResult.data.push(0);
				}
				
				chartSeries.push(lastServiceResult);
			}
			
			if(response.resultRange == BMC.Reports.ServiceCommon.reportResultRanges.DAILY){
				for(var j=0;j<chartCategoriesCt;j++){
					if(chartCategories[j] == moment(result.date).format('MM/DD'))
					{
						lastServiceResult.data[j]+=_options.getSeriesData(result);
					}
				}
			}
			else if(response.resultRange == BMC.Reports.ServiceCommon.reportResultRanges.MONTHLY){
				for(var j=0;j<chartCategoriesCt;j++){
					if(chartCategories[j] == moment(result.date).format('MM/YYYY'))
					{
						lastServiceResult.data[j]+=_options.getSeriesData(result);
					}
				}
			}
			else{
				lastServiceResult.data[result.hour] += _options.getSeriesData(result);
			}
		}
	
		$('#'+target).highcharts({
	        title: {
	            text: '',
	        },
	        credits:{
	        	enabled: false
	        },
	        xAxis: {
	            categories: chartCategories
	        },
	        yAxis: {
	            title: {
	                text: _options.chartYTitle
	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        tooltip:{
	        	valueSuffix: _options.valueSuffix,
	        	pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: <b>{point.y}</b><br/>'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        plotOptions: plotOptions,
	        series: chartSeries
	    });
	}
	
	function resetFilter(){
		$('#ddl-services').select2('data',null);
		$('#ddl-channels').select2('val','');
		$('#ddl-time-period').val(0);
		$('#div-time-period').hide();
	}
	
	function getReportData(){
		//EDG: Get date range based on option selected
		var startDate = null;
		var endDate = null;
		var timePeriod = parseInt($('#ddl-time-period').val());
		
		switch(timePeriod)
		{
			//Yesterday
			case 1:
				startDate = moment().subtract(1,'days').startOf('day');
				endDate = moment().subtract(1,'days').endOf('day');
				break;
			//This Week
			case 2:
				startDate = moment().startOf('week');
				endDate = moment().endOf('week');
				break;
			//Last Week
			case 3:
				startDate = moment().subtract(1,'weeks').startOf('week');
				endDate = moment().subtract(1,'weeks').endOf('week');
				break;
			//This Month
			case 4:
				startDate = moment().startOf('month');
				endDate = moment().endOf('month');
				break;
			//Last Month
			case 5:
				startDate = moment().subtract(1,'months').startOf('month');
				endDate = moment().subtract(1,'months').endOf('month');
				break;
			//Custom
			case 6:
				startDate = moment($('#txt-start-date').val(),'MM/DD/YYYY').startOf('day');
				endDate = moment($('#txt-end-date').val(),'MM/DD/YYYY').endOf('day');
				break;
			//Today
			default:
				startDate = moment().startOf('day');
				endDate = moment().endOf('day');
				break;
		}
		
		var submitJson = {
			timePeriod: timePeriod,
			startDate: startDate.format(),
			endDate: endDate.format(),
			serviceIds: $('#ddl-services').val(),
			channelIds: $('#ddl-channels').val()
		};

		BMC.pushState(null,null,'?'+$.param(submitJson));
	}
	
	function onStateChange(state){
		var urlParams = BMC.Utils.queryStringToJson(state.hash);
		if(urlParams!=null){
			
			var submitJson ={
				startDate: urlParams.startDate,
				endDate: urlParams.endDate	
			};
			
			$('#ddl-time-period').val(urlParams.timePeriod);
			$('#txt-start-date').datepicker('setDate',new Date(urlParams.startDate));
			$('#txt-end-date').datepicker('setDate',new Date(urlParams.endDate));
			
			if(urlParams.serviceIds != ""){
				submitJson.serviceIds = urlParams.serviceIds;
				$('#ddl-services').select2('val',urlParams.serviceIds);
			}
			
			if(urlParams.channelIds!=null && urlParams.channelIds!=""){
				submitJson.channelIds = [urlParams.channelIds];
				$('#ddl-channels').select2('val',urlParams.channelIds);
			}
			
			if(urlParams.timePeriod == 6){
				$('#div-time-period').show();
			}
			
			$.ajax({
				url: _options.getReportDataUrl,
				type: 'POST',
				data: JSON.stringify(submitJson),
				success: function(response){
					if(response.results !=null && response.results.length>0){
						
						renderChart(urlParams,response,'div-report-container');

						$('#div-report-container').show();
					}
					else{
						BMC.showMessage('There was no data available for the report criteria you provided','info');
						$('#div-report-container').hide();
					}
				}
			});
		}
	}
	
	return{
		init: function(options){
			_options = options;
			
			$.ajaxSetup({
                cache: false,
                type: 'GET',
                dataType: 'json',
                disableOverlay: false,
                contentType: 'application/json',
                error: function (xhr, ajaxOptions, thrownError) {
            		var that = this;
            		
            		if (xhr.status == 401){
            			window.location = 'login.jsp';
            		}
            		else{
            			BMC.showMessage('There was an error processing your request, please try again','error');
            			
            			if(that.errorCallback)
	            		{
	            			that.errorCallback();
	            		}
            		}
            	}
            });
			
			
			$('#ddl-time-period').change(function(){
				var val = parseInt($(this).val());
				
				if(val == 6)
				{
					$('#div-time-period').show();
					$('#txt-start-date,#txt-end-date').val(moment().format('MM/DD/YYYY'));
				}
				else
				{
					$('#div-time-period').hide();
				}
			});

			$('#btn-generate-report').click(getReportData);
			
			$('#btn-reset-filter').click(resetFilter);
			
			$('#txt-start-date,#txt-end-date').datepicker({
				autoclose: true,
				todayHighlight: true,
				dateFormat: 'mm/dd/yyyy'
			});
			
			$('#ddl-services').select2({ 
				allowClear: true, 
				placeholder: 'All Services'
			});
			
			$('#ddl-channels').select2({ 
				allowClear: true
			});
			
			$('#div-first-panel').collapse('toggle');
			
			BMC.registerStateChangeHandler(onStateChange);
		    BMC.initState();
			
		},
		reportResultRanges:{
			HOURLY : 'HOURLY',
			DAILY : 'DAILY',
			MONTHLY : 'MONTHLY'
		}
			
	};
}());