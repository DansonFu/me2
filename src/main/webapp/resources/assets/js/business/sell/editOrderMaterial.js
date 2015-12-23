var selectMaterialEditId = "";
var autoNum =-1;
function getautoId(){
	return autoNum--;
}
var subIframeSelectData = null;

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/sellOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称', '规格','产品编号','色号','单价','件数', '总片数', '面积','金额','优惠金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%', editable:false,hidden:true},
			{name:'product.name',index:'name', width:'10%', editable:false},
			{name:'product.spec',index:'spec', width:'10%', editable:false},
			{name:'product.num',index:'product.num', width:'10%', editable:false},
			{name:'product.color',index:'color', width:'10%', editable:false,edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.price',index:'price', width:'10%', editable:false},
			{name:'pieces',index:'pieces', width:'10%', editable:true,hidden:true},
			{name:'slices',index:'slices', width:'10%', editable:true},
			{name:'area',index:'area', width:'10%', editable:false},
			{name:'amount',index:'amount', width:'10%', editable:false},
			{name:'discount',index:'discount', width:'10%', editable:false}
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
		caption: "修改商品",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector).jqGrid('resetSelection');
		    
		    //设置编辑数据
		    selectMaterialEditId = rowid;
			var rowData = $("#grid-table").jqGrid("getRowData",rowid);
			setEditMatrailData(rowData);
			$("#addMaterialType").val("edit");
		    return (true);
		},
		gridComplete:function(){
			//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table").hide();
			
			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
			var sum=0;
			for(var i=0;i<rowDatas.length;i++){
				sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
			}
			$(grid_selector).footerData("set",{amount:"合计",discount:sum});
		},
		pgbuttons:false,	//是否显示分页按钮
		pginput:false,	//是否显示分页输入框
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
	
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
			//edit record form
			closeAfterEdit: true,
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			},
			afterSubmit : onEditafterSubmit
		},
		{
			//new record form
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			},
			afterSubmit : function(response, postdata){
				if(response.responseJSON.message=="success"){
					return [true];
				}else{
					return [false];
				}
			}
		},
		{
			//delete record form
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				if(form.data('styled')) return false;
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_delete_form(form);
				
				form.data('styled', true);
			},
			onClick : function(e) {
			},
			afterSubmit:onAfterSubmit
		},
		{
			//search form
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				style_search_form(form);
			},
			afterRedraw: function(){
				style_search_filters($(this));
			}
			,
			multipleSearch: true,
			/**
			multipleGroup:true,
			showQuery: true
			*/
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
			var myid=$("#grid-table").jqGrid("getGridParam",'selarrrow');
			$("#grid-table").jqGrid("delRowData",myid);
			$("#addMaterialId").val("");
			$("#addMaterialName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#addMaterialDiscount").val("");
			subIframeSelectData = null;
			selectMaterialEditId = null;
			$("#addMaterialType").val("");
		}, 
		position: "first", 
		title:"删除商品", 
		cursor: "pointer",
		id:"delButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-pencil blue", 
		onClickButton:function(){
			$("#addEditDiv").show();
			var myid=$("#grid-table").jqGrid("getGridParam",'selarrrow');
			if(myid!=""){
				$("#addEditDiv").show();
				selectMaterialEditId = myid;
				var rowData = $("#grid-table").jqGrid("getRowData",myid);
				setEditMatrailData(rowData);
				$("#addMaterialType").val("edit");
				$("#andEditText").html("修改购买商品信息");
			}else{
				fDialogAlert("请选择要修改的数据行");
			}
			
		}, 
		position: "first", 
		title:"修改商品", 
		cursor: "pointer",
		id:"editButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-plus-sign green", 
		onClickButton:function(){
			$("#addEditDiv").show();
			$("#addMaterialPieces").focus();
			
			jQuery(grid_selector).resetSelection();
			
			$("#addMaterialId").val("");
			$("#addMaterialName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#addMaterialDiscount").val("");
			
			subIframeSelectData = null;
			
			$("#andEditText").html("添加购买商品信息");
			$("#addMaterialType").val("add");
		}, 
		position: "first", 
		title:"添加商品", 
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
	
	function onAfterSubmit(response, postdata)
	{
		if(response.responseJSON.message=="success"){
			return [true];
		}else{
			return [false];
		}
	} 
	
	function onEditafterSubmit(response, postdata){
		if(response.responseJSON.message=="success"){
			return [true];
		}else{
			return [false];
		}
	}
});

