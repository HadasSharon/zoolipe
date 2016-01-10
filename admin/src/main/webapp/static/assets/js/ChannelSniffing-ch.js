
$(document).ready(function(event) {
	
	//$(".deleteChannelLink").click(function(event) {
	$("#channelTable").on('click', '.deleteChannelLink',	function() {
		$("#deleteError").hide('slow');
		document.getElementById("deleteChannelIdButton").disabled=false;
 		var channelId = $(this).data("channel-id");
		$("#deleteChannelId").val(channelId);
		//alert("To be deleted id"+channelId);
		
 		var channelDetailTr = $("#channelDetail_"+channelId);
		
		var channel = channelDetailTr.find(".channelName").html();
		
		$("#deleteConf").html("You are about to delete Channel <B>"+channel +"</B> & any associated <B>Throttles</B>, this procedure is irreversible.<br/> Do you want to proceed?");
		$("#deleteConf").show('slow');
		
		//alert("InExternalJS"); 
	}); 

	$("#deleteChannelIdButton").click(function(event) {
		var channelId = $("#deleteChannelId").val();
		document.getElementById("deleteChannelIdButton").disabled=true;
		//alert("deleting channel with id "+channelId);
	
		$.ajax({
			type: "post",
			cache: false,
			url: "/alerts/cadmin/deleteChannel.htm",
 			data: "id=" + channelId,
			success: function(response) {
				if (response.status == "SUCCESS") {
				    $('#addSuccess').hide();
				    $('#updateSuccess').hide();
				    $('#deleteSuccess').show();
					$("#cancel-delete").click();	
					
					$("#channelDetail_"+channelId).closest('tr').remove();
				    $("#channelTable").trigger('update');
				    document.getElementById("deleteChannelIdButton").disabled=false;
				}
				else{
					var errorInfo = response.status;
					document.getElementById("deleteChannelIdButton").disabled=false;
					//errorInfo += "</ul>";
					$("#deleteError").html(errorInfo +" <br/> Error updating gateway ");
					$("#deleteError").show('slow');
				}

			},
			error: function(err) {
				alert("Alert from Error of Delete Ajax Call"+ err.responseText);
				document.getElementById("deleteChannelIdButton").disabled=false;
				return false;
			}
		}); 		
		event.preventDefault();
        event.stopPropagation();
        
	});
	
	//$(".editChannelLink").click(function(event) {
	$("#channelTable").on('click','.editChannelLink',function() {
		$("#editError").hide('slow');
		document.getElementById("editSubmit").disabled=false;
		var chnId = $(this).data("channel-id");
		
		var channelDetailTr = $("#channelDetail_"+chnId);
		var editForm = $("#editForm");
		//alert("edit id:"+userId);
		
		//alert("Name:"+channelDetailTr.find(".channelName").html()+":apikey:"+channelDetailTr.find(".apikey").html()+":SourceIP:"+channelDetailTr.find(".sourceip").html());
		editForm.find("#id").val(chnId);
		editForm.find("#name").val(channelDetailTr.find(".channelName").html());
		editForm.find("#apikey").val(channelDetailTr.find(".apikey").html());
		editForm.find("#sourceIP").val(channelDetailTr.find(".sourceip").html());
		
		var activeStr = channelDetailTr.find(".activeChannel").html().trim();
		if("Active" === activeStr) {
			editForm.find(".editChannelActive").prop('checked', true);
		} else {
			editForm.find(".editChannelNotActive").prop('checked', true);
		}
		
		editForm.trigger('refresh');
	});
	
	$("#editFormReset").click(function(event) {
		$("#editForm").trigger("reset");
		$("#editForm").find("input:radio, input:checkbox").removeAttr('checked').removeAttr('selected');
		$("#editSuccess").hide('slow');
		$("#editError").hide('slow');
		$(".nameEdit").removeClass('errorClass');
		$("#nameEdit-exist").hide('slow');
		$("#nameEdit-error").hide('slow');
		$("#apikeyEdit-exist").hide('slow');
		document.getElementById("editSubmit").disabled=false;
	});
	
	$("#editForm").submit(function(event, ui) {
		
		var validate=validateEdit();
		
		//alert("Value from validate edit in form submit before ajax call:"+validate);
		if(validate){
			document.getElementById("editSubmit").disabled=true;
		$.ajax({
			type: "post",
			cache: false,
			url: "/alerts/cadmin/updateChannel.htm",
			data: $('#editForm').serialize(),
			beforeSend: function(xhr) {
	            xhr.setRequestHeader("Accepts", "application/json");
	        },
			success: function(response) {
				if(response.status == "SUCCESS") {
					//alert("inside successful update");
					
				    $("#addSuccess").hide();
				    $("#updateSuccess").show();
				    $("#deleteSuccess").hide();				
					$("#editError").hide('slow');
					$("#editFormReset").click();		
					document.getElementById("editSubmit").disabled=false;
					//window.location.href = "./channel.htm";
					
					var IDValue = response.result.id;
					var name = response.result.name;
					var apikey = response.result.apikey; 
					var sourceip =  response.result.sourceIP;  
					var active = response.result.active;

					//alert("values to be updated result: "+IDValue+":"+name+":"+apikey+":"+sourceip+":"+active);
					
					var resort = "";
					var resortname=true;
					callback = function(table){ /* do something */ };
				    $("#channelDetail_"+IDValue+" .channelName").text(name);
				    $("#channelTable").trigger("updateCell",[this, resortname, callback]);
	
					callback = function(table){ /* do something */ };
				    $("#channelDetail_"+IDValue+" .apikey").text(apikey);
				    $("#channelTable").trigger("updateCell",[this, resort, callback]);
				    
					callback = function(table){ /* do something */ };
				    $("#channelDetail_"+IDValue+" .sourceip").text(sourceip);
				    $("#channelTable").trigger("updateCell",[this, resort, callback]); 
				    
					if(1 == active) {
						resort = true;
						callback = function(table){ /* do something */ };
					    $("#channelDetail_"+IDValue+" .activeChannel").text("Active");
					    $("#channelTable").trigger("updateCell",[this, resort, callback]);
					} else {
						resort = true;
						callback = function(table){ /* do something */ };
					    $("#channelDetail_"+IDValue+" .activeChannel").text("Not active");
					    $("#channelTable").trigger("updateCell",[this, resort, callback]);
					}
				    
					
				} else {
					var errorInfo = response.status;
					document.getElementById("editSubmit").disabled=false;
					//errorInfo += "</ul>";
					$("#editError").html(errorInfo +" <br/> Error updating gateway ");
					$("#editSuccess").hide('slow');
					$("#editError").show('slow');

				}
			},
			error: function(err) {
				alert(err.responseText);
				document.getElementById("editSubmit").disabled=false;
				return false;
			}
		});
		}
		
		event.preventDefault();
		event.stopPropagation();
		});
	});


	validateChannelName = function() {
		var ajax_params;
		//alert("inside validate chanenl name of Add with name :"+$("#name").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateChannelName.htm",
			type : "GET",
			data : {
				"channelname" : $("#name").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#cname-exist").hide();
					$("#cname-error").show();
					$("#channelName").addClass('errorClass');
					userNameExistBool = false;
				} else if (response == "InValid") {
					$("#cname-exist").show();
					$("#cname-error").hide();
					$("#channelName").addClass('errorClass');
					userNameExistBool = false;
				} else {
					$("#cname-exist").hide();
					$("#cname-error").hide();
					$('#channelName').removeClass('errorClass');
					userNameExistBool = true;
				}
			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};
		return $.ajax(ajax_params);
	};
	
	validateApiKey = function() {
		var ajax_params;
		//alert("inside validate apikey of Add with apikey :"+$("#apikey").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateApiKey.htm",
			type : "GET",
			data : {
				"apikey" : $("#apikey").val()
			},
			success : function(response) {
				 if (response == "InValid") {
					$("#apikey-exist").show();
				} else {
					$("#apikey-exist").hide();
				}
			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};
		return $.ajax(ajax_params);
	};	

	
	validateNameEdit = function() {
		var ajax_params;
		var name=document.forms[1].elements["name"].value;
		//alert("Before edit field validation befor ajax validation call for name:"+name+":ID:"+$("#id").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateNameEdit.htm",
			type : "GET",
			data : {
				"name" : name,
				"id" : $("#id").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#nameEdit-exist").hide();
					$("#nameEdit-error").show();
					$(".nameEdit").addClass('errorClass');
					emailExistForEditBool = false;
				} else if (response == "InValid") {
					$("#nameEdit-exist").show();
					$("#nameEdit-error").hide();
					$(".nameEdit").addClass('errorClass');
					emailExistForEditBool = false;
				} else {
					$("#nameEdit-exist").hide();
					$("#nameEdit-error").hide();
					$(".nameEdit").removeClass('errorClass');
					emailExistForEditBool = true;
				}

			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};

		return $.ajax(ajax_params);
	};
	
	validateApiKeyEdit = function() {
		var ajax_params;
		var apikey=document.forms[1].elements["apikey"].value;
		//alert("Before edit field validation befor ajax validation call for apikey:"+apikey+":ID:"+$("#id").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateApiKeyEdit.htm",
			type : "GET",
			data : {
				"apikey" : apikey,
				"id" : $("#id").val()
			},
			success : function(response) {
				if (response == "InValid") {
					$("#apikeyEdit-exist").show();
				} else {
					$("#apikeyEdit-exist").hide();
				}

			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};

		return $.ajax(ajax_params);
	};

	 $(function() {
		function validateChannelName(channelName) {
			var pattern = /^[a-zA-Z0-9]+$/;
			if (pattern.test(channelName) && channelName != '') {
				$('#cname-error').hide();
				
				$('#channelName').removeClass('errorClass');
				return true;

			} else {
				$('#cname-error').show();
				$('#channelName').addClass('errorClass');
				return false;

			}
		}

	validateEdit=function() {
			var cname=document.forms[1].elements["name"].value;

			var channelNameBool = validateChannelName(cname);
			
			if (channelNameBool) {
				return true;
			} else {
				return false;
			}
		};
		
		$("#register").click(
				function() {
					var cname=document.getElementById('name').value;
					//alert("you clicked on create channel for channel name"+cname);
					
					//alert("before calling disable function:update4");
					//$("#register").disabled=true;
					//$("#myModal").next(".ui-dialog-buttonpane button:contains('SaveChannel')")
		            //  .attr("disabled", true);
					//$(".ui-dialog-buttonpane button:contains('SaveChannel')").attr("disabled", true)
                   // .addClass("ui-state-disabled");
					//$("#register").button("disable");
					//$("#register").button("option", "disabled", true);
					//$("#register").addprop('disabled', disabled);
					
					//var apikey=document.getElementById('apikey').value;
					//var sourceip=document.getElementById('sourceIP').value;
					
					alert ("beforeButtonDisable1");
					//$("#register").addClass("disable-bt");
					//document.getElementById("register").disabled=true;
					
					var channelNameBool = validateChannelName(cname);
					
					//alert ("value from chanel boolean "+channelNameBool);

					if (channelNameBool) {
						var formDataObject = {
								name : document.getElementById('name').value,
								apikey : document.getElementById('apikey').value,
								sourceIP : document.getElementById('sourceIP').value
						};						
						return register_channel(formDataObject);
					} else {
						return false;
					}
				});
		
	register_channel = function(formDataObject) {
			var ajax_params;
			
			ajax_params = {
				url : "/alerts/cadmin/channel.htm",
				type : "POST",
				data : $("#channelSource").serialize(),
				success : function(response) {

					//	window.location.href = "./channel.htm";
					    $("#close-modal").click();
					    $("#addSuccess").show();
					    $("#updateSuccess").hide();
					    $("#deleteSuccess").hide();		
					    
					    var row = "<tr id=\"channelDetail_"+response.id+"\">"+
						"<td class=\"channelName\">"+  formDataObject["name"]    +"</td>"+
						"<td class=\"apikey\">"+   formDataObject["apikey"]   +"</td>"+
						"<td class=\"sourceip\">"+  formDataObject["sourceIP"]   +"</td>" +
						"<td class=\"style3 activeChannel\">Active </td>" +
						"<td class=\"style1\">" +
						"<div><a class=\"editChannelLink\"  data-channel-id=\""+ response.id +
						"\" data-toggle=\"modal\" data-target=\"#myModalEdit\" href=\"#\"><i  class=\"ace-icon fa fa-lg fa-edit\"></i> </a> " +
						"<a class=\"deleteChannelLink\" data-channel-id=\""+   response.id  +
						"\" href=\"#\"  data-toggle=\"modal\" data-target=\"#confirm-delete\"><i  class=\"ace-icon fa fa-lg fa-trash-o\"></i></a>"+
						"</div> </td>"+
						"</tr>",
						$row = $(row),
						resort = true;
					    
					   // alert("To be added row "+row);
						
						$("#channelTable")
					      .find('tbody').append($row);
						$("#channelTable").trigger('sortReset');
						$("#channelTable").trigger('update');
						/*
						$("#channelTable").trigger('update').trigger("appendCache").trigger("sorton",[[0,1]]);
						$("#channelTable").tablesorter();*/
						
						
						
						// To be deleted 
					    //.trigger('addRows', [$row, resort]);
						//$("#channelTable").data('tablesorter').sortList=[[0,1]];
						//$("#channelTable").trigger('refresh');
						//callback = function(table){ /* do something */ };
						//$("#channelTable").trigger('updateAll', [this, resort, callback]);
					    //$("#channelTable").trigger("update",[this, resort, callback]);
					    
						
						return false;
				},
				error : function(err) {
					return alert(err.responseText);
					//return alert('There was some error reaching the server at this time. Please try again later.');
				}
			};
			return $.ajax(ajax_params);
		};
		
	$.extend($.tablesorter.themes.bootstrap, {
			// these classes are added to the table. To see other table classes available,
			// look here: http://twitter.github.com/bootstrap/base-css.html#tables
			table : 'table table-bordered',
			caption : 'caption',
			header : 'bootstrap-header', // give the header a gradient background
			footerRow : '',
			footerCells : '',
			icons : '', // add "icon-white" to make them white; this icon class is added to the <i> in the header
			sortNone : 'bootstrap-icon-unsorted',
			sortAsc : 'icon-chevron-up glyphicon glyphicon-chevron-up', // includes classes for Bootstrap v2 & v3
			sortDesc : 'icon-chevron-down glyphicon glyphicon-chevron-down', // includes classes for Bootstrap v2 & v3
			active : '', // applied when column is sorted
			hover : '', // use custom css here - bootstrap class may not override it
			filterRow : '', // filter row class
			even : '', // odd row zebra striping
			odd : '' // even row zebra striping
		});

		// call the tablesorter plugin and apply the uitheme widget
	$("table").tablesorter({
			// this will apply the bootstrap theme if "uitheme" widget is included
			// the widgetOptions.uitheme is no longer required to be set
			theme : "bootstrap",
			
			sortList : [[0,1]],

			widthFixed : true,

			headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!

			// widget code contained in the jquery.tablesorter.widgets.js file
			// use the zebra stripe widget if you plan on hiding any rows (filter widget)
			widgets : [ "uitheme", "filter", "zebra" ],

			widgetOptions : {
				// using the default zebra striping class name, so it actually isn't included in the theme variable above
				// this is ONLY needed for bootstrap theming if you are using the filter widget, because rows are hidden
				
				zebra : [ "even", "odd" ],

				// reset filters button
				filter_reset : ".reset"

			// set the uitheme widget to use the bootstrap theme class names
			// this is no longer required, if theme is set
			// ,uitheme : "bootstrap"

			}//,
			//widgetZebra:{css: ['normal-row', 'alt-row']}
		}).tablesorterPager({

			// target the pager markup - see the HTML block below
			container : $(".ts-pager"),

			// target the pager page select dropdown - choose a page
			cssGoto : ".pagenum",

			// remove rows from the table to speed up the sort of large tables.
			// setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
			removeRows : false,

			// output string - default is '{page}/{totalPages}';
			// possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
			output : '{startRow} - {endRow} / {filteredRows} ({totalRows})'

		});//.find('tr:nth-child(even)').css('background-color', 'white').end().find('tr:nth-child(odd)').css('background-color', 'lightgray').end();

	});

					
	$('#confirm-delete').on(
				'show.bs.modal',
				function(e) {
					$(this).find('.danger').attr('href',
							$(e.relatedTarget).data('href'));
					$('.debug-url').html(
							'Delete URL: <strong>'
									+ $(this).find('.danger').attr('href')
									+ '</strong>');
				});
	$('#createChannelLink').on("click", function() {
			$('#name').val("");
			$("#cname-exist").hide();
			$("#apikey-exist").hide();
			$("#cname-error").hide();
			$('#name').removeClass('errorClass');
			$('#apikey').val("");
			$('#sourceIP').val("");
		});