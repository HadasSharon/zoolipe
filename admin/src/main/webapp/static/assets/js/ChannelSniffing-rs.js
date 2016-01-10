$(document).ready(function(event) {
	
	//$(".deleteResourceLink").click(function(event) {
	$("#resourceTable").on('click', '.deleteResourceLink',	function() {
		$("#deleteError").hide('slow');
		document.getElementById("deleteResourceIdButton").disabled=false;
 		var resourcelId = $(this).data("resource-id");
 		var resourceDetailTr = $("#resourceDetail_"+resourcelId);
		$("#deleteResourceId").val(resourcelId);
		
		var resourceName = resourceDetailTr.find(".resourceName").html();
		
		$("#deleteConf").html("You are about to delete Service <B>"+resourceName +"</B> & any associated <B>Throttles</B>, this procedure is irreversible.<br/> Do you want to proceed?");
		$("#deleteConf").show('slow');
	}); 

	$("#deleteResourceIdButton").click(function(event) {
		var resourceId = $("#deleteResourceId").val();
		document.getElementById("deleteResourceIdButton").disabled=true;
		//alert("deleting Resource with id "+resourceId);
	
		$.ajax({
			type: "post",
			cache: false,
			url: "/alerts/cadmin/deleteResource.htm",
 			data: "id=" + resourceId,
			success: function(response) {
				if (response.status == "SUCCESS") {
				    $('#addSuccess').hide();
				    $('#updateSuccess').hide();
				    $('#deleteSuccess').show();
					$("#cancel-delete").click();	
					document.getElementById("deleteResourceIdButton").disabled=false;
					$("#resourceDetail_"+resourceId).closest('tr').remove();
				    $("#resourceTable").trigger('update');
				}
				else{
					var errorInfo = response.status;
					document.getElementById("deleteResourceIdButton").disabled=false;
					$("#deleteError").html(errorInfo +" <br/> Error updating gateway ");
					$("#deleteError").show('slow');
				}

			},
			error: function(err) {
				alert(err.responseText);
				document.getElementById("deleteResourceIdButton").disabled=false;
				return false;
			}
		}); 		
		event.preventDefault();
        event.stopPropagation();
        
	});

	
	//$(".editResourceLink").click(function(event) {
	$("#resourceTable").on('click','.editResourceLink',function() {
		$("#editError").hide('slow');
		document.getElementById("editSubmit").disabled=false;
		var resrouceid = $(this).data("resource-id");
		
		var resourceDetailTr = $("#resourceDetail_"+resrouceid);
		var editForm = $("#editForm");
		//alert("edit id:"+userId);
		
		//alert("Name:"+resourceDetailTr.find(".resourceName").html()+":Path:"+resourceDetailTr.find(".resourcePath").html()+":ID:"+resrouceid);
		editForm.find("#id").val(resrouceid);
		editForm.find("#name").val(resourceDetailTr.find(".resourceName").html());
		editForm.find("#resourcePath").val(resourceDetailTr.find(".resourcePath").html());

		
		var activeStr = resourceDetailTr.find(".activeResource").html().trim();
		if("Active" === activeStr) {
			editForm.find(".editResourceActive").prop('checked', true);
		} else {
			editForm.find(".editResourceNotActive").prop('checked', true);
		}
		
		editForm.trigger('refresh');
	});
	
	$("#editFormReset").click(function(event) {
		$("#editForm").trigger("reset");
		$("#editForm").find("input:radio, input:checkbox").removeAttr('checked').removeAttr('selected');
		$("#editSuccess").hide('slow');
		$("#editError").hide('slow');
		$(".nameEdit").removeClass('errorClass');
		$("#rnameEdit-exist").hide('slow');
		$("#rnameEdit-error").hide('slow');
		$("#rpathEdit-exist").hide('slow');
		$("#rpathEdit-error").hide('slow');
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
			url: "/alerts/cadmin/updateResource.htm",
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
					var resourcePath = response.result.resourcePath;  
					var active = response.result.active;

					//alert("values to be updated result: "+IDValue+":"+name+":"+apikey+":"+sourceip+":"+active);
					
					var resort = "";
					var resortname=true;
					callback = function(table){ /* do something */ };
				    $("#resourceDetail_"+IDValue+" .resourceName").text(name);
				    $("#resourceTable").trigger("updateCell",[this, resortname, callback]);
	
					callback = function(table){ /* do something */ };
				    $("#resourceDetail_"+IDValue+" .resourcePath").text(resourcePath);
				    $("#resourceTable").trigger("updateCell",[this, resort, callback]);
				    
				    
					if(1 == active) {
						resort = true;
						callback = function(table){ /* do something */ };
					    $("#resourceDetail_"+IDValue+" .activeResource").text("Active");
					    $("#resourceTable").trigger("updateCell",[this, resort, callback]);
					} else {
						resort = true;
						callback = function(table){ /* do something */ };
					    $("#resourceDetail_"+IDValue+" .activeResource").text("Not active");
					    $("#resourceTable").trigger("updateCell",[this, resort, callback]);
					}
				    
					
				} else {
					var errorInfo = response.status;
					document.getElementById("editSubmit").disabled=false;
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


	validateResourceName = function() {
		var ajax_params;
		//alert("inside validate chanenl name of Add with name :"+$("#name").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateResourceName.htm",
			type : "GET",
			data : {
				"resourceName" : $("#name").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#rname-exist").hide();
					$("#rname-error").show();
					$("#resourceName").addClass('errorClass');
				} else if (response == "InValid") {
					$("#rname-exist").show();
					$("#rname-error").hide();
					$("#resourceName").addClass('errorClass');
				} else {
					$("#rname-exist").hide();
					$("#rname-error").hide();
					$('#resourceName').removeClass('errorClass');
				}
			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};
		return $.ajax(ajax_params);
	};
	
	validateResourcePath = function() {
		var ajax_params;
		//alert("inside validate apikey of Add with apikey :"+$("#apikey").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateResourcePath.htm",
			type : "GET",
			data : {
				"resourcePath" : $("#resourcePath").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#resourcePath-exist").hide();
					$("#resourcePath-error").show();
					$("#resourcePath").addClass('errorClass');
				} else if (response == "InValid") {
					$("#resourcePath-exist").show();
					$("#resourcePath-error").hide();
					$("#resourcePath").addClass('errorClass');
				} else {
					$("#resourcePath-exist").hide();
					$("#resourcePath-error").hide();
					$("#resourcePath").removeClass('errorClass');
				}
			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};
		return $.ajax(ajax_params);
	};	

	
	validateResourceNameEdit = function() {
		var ajax_params;
		var name=document.forms[1].elements["name"].value;
		//alert("Before edit field validation befor ajax validation call for name:"+name+":ID:"+$("#id").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateResourceNameEdit.htm",
			type : "GET",
			data : {
				"name" : name,
				"id" : $("#id").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#rnameEdit-exist").hide();
					$("#rnameEdit-error").show();
					$(".nameEdit").addClass('errorClass');
					emailExistForEditBool = false;
				} else if (response == "InValid") {
					$("#rnameEdit-exist").show();
					$("#rnameEdit-error").hide();
					$(".nameEdit").addClass('errorClass');
					emailExistForEditBool = false;
				} else {
					$("#rnameEdit-exist").hide();
					$("#rnameEdit-error").hide();
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
	
	validateResourcePathEdit = function() {
		var ajax_params;
		var resourcePath=document.forms[1].elements["resourcePath"].value;
		//alert("Before edit field validation befor ajax validation call for apikey:"+apikey+":ID:"+$("#id").val());
		ajax_params = {
			cache: false,
			url : "/alerts/cadmin/validateResourcePathEdit.htm",
			type : "GET",
			data : {
				"resourcePath" : resourcePath,
				"id" : $("#id").val()
			},
			success : function(response) {
				if (response == "InValidData") {
					$("#rPathEdit-exist").hide();
					$("#rPathEdit-error").show();
					$("#resourcePath").addClass('errorClass');
				} else if (response == "InValid") {
					$("#rPathEdit-exist").show();
					$("#rPathEdit-error").hide();
					$("#resourcePath").addClass('errorClass');
				} else {
					$("#rPathEdit-exist").hide();
					$("#rPathEdit-error").hide();
					$("#resourcePath").removeClass('errorClass');
				}
			},
			error : function() {
				return alert('There was some error reaching the server at this time. Please try again later.');
			}
		};

		return $.ajax(ajax_params);
	};

	 $(function() {
		function validateResourceName(resourceName) {
			var pattern = /^[a-zA-Z0-9]+$/;
			if (pattern.test(resourceName) && resourceName != '') {
				$('#rname-error').hide();
				
				$('#resourceName').removeClass('errorClass');
				return true;

			} else {
				$('#rname-error').show();
				$('#resourceName').addClass('errorClass');
				return false;

			}
		}
		function validateResourcePath(resourcePath) {
			if (resourcePath != '') {
				$('#resourcePath-error').hide();
				
				$('#resourceName').removeClass('errorClass');
				return true;

			} else {
				$('#resourcePath-error').show();
				$('#resourceName').addClass('errorClass');
				return false;

			}
		}		

	validateEdit=function() {
			var rname=document.forms[1].elements["name"].value;
			var rpath=document.forms[1].elements["resourcePath"].value;

			var rNameBool = validateResourceName(rname);
			var rPathBool = validateResourcePath(rpath);
			
			if (rNameBool && rPathBool) {
				return true;
			} else {
				return false;
			}
		};
		
		
		$("#register").click(
				function() {
					var rname=document.getElementById('name').value;
					var rpath=document.getElementById('resourcePath').value;
					//var apikey=document.getElementById('apikey').value;
					//var sourceip=document.getElementById('sourceIP').value;
					
					//alert ("values at add validation"+rname+":"+rpath);
					
					var resourceNameBool = validateResourceName(rname);
					var resourcePathBool = validateResourcePath(rpath);
					
					
					//alert ("value from resource boolean "+resourceNameBool+":"+resourcePathBool);

					if (resourceNameBool && resourcePathBool) {
						var formDataObject = {
								name : document.getElementById('name').value,
								resourcePath : document.getElementById('resourcePath').value
						};						
						return register_channel(formDataObject);
					} else {
						return false;
					}
				});
		
	register_channel = function(formDataObject) {
			var ajax_params;
			
			ajax_params = {
				url : "/alerts/cadmin/resource.htm",
				type : "POST",
				data : $("#channelResource").serialize(),
				success : function(response) {

					//	window.location.href = "./channel.htm";
					    $("#close-modal").click();
					    $("#addSuccess").show();
					    $("#updateSuccess").hide();
					    $("#deleteSuccess").hide();		
					    
					    var row = "<tr id=\"resourceDetail_"+response.id+"\">"+
						"<td class=\"resourceName\">"+  formDataObject["name"]    +"</td>"+
						"<td class=\"resourcePath\">"+   formDataObject["resourcePath"]   +"</td>"+
						"<td class=\"style3 activeResource\">Active </td>" +
						"<td class=\"style1\">" +
						"<div><a class=\"editResourceLink\"  data-resource-id=\""+response.id+
						"\" data-toggle=\"modal\" data-target=\"#myModalEdit\" href=\"#\"><i  class=\"ace-icon fa fa-lg fa-edit\"></i> </a> " +
						"<a class=\"deleteResourceLink\" data-resource-id=\""+response.id+
						"\" href=\"#\"  data-toggle=\"modal\" data-target=\"#confirm-delete\"><i  class=\"ace-icon fa fa-lg fa-trash-o\"></i></a>"+
						"</div> </td>"+
						"</tr>",
						$row = $(row),
						resort = true;
						
						$("#resourceTable")
					      .find('tbody').append($row);
						$("#resourceTable").trigger('sortReset');
						$("#resourceTable").trigger('update');

						/*
						$("#resourceTable").trigger('update').trigger("appendCache").trigger("sorton",[[0,1]]);
						$("#resourceTable").tablesorter();*/
					
						
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
	$('#createResourceLink').on("click", function() {
			$('#name').val("");
			$("#rname-exist").hide();
			$("#resourcePath-exist").hide();
			$("#rname-error").hide();
			$("#resourcePath-error").hide();
			$('#name').removeClass('errorClass');
			$('#resourcePath').removeClass('errorClass');
			$('#resourcePath').val("");
		});