function billSubmit(isStartProcess){
	var griddata=jQuery("#grid-table").jqGrid("getRowData");
	for(var i=0;i<griddata.length;i++){
		for(var name in griddata[i]){
			if(name=="product.productId"){
				griddata[i]["product"]={};
				griddata[i]["product"]["productId"]=griddata[i][name];
				break;
			}
		}
	}
	
	griddata = JSON.stringify(griddata);
	//var contactId=$("#contactId2").val();
	var contactId=$("input[name=contactId]").val();
	var contactName=$("#contactName").val();
	var contactNameHidden=$("#contactNameHidden").val();
	var phone=$("#phone").val();
	var note=$("#note").val();
	var address=$("#address").val();
	var mobile=$("#mobile").val();
	var needPay=$("#needPay").val();
	var productType=$("#productType").val();
	var orderId=$("#orderId").val();
	var amount=$("#grid-table").footerData("get").discount;
	var seId=$("#shipmentEnterpriseId").val();
	
	if(contactName==null||contactName==""){
		fDialogAlert("客户不能为空");
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
	
	var jsondata =jsondata = {"isStartProcess":isStartProcess,"phone":phone,"note":note,"address":address,"mobile":mobile,"needPay":needPay,"productType":productType,"amount":amount,"griddata":griddata,"name":contactName,"contact.contactId":contactId,"contact.name":contactNameHidden,"orderId":orderId,"shipmentEnterprise.seId":seId};
	
    $.ajax({
		url:basePath+"erp/sellOrder/editOrder",
		type:"post",
		datatype:"json",
//		contentType: "application/json;charset=utf-8",
		//data:{"isStartProcess":isStartProcess,"contact.contactId":contactId,"phone":phone,"note":note,"address":address,"mobile":mobile,"needPay":needPay,"productType":productType,"orderId":orderId,"amount":amount,"griddata":griddata},
		//data:dataobject,//,"contactId="+contactId+"&phone="+phone+"&note="+note+"&address="+address+"&mobile="+mobile+"&griddata="+griddata,
		data:jsondata,
		success:function(data){
			if(data.message=="success"){
				window.location=basePath+"erp/toSellOrder";
			}else{
				fDialogAlert("保存失败");
			}
		}
    });
}

function billReset(){
	$.ajax({
		url:basePath+"erp/sellOrder/editOrderMaterialReset",
		type:"post",
		datatype:"json",
		data:{"orderId":$("#orderId").val()},
		success:function(data){
			document.getElementById("editOrderMaterialForm").reset();
			$("#contactId").val(data.order.contact.contactId);
			$("#contactNameHidden").val(data.order.contact.name);
			//$("#shipmentEnterpriseId").val(data.order.shipmentEnterprise.seId);
			
			jQuery("#grid-table").jqGrid().clearGridData();
			for(var i=0;i<data.orderMaterial.length;i++){
				jQuery("#grid-table").jqGrid().addRowData(data.orderMaterial[i].orderMaterialId,
						{"product.productId":data.orderMaterial[i].product.productId,
					"product.num":data.orderMaterial[i].product.num,
					"product.name":data.orderMaterial[i].product.name,
					"product.spec":data.orderMaterial[i].product.spec,
					"product.color":data.orderMaterial[i].product.color,
					"product.price":data.orderMaterial[i].product.price,
					"pieces":data.orderMaterial[i].pieces,
					"slices":data.orderMaterial[i].slices,
					"area":data.orderMaterial[i].area,
					"amount":data.orderMaterial[i].amount,
					"orderMaterialId":data.orderMaterial[i].orderMaterialId},"last");
			}
		}
    });
	
	var shipmentEnterpriseIdHidden=$("#shipmentEnterpriseIdHidden").val();
	$.ajax({
		url:basePath+"erp/customInfo/getContactLogisticsList",
		type:"post",
		datatype:"json",
		data: {"contactId":$("#contactId").val()},
		success:function(data){
			var content="";
			for(var i=0;i<data.rows.length;i++){
				content=content+"<option value='"+data.rows[i].shipmentEnterprise.seId+"' "+(shipmentEnterpriseIdHidden==data.rows[i].shipmentEnterprise.seId?"selected":"")+">"+data.rows[i].shipmentEnterprise.name+"</option>"
			}
			$("#shipmentEnterpriseId").html(content);
		}
    });
	
}

function billReturn(){
	window.location=basePath+"erp/toSellOrder";
}


//############################################################
function selectMaterialOnclick(){
	$.fDialog({
		title : '<b>商品信息列表</b>',
		content : basePath+'erp/selectMaterial?productId='+$("#addMaterialId").val(),
		module : 'iframe',
		w : '80%',
		h : '523',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectData){
				$("#addMaterialId").val(subIframeSelectData.productId);
				$("#addMaterialName").val(subIframeSelectData.name);
			}
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function addMaterialSubmit(){
	var data=null;
	var rowid=$("#grid-table").jqGrid("getGridParam",'selarrrow');
	if(rowid!=null&&rowid!=""){
		var rowData = $("#grid-table").jqGrid("getRowData",rowid);
		$.ajax({
			async:false,
			url:basePath+"erp/material/getMaterialById",
			type:"post",
			datatype:"json",
			data:{"productId":rowData["product.productId"]},
			success:function(ajaxdata){
				data=ajaxdata;
			}
	    });
	}
	
	var postdata={};
	//(1)获取数据
	if(subIframeSelectData!=null){
		data = subIframeSelectData;
	}
	var addMaterialPieces = $("#addMaterialPieces").val();
	var addMaterialSlices = $("#addMaterialSlices").val();
	var addMaterialDiscount = $("#addMaterialDiscount").val();
	
	if(addMaterialPieces!=null&&addMaterialPieces!=""){
		if(!((!isNaN(addMaterialPieces))&&(parseInt(addMaterialPieces)==addMaterialPieces)&&(addMaterialPieces>=0))){
			fDialogAlert("商品件数：请输入一个非负整数");
			return;
		}
	}else{
		addMaterialPieces=0;
	}
	
	if(addMaterialSlices!=null&&addMaterialSlices!=""){
		if(!((!isNaN(addMaterialSlices))&&(parseInt(addMaterialSlices)==addMaterialSlices)&&(addMaterialSlices>=0))){
			fDialogAlert("商品散片数：请输入一个非负整数");
			return;
		}
	}else{
		addMaterialSlices=0;
	}
	
	if(addMaterialDiscount!=null&&addMaterialDiscount!=""){
		if(!((!isNaN(addMaterialDiscount))&&(addMaterialDiscount>=0))){
			fDialogAlert("优惠金额：请输入一个非负数");
			return;
		}
	}else{
		addMaterialDiscount=0;
	}
	
	if(addMaterialPieces==0&&addMaterialSlices==0){
		fDialogAlert("商品总片数不能为0");
		return;
	}
	
	//(2)设置数据
	postdata.area=((Number(data.silces*addMaterialPieces)+Number(addMaterialSlices))*data.area).toFixed(6);
	postdata.amount=(postdata.area*data.price).toFixed(2);
	postdata.product={};
	postdata.product.name=data.name;
	postdata.product.num=data.num;
	postdata.product.spec=data.spec;
	postdata.product.color=data.color;
	postdata.product.price=data.price;
	postdata.product.productId=data.productId;
	postdata.pieces=addMaterialPieces;
	postdata.slices=addMaterialSlices;
	postdata.discount=addMaterialDiscount;
	var addMaterialType = $("#addMaterialType").val()
	if(addMaterialType=="edit"){
		jQuery("#grid-table").jqGrid().setRowData(selectMaterialEditId,postdata);
		
		var rowDatas=jQuery("#grid-table").jqGrid().getRowData();
		var sum=0;
		for(var i=0;i<rowDatas.length;i++){
			sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
		}
		$("#grid-table").footerData("set",{amount:"合计",discount:sum});
	}else{
		jQuery("#grid-table").jqGrid().addRowData(getautoId(),postdata,"last");
	}
	
	
	//(3)清除数据并关闭dov
	$("#addMaterialType").val("");
	$("#addMaterialId").val("");
	$("#addMaterialName").val("");
	$("#addMaterialPieces").val("");
	$("#addMaterialSlices").val("");
	$("#addMaterialDiscount").val("");
	subIframeSelectData=null;
}

function subIframeSetSelectData(data){
	subIframeSelectData = data;
}

function setEditMatrailData(data){
	$("#addMaterialId").val(data["product.productId"]);
	$("#addMaterialName").val(data["product.name"]);
	$("#addMaterialPieces").val(data.pieces);
	$("#addMaterialSlices").val(data.slices);
	$("#addMaterialDiscount").val(data.discount);
}
//############################################################

//##################################################
//弹出选择客户页面
var subIframeSelectDataContact = null;
function selectContactOnclick(){
	$.fDialog({
		title : '<b>客户信息列表</b>',
		content : basePath+'erp/selectCustom?customId='+$("#contactId").val(),
		module : 'iframe',
		w : '80%',
		h : '526',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataContact){
				$("#contactId").val(subIframeSelectDataContact.contactId);
				$("#contactName").val(subIframeSelectDataContact.name);
				$("#contactNameHidden").val(subIframeSelectDataContact.name);
				$("#phone").val(subIframeSelectDataContact.phone);
				$("#mobile").val(subIframeSelectDataContact.mobile);
				$("#address").val(subIframeSelectDataContact.address);
				subIframeSelectDataContact=null;
				//向选择物流商的select标签中填充数据
				$.ajax({
					url:basePath+"erp/customInfo/getContactLogisticsList",
					type:"post",
					datatype:"json",
					data: {"contactId":$("#contactId").val()},
					success:function(data){
						var content=null;
						for(var i=0;i<data.rows.length;i++){
							content=content+"<option value='"+data.rows[i].shipmentEnterprise.seId+"'>"+data.rows[i].shipmentEnterprise.name+"</option>"
						}
						$("#shipmentEnterpriseId").html(content);
					}
			    });
			}
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function subIframeSetSelectDataContact(data){
	subIframeSelectDataContact = data;
}
//##################################################
//##################################################
//弹出选择物流商页面
var subIframeSelectDataShipmentEnterprise = null;
function selectShipmentEnterpriseOnclick(){
	$.fDialog({
		title : '<b>物流商信息列表</b>',
		content : basePath+'erp/selectShipmentEnterprise?seId='+$("#shipmentEnterpriseId").val(),
		module : 'iframe',
		w : '80%',
		h : '522',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataShipmentEnterprise){
//				$("#shipmentEnterpriseId").val(subIframeSelectDataShipmentEnterprise.seId);
//				$("#shipmentEnterprisetName").val(subIframeSelectDataShipmentEnterprise.name);
				var content="<option value='"+subIframeSelectDataShipmentEnterprise.seId+"'>"+subIframeSelectDataShipmentEnterprise.name+"</option>"
				$("#shipmentEnterpriseId").html(content);
				subIframeSelectDataShipmentEnterprise=null;
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
	//向选择物流商的select标签中填充数据
	var shipmentEnterpriseIdHidden=$("#shipmentEnterpriseIdHidden").val();
	$.ajax({
		url:basePath+"erp/customInfo/getContactLogisticsList",
		type:"post",
		datatype:"json",
		data: {"contactId":$("#contactId").val()},
		success:function(data){
			var content="";
			for(var i=0;i<data.rows.length;i++){
				content=content+"<option value='"+data.rows[i].shipmentEnterprise.seId+"' "+(shipmentEnterpriseIdHidden==data.rows[i].shipmentEnterprise.seId?"selected":"")+">"+data.rows[i].shipmentEnterprise.name+"</option>"
			}
			$("#shipmentEnterpriseId").html(content);
		}
    });
});