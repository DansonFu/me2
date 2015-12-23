jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	jQuery(grid_selector).jqGrid({
		datatype: "local",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '名称','地址', '电话','手机号','电子邮箱','邮编'],//列显示名称
		colModel:[
			{name:'shipmentEnterprise.seId',index:'seId', width:'5%', editable:false,hidden:true,key:true},
			{name:'shipmentEnterprise.name',index:'name', width:'5%'},
			{name:'shipmentEnterprise.address',index:'address', width:'10%'},
			{name:'shipmentEnterprise.phone',index:'phone', width:'10%'},
			{name:'shipmentEnterprise.mobile',index:'mobile', width:'10%'},
			{name:'shipmentEnterprise.email',index:'email', width:'10%'},
			{name:'shipmentEnterprise.zipcode',index:'zipcode', width:'10%'}
		],//列属性
		viewrecords : true,//显示显示总记录数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "物流商信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		pgbuttons:false,	//是否显示分页按钮
		pginput:false,	//是否显示分页输入框
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	
	//switch element when editing inline
	function aceSwitch( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=checkbox]')
					.wrap('<label class="inline" />')
				.addClass('ace ace-switch ace-switch-5')
				.after('<span class="lbl"></span>');
		}, 0);
	}
	
	//enable datepicker
	function pickDate( cellvalue, options, cell ) {
		setTimeout(function(){
			$(cell) .find('input[type=text]')
					.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
		}, 0);
	}
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'icon-pencil blue',
			add: false,
			addicon : 'icon-plus-sign purple',
			del: false,
			delicon : 'icon-trash red',
			search: false,
			searchicon : 'icon-search orange',
			refresh: false,
			refreshicon : 'icon-refresh green',
			view: true,
			viewicon : 'icon-zoom-in grey',
		},
		{
			
		},
		{
			
		},
		{
			
		},
		{
			
		},
		{
			//view record form
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			}
		}
	)
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-trash red", 
		onClickButton:function(){
			var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
			$("#grid-table").jqGrid("delRowData",myid);
			
			$("#shipmentEnterpriseId").val("");
			$("#shipmentEnterpriseName").val("");
			
			subIframeSelectDataShipmentEnterprise = null;
		}, 
		position: "first", 
		title:"删除物流商", 
		cursor: "pointer",
		id:"delButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-plus-sign green", 
		onClickButton:function(){
			$("#addEditDiv").show();
			
			$("#shipmentEnterpriseId").val("");
			$("#shipmentEnterpriseName").val("");
			
			subIframeSelectDataShipmentEnterprise = null;
		}, 
		position: "first", 
		title:"添加物流商", 
		cursor: "pointer",
		id:"addButton"
	});
	
	function style_edit_form(form) {
		//enable datepicker on "sdate" field and switches for "stock" field
		form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
			.end().find('input[name=stock]')
				  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');

		//update buttons classes
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
		buttons.eq(1).prepend('<i class="icon-remove"></i>')
		
		buttons = form.next().find('.navButton a');
		buttons.find('.ui-icon').remove();
		buttons.eq(0).append('<i class="icon-chevron-left"></i>');
		buttons.eq(1).append('<i class="icon-chevron-right"></i>');
	}
	
	function style_delete_form(form) {
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
		buttons.eq(1).prepend('<i class="icon-remove"></i>')
	}
	
	function style_search_filters(form) {
		form.find('.delete-rule').val('X');
		form.find('.add-rule').addClass('btn btn-xs btn-primary');
		form.find('.add-group').addClass('btn btn-xs btn-success');
		form.find('.delete-group').addClass('btn btn-xs btn-danger');
	}
	
	function style_search_form(form) {
		var dialog = form.closest('.ui-jqdialog');
		var buttons = dialog.find('.EditTable')
		buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
		buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
		buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
	}
	
	function beforeDeleteCallback(e) {
		var form = $(e[0]);
		if(form.data('styled')) return false;
		
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_delete_form(form);
		
		form.data('styled', true);
	}
	
	function beforeEditCallback(e) {
		var form = $(e[0]);
		form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
		style_edit_form(form);
	}
	
	//it causes some flicker when reloading or navigating grid
	//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
	//or go back to default browser checkbox styles for the grid
	function styleCheckbox(table) {
	/**
		$(table).find('input:checkbox').addClass('ace')
		.wrap('<label />')
		.after('<span class="lbl align-top" />')


		$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
		.find('input.cbox[type=checkbox]').addClass('ace')
		.wrap('<label />').after('<span class="lbl align-top" />');
	*/
	}
	
	//unlike navButtons icons, action icons in rows seem to be hard-coded
	//you can change them like this in here if you want
	function updateActionIcons(table) {
		/**
		var replacement = 
		{
			'ui-icon-pencil' : 'icon-pencil blue',
			'ui-icon-trash' : 'icon-trash red',
			'ui-icon-disk' : 'icon-ok green',
			'ui-icon-cancel' : 'icon-remove red'
		};
		$(table).find('.ui-pg-div span.ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
		*/
	}
	
	//replace icons with FontAwesome icons like above
	function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
			'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
			'ui-icon-seek-next' : 'icon-angle-right bigger-140',
			'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}
	
	function enableTooltips(table) {
		$('.navtable .ui-pg-button').tooltip({container:'body'});
		$(table).find('.ui-pg-div').tooltip({container:'body'});
	}

});


