var autoNum =0;
function getautoId(){
	return autoNum++;
}

var subIframeSelectData = null;

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector= "#grid-pager";
	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/returnOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称','规格','产品编号','色号','单价','件数', '总片数', '面积','总金额','优惠金额','单片面积','每箱片数'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true,key:true},
			{name:'product.productId',index:'productId', width:'5%',hidden:true},
			{name:'product.name',index:'name', width:'10%'},
			{name:'product.spec',index:'spec', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.color',index:'color', width:'10%'},
			{name:'product.price',index:'price', width:'10%'},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%'},
			{name:'area',index:'area', width:'10%'},
			{name:'amount',index:'amount', width:'10%'},
			{name:'discount',index:'discount', width:'10%'},
			{name:'product.area',index:'product.area', width:'10%',hidden:true},
			{name:'product.silces',index:'product.silces', width:'10%',hidden:true}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		caption: "销售采购商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
//		beforeSelectRow: function(rowid, e){
//		    //设置编辑数据
//		    selectMaterialEditId = rowid;
//			var rowData = $("#grid-table").jqGrid("getRowData",rowid);
//			setEditMatrailData(rowData);
//			$("#addMaterialType").val("edit");
//		    return (true);
//		},
		gridComplete:function(){
			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
			var sum=0;
			for(var i=0;i<rowDatas.length;i++){
				sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
			}
			$(grid_selector).footerData("set",{amount:"合计",discount:sum});
		},
		pgbuttons:false,	//是否显示分页按钮
		pginput:false	//是否显示分页输入框
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
	
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
				view: false,
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
				afterSubmit : onEditafterSubmit,
				beforeSubmit:function(postdata, formid) {
					var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
					if(postdata.productId==null||postdata.productId==""){
						return [false,"请选择产品"]
					}else{
						if((postdata.pieces==null||postdata.pieces=="")&&(postdata.slices==null||postdata.slices=="")){
							return [false,"片数和件数至少填写一个"]
						}else{
							$.ajax({
								async:false,
								url:basePath+"erp/material/getMaterialById",
								type:"post",
								datatype:"json",
								data:{"productId":postdata.productId},
								success:function(data){
									var a=0,b=0;
									if(postdata.pieces!=null&&postdata.pieces!=""){
										a=postdata.pieces;
									}
									if(postdata.slices!=null&&postdata.slices!=""){
										b=postdata.slices;
									}
									postdata.area=((Number(data.silces*a)+Number(b))*data.area).toFixed(6);
									postdata.amount=(postdata.area*data.price).toFixed(2);
									postdata.name=data.name;
									postdata.spec=data.spec;
									postdata.color=data.color;
									postdata.price=data.price;
								}
						    });
							jQuery(grid_selector).jqGrid().setRowData(myid,postdata);
							return[true];
						}
					}
				}
			},
			{
				
				
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
		buttonicon:"icon-plus-sign green", 
		onClickButton:function(){
			jQuery("#grid-table2").jqGrid().clearGridData();
			var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");
			for(var i=0;i<selectedIds.length;i++){
				var rowData=jQuery("#grid-table").jqGrid("getRowData",selectedIds[i]);
				jQuery("#grid-table2").jqGrid().addRowData(rowData.orderMaterialId,rowData,"last");
			}
		}, 
		position: "first", 
		title:"添加到采购退货信息列表", 
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

function selectMaterialOnclick(){
	$.fDialog({
		title : '<b>商品信息列表</b>',
		content : basePath+'erp/selectMaterial?productId='+$("#addMaterialId").val(),
		module : 'iframe',
		w : '80%',
		h : '522',
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
	$("#addMaterialId").val(data['product.productId']);
	$("#addMaterialName").val(data['product.name']);
	$("#addMaterialPieces").val(data.pieces);
	$("#addMaterialSlices").val(data.slices);
	$("#addMaterialDiscount").val(data.discount);
}

//##################################################
//弹出选择客户页面
var subIframeSelectDataContact = null;
function selectContactOnclick(){
	$.fDialog({
		title : '<b>供应商信息列表</b>',
		content : basePath+'erp/selectSupplier',
		module : 'iframe',
		w : '80%',
		h : '522',
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


function processSubmit(obj){
	var griddata=jQuery("#grid-table2").jqGrid("getRowData");
	for(var i=0;i<griddata.length;i++){
		for(var name in griddata[i]){
			if(name=="product.productId"){
				griddata[i]["product"]={};
				griddata[i]["product"]["productId"]=griddata[i][name];
				break;
			}
		}
	}
	if(griddata.length==0){
		fDialogAlert("请添加采购商品");
		return;
	}
	griddata = JSON.stringify(griddata);
	var newOrderId=$("#newOrderId").val();
	var orderId=$("#orderId").val();
	var contactId=$("#contactId").val();
	var contactName=$("#contactName").val();
	var contactNameHidden=$("#contactNameHidden").val();
	var address=$("#address").val();
	var phone=$("#phone").val();
	var mobile=$("#mobile").val();
	var needPay=$("#needPay").val();
	var productType=$("#productType").val();
	var note=$("#note").val();
	var amount=$("#grid-table").footerData("get").discount;
	var form_taskId=$("#form_taskId").val();
	var form_id=$("#form_id").val();
	var form_comment=$("#form_comment").val();
	var priority=$("#priority").val();
	var orderPreType=$("#orderPreType").val();
	
	if(contactName==null||contactName==""){
		fDialogAlert("供应商不能为空");
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
	var jsondata = {
			"priority":priority,
			"taskId":form_taskId,
			"id":form_id,//使用新生成的采购订单号
			"comment":form_comment,
			"outcome":obj.value,
			"orderPreType":orderPreType,
			"phone":phone,"note":note,"address":address,
			"mobile":mobile,"needPay":needPay,
			"productType":productType,"amount":amount,
			"griddata":griddata,"name":contactName,
			"contact.contactId":contactId,
			"contact.name":contactNameHidden,
			"orderId":orderId};
    $.ajax({
		url:basePath+"erp/sellBuyOrderAuditCGSubmit_tj",
		type:"post",
		datatype:"json",
//		contentType: "application/json;charset=utf-8",
		data: jsondata,
//		data:dataobject,//,"contactId="+contactId+"&phone="+phone+"&note="+note+"&address="+address+"&mobile="+mobile+"&griddata="+griddata,
		success:function(data){
			if(data.message=="success"){
				window.location=basePath+"erp/processTask";
			}else{
				fDialogAlert("保存失败");
			}
		}
    });
}







//##########################################################################################
//var subIframeSelectDataInventory=null;
var selectMaterialEditId = "";
var autoNum =-1;
function getautoId(){
	return autoNum--;
}


jQuery(function($) {
	var grid_selector2 = "#grid-table2";
	var pager_selector2 = "#grid-pager2";
	
	jQuery(grid_selector2).jqGrid({
//		url:basePath+"erp/sellOrder/getOrderMaterialList",//获取数据的地址
//		mtype:"POST",
		datatype: "local",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称', '规格','产品编号','色号','单价','每箱片数','件数', '总片数','面积','总金额','优惠金额','单片面积'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'product.productId', width:'5%',hidden:true},
			{name:'product.name',index:'product.name', width:'10%'},
			{name:'product.spec',index:'product.spec', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.color',index:'product.color', width:'10%'},
			{name:'product.price',index:'product.price', width:'10%'},
			{name:'product.silces',index:'product.silces', width:'10%',hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%'},
			{name:'area',index:'area', width:'10%'},
			{name:'amount',index:'amount', width:'10%'},
			{name:'discount',index:'discount', width:'10%'},
			{name:'product.area',index:'product.area', width:'10%',hidden:true}
		],//列属性
		viewrecords : true,//显示显示总记录数
		pager : pager_selector2,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		caption: "采购商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		footerrow:true,
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector2).jqGrid('resetSelection');
		    
		    //设置编辑数据
		    selectMaterialEditId = rowid;
			var rowData = $("#grid-table2").jqGrid("getRowData",rowid);
			setEditMatrailData(rowData);
//			subIframeSelectDataInventory=rowData;
			$("#editType").val("edit");
			$("#orderMaterialId").val(rowData["orderMaterialId"]);
		    return (true);
		},
		gridComplete:function(){
			//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table2").hide();
			
			var rowDatas=jQuery(grid_selector2).jqGrid().getRowData();
			var sum=0;
			for(var i=0;i<rowDatas.length;i++){
				sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
			}
			$(grid_selector2).footerData("set",{amount:"合计",discount:sum});
		},
		pgbuttons:false,	//是否显示分页按钮
		pginput:false,	//是否显示分页输入框
	});
	$(grid_selector2).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector2).jqGrid('setGroupHeaders', {
	    useColSpanStyle: true, 
	    groupHeaders:[
	      {startColumnName: 'product.productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
	    ]
	});
	
	jQuery(grid_selector2).jqGrid('navGrid',pager_selector2,
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
				view: false,
				viewicon : 'icon-zoom-in grey',
			});
	
	jQuery("#grid-table2").jqGrid('navGrid',"#grid-pager2").jqGrid('navButtonAdd',"#grid-pager2",{
		caption:"", 
		buttonicon:"icon-trash red", 
		onClickButton:function(){
			var myid=$("#grid-table2").jqGrid("getGridParam",'selarrrow');
			$("#grid-table2").jqGrid("delRowData",myid);
			$("#orderMaterialId").val("");
			$("#productId").val("");
			$("#productName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#addMaterialDiscount").val("");
			//subIframeSelectDataInventory = null;
			selectMaterialEditId = null;
			$("#editType").val("");
		}, 
		position: "first", 
		title:"删除商品", 
		cursor: "pointer",
		id:"delButton2"
	});
	
	jQuery("#grid-table2").jqGrid('navGrid',"#grid-pager2").jqGrid('navButtonAdd',"#grid-pager2",{
		caption:"", 
		buttonicon:"icon-pencil blue", 
		onClickButton:function(){
			var myid=$("#grid-table2").jqGrid("getGridParam",'selarrrow');
			if(myid!=""){
				$("#addEditDiv").show();
				selectMaterialEditId = myid;
				var rowData = $("#grid-table2").jqGrid("getRowData",myid);
				setEditMatrailData(rowData);
				$("#editType").val("edit");
				
				//
				$("#andEditText").html("修改退货商品信息");
			}else{
				fDialogAlert("请选择要修改的数据行");
			}
			
		}, 
		position: "first", 
		title:"修改商品", 
		cursor: "pointer",
		id:"editButton2"
	});
	
	jQuery("#grid-table2").jqGrid('navGrid',"#grid-pager2").jqGrid('navButtonAdd',"#grid-pager2",{
		caption:"", 
		buttonicon:"icon-plus-sign green", 
		onClickButton:function(){
			$("#addEditDiv").show();
			$("#addMaterialPieces").focus();
			
			jQuery(grid_selector2).resetSelection();
			
			$("#orderMaterialId").val("");
			$("#productId").val("");
			$("#productName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#addMaterialDiscount").val("");
			
			//subIframeSelectDataInventory = null;
			$("#andEditText").html("选择退货商品信息");
			$("#editType").val("add");
		}, 
		position: "first", 
		title:"添加商品", 
		cursor: "pointer",
		id:"addButton2"
	});
	
});

function addMaterialSubmit(){
	var postdata={};
	//(1)获取数据
	var data = $("#grid-table2").jqGrid("getRowData",$("#orderMaterialId").val());
	
	var addMaterialPieces = $("#addMaterialPieces").val();
	var addMaterialSlices = $("#addMaterialSlices").val();
	var addMaterialDiscount = $("#addMaterialDiscount").val();
	
	if(data['product.productId']==null||data['product.productId']==""){
		fDialogAlert("请选择一个商品");
		return;
	}
	
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
	postdata.area=((Number(data['product.silces']*addMaterialPieces)+Number(addMaterialSlices))*data['product.area']).toFixed(6);
	postdata.amount=(postdata.area*data['product.price']).toFixed(2);
	postdata.orderMaterialId=data['orderMaterialId'];
	postdata.product={};
	postdata.product.name=data['product.name'];
	postdata.product.num=data['product.num'];
	postdata.product.spec=data['product.spec'];
	postdata.product.color=data['product.color'];
	postdata.product.productId=data['product.productId'];
	postdata.product.silces=data['product.silces'];
	postdata.product.price=data['product.price'];
	postdata.pieces=addMaterialPieces;
	postdata.slices=addMaterialSlices;
	postdata.discount=(Number(addMaterialDiscount)).toFixed(2);
	postdata.count=data.count;
	var editType = $("#editType").val()
	if(editType=="edit"){
		jQuery("#grid-table2").jqGrid().setRowData(selectMaterialEditId,postdata);
		
		var rowDatas=jQuery("#grid-table2").jqGrid().getRowData();
		var sum=0;
		for(var i=0;i<rowDatas.length;i++){
			sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
		}
		$("#grid-table2").footerData("set",{amount:"合计",discount:sum});
	}else{
		jQuery("#grid-table2").jqGrid().addRowData(getautoId(),postdata,"last");
	}
	
	
	//(3)清除数据并关闭dov
	$("#editType").val("");
	$("#orderMaterialId").val("");
	$("#productId").val("");
	$("#productName").val("");
	$("#addMaterialPieces").val("");
	$("#addMaterialSlices").val("");
	$("#addMaterialDiscount").val("");
	//subIframeSelectDataInventory=null;
}

function addMaterialCancel(){
	$("#editType").val("");
	$("#orderMaterialId").val("");
	$("#productId").val("");
	$("#productName").val("");
	$("#addMaterialPieces").val("");
	$("#addMaterialSlices").val("");
	$("#addMaterialDiscount").val("");
}

function subIframeSetSelectData(data){
	subIframeSelectData = data;
}

function setEditMatrailData(data){
	$("#productName").val(data["product.name"]);
	$("#productId").val(data["product.productId"]);
	$("#orderMaterialId").val(data["orderMaterialId"]);
	$("#addMaterialPieces").val(data.pieces);
	$("#addMaterialSlices").val(data.slices);
	$("#addMaterialDiscount").val(data.discount);
}
//############################################################

//function subIframeSetSelectDataInventory(data){
//	subIframeSelectDataInventory = data;
//}

//############################################################
//提交操作
function billSubmitCheck(){
	var needData = $("#grid-table").jqGrid("getRowData");
	var selectData = $("#grid-table2").jqGrid("getRowData");
	
	//合并相同的商品
	var tmpData=new Array();
	if(needData!=null&&needData.length>0){
		for(var i=0;i<needData.length;i++){
			var isFind=false;
			for(var j=0;j<tmpData.length;j++){
				if(needData[i]['product.productId']==tmpData[j]['product.productId']){
					tmpData[j]['pieces']=Number(tmpData[j]['pieces'])+Number(needData[i]['pieces']);
					tmpData[j]['slices']=Number(tmpData[j]['slices'])+Number(needData[i]['slices']);
					isFind=true;
					break;
				}
			}
			if(isFind==false){
				tmpData[tmpData.length]=needData[i];
			}
		}
	}
	needData=tmpData;
	tmpData=new Array();
	if(selectData!=null&&selectData.length>0){
		for(var i=0;i<selectData.length;i++){
			var isFind=false;
			for(var j=0;j<tmpData.length;j++){
				if(selectData[i]['product.productId']==tmpData[j]['product.productId']){
					tmpData[j]['pieces']=Number(tmpData[j]['pieces'])+Number(selectData[i]['pieces']);
					tmpData[j]['slices']=Number(tmpData[j]['slices'])+Number(selectData[i]['slices']);
					isFind=true;
					break;
				}
			}
			if(isFind==false){
				tmpData[tmpData.length]=selectData[i];
			}
		}
	}
	selectData=tmpData;
	
	//检查所选出库商品数量是否和所需出库商品数量一致
	if(selectData.length==0){
		fDialogAlert("请选择要退货的商品信息");
		return false;
	}else{
		for(var i=0;i<selectData.length;i++){
			for(var j=0;j<needData.length;j++){
				if(selectData[i]['product.productId']==needData[j]['product.productId']){
					var selectTotal=selectData[i]['pieces']*needData[j]['product.silces']+Number(selectData[i]['slices']);
					var needTotal=needData[j]['pieces']*needData[j]['product.silces']+Number(needData[j]['slices']);
					if(selectTotal>needTotal){
						return false
					}
				}
			}
		}
		return true;
	}
}

$(function(){
	var newOrderId = $("#newOrderId").val();
	if(newOrderId){
		$.ajax({
			url:basePath+"erp/sellOrder/getOrderMaterialList",
			type:"post",
			datatype:"json",
			data:{"orderId":newOrderId},
			success:function(data){
				for(var i=0;i<data.rows.length;i++){
					jQuery("#grid-table2").jqGrid().addRowData(data.rows[i].orderMaterialId,data.rows[i],"last");
				}
			}
	    });
	}
});