function billSubmit(isStartProcess){
	var griddata=jQuery("#grid-table").jqGrid("getRowData");
	for(var i=0;i<griddata.length;i++){
		for(var name in griddata[i]){
			if(name=="shipmentEnterprise.seId"){
				griddata[i]["shipmentEnterprise"]={};
				griddata[i]["shipmentEnterprise"]["seId"]=griddata[i][name];
				break;
			}
		}
	}
	griddata = JSON.stringify(griddata);
	
	var name = $("#name").val();
	var contactId=$("#contactId").val();
	var address=$("#address").val();
	var phone=$("#phone").val();
	var mobile=$("#mobile").val();
	var email=$("#email").val();
	var zipcode=$("#zipcode").val();
	var contactPerson=$("#contactPerson").val();
	var qq=$("#qq").val();
	var fax=$("#fax").val();
	var taxNumber=$("#taxNumber").val();
	var openBank=$("#openBank").val();
	var account=$("#account").val();
	var invoiceAddress=$("#invoiceAddress").val();
	var note=$("#note").val();
	
	if(name==null||name==""){
		fDialogAlert("客户名称不能为空");
		return;
	}
	
	if(phone!=null&&phone!=""){
		var reg=RegExp("^([0-9]{3,4}-)?[0-9]{7,8}$");
		if(!reg.test(phone)){
			fDialogAlert("请填写正确的电话号码");
			return;
		}
	}
	
	if(mobile!=null&&mobile!=""){
		var reg=RegExp("^[0-9]{11}$");
		if(!reg.test(mobile)){
			fDialogAlert("请填写正确的手机号码");
			return;
		}
	}
	
	var jsondata = {"name":name,"contactId":contactId,"address":address,"phone":phone,"mobile":mobile,"email":email,"zipcode":zipcode,"contactPerson":contactPerson,"qq":qq,"fax":fax,"taxNumber":taxNumber,"openBank":openBank,"account":account,"invoiceAddress":invoiceAddress,"note":note,"griddata":griddata};

    $.ajax({
		url:basePath+"erp/customInfo/edit",
		type:"post",
		datatype:"json",
		data: jsondata,
		success:function(data){
			if(data.message=="success"){
				window.location=basePath+"erp/toCustomInfo";
			}else{
				fDialogAlert("保存失败");
			}
		}
    });
}

function billReset(){
	document.getElementById("addContactForm").reset();
	$("#shipmentEnterpriseId").val("");
	$("#shipmentEnterpriseName").val("");
	jQuery("#grid-table").jqGrid().clearGridData();
}

function billReturn(){
	window.location=basePath+"erp/toCustomInfo";
}


function addShipmentEnterpriseSubmit(){
	if(subIframeSelectDataShipmentEnterprise===null){
		fDialogAlert("请选择一个物流商");
		return;
	}
	var griddata=jQuery("#grid-table").jqGrid("getRowData");
	for(var i=0;i<griddata.length;i++){
		if(subIframeSelectDataShipmentEnterprise.seId==griddata[i]['shipmentEnterprise.seId']){
			fDialogAlert("不能添加相同的物流商");
			return;
		}
	}
	jQuery("#grid-table").jqGrid().addRowData(subIframeSelectDataShipmentEnterprise.seId,
			{"shipmentEnterprise.seId":subIframeSelectDataShipmentEnterprise.seId,
		"shipmentEnterprise.name":subIframeSelectDataShipmentEnterprise.name,
		"shipmentEnterprise.address":subIframeSelectDataShipmentEnterprise.address,
		"shipmentEnterprise.phone":subIframeSelectDataShipmentEnterprise.phone,
		"shipmentEnterprise.mobile":subIframeSelectDataShipmentEnterprise.mobile,
		"shipmentEnterprise.email":subIframeSelectDataShipmentEnterprise.email,
		"shipmentEnterprise.zipcode":subIframeSelectDataShipmentEnterprise.zipcode},"last");
	$("#shipmentEnterpriseId").val("");
	$("#shipmentEnterpriseName").val("");
	subIframeSelectDataShipmentEnterprise=null;
}

function addShipmentEnterpriseCancel(){
	$("#shipmentEnterpriseId").val("");
	$("#shipmentEnterpriseName").val("");
	subIframeSelectDataShipmentEnterprise=null;
}

//##################################################
//弹出选择物流商页面
var subIframeSelectDataShipmentEnterprise = null;
function selectShipmentEnterpriseOnclick(){
	$.fDialog({
		title : '<b>物流商信息列表</b>',
		content : basePath+'erp/selectShipmentEnterprise',
		module : 'iframe',
		w : '80%',
		h : '526',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataShipmentEnterprise){
				$("#shipmentEnterpriseId").val(subIframeSelectDataShipmentEnterprise.seId);
				$("#shipmentEnterpriseName").val(subIframeSelectDataShipmentEnterprise.name);
			}
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function subIframeSetSelectDataShipmentEnterprise(data){
	subIframeSelectDataShipmentEnterprise = data;
}
//##################################################

$(function(){
	$.ajax({
		url:basePath+"erp/customInfo/getContactLogisticsList",
		type:"post",
		datatype:"json",
		data:{"contactId":$("#contactId").val()},
		success:function(data){
			for(var i=0;i<data.rows.length;i++){
				jQuery("#grid-table").jqGrid().addRowData(data.rows[i].seId,
						{"shipmentEnterprise.seId":data.rows[i].shipmentEnterprise.seId,
					"shipmentEnterprise.name":data.rows[i].shipmentEnterprise.name,
					"shipmentEnterprise.address":data.rows[i].shipmentEnterprise.address,
					"shipmentEnterprise.phone":data.rows[i].shipmentEnterprise.phone,
					"shipmentEnterprise.mobile":data.rows[i].shipmentEnterprise.mobile,
					"shipmentEnterprise.email":data.rows[i].shipmentEnterprise.email,
					"shipmentEnterprise.zipcode":data.rows[i].shipmentEnterprise.zipcode},"last");
			}
		}
    });
});