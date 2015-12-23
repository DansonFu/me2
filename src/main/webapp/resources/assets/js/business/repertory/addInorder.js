var autoNum =0;
function getautoId(){
	return autoNum++;
}

var requestOrderId="";
var selectMaterialEditId = "";
var subIframeSelectData = null;

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	jQuery(grid_selector).jqGrid({
		datatype: "local",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称','产品编号','规格','色号','单价','件数', '总片数','单位面积' ,'总面积','总金额','仓库id','仓库名称','备注'],//列显示名称
		colModel:[
			{name:'inoutorderId',index:'inoutorderId', width:'5%', editable:false,hidden:true},
			{name:'productId',index:'productId', width:'5%',hidden:true},
			{name:'name',index:'name', width:'10%', editable:false},
			{name:'num',index:'num', width:'10%', editable:false},
			{name:'spec',index:'spec', width:'10%', editable:false},
			{name:'color',index:'color', width:'10%', editable:false,edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'price',index:'price', width:'10%', editable:false,hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%'},
			{name:'oneArea',index:'oneArea', width:'10%', hidden:true,editable:false},
			{name:'area',index:'area', width:'10%', editable:false},
			{name:'amount',index:'amount', width:'10%', hidden:true,editable:false},
			{name:'storageId',index:'storageId', width:'10%', hidden:true,editable:false},
			{name:'storageName',index:'storageName', width:'10%', editable:false},
			{name:'mark',index:'mark', width:'10%', editable:false}
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
		caption: "本地入库商品信息",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
		beforeSelectRow: function(rowid, e){
		    //设置编辑数据
		    selectMaterialEditId = rowid;
			var rowData = $("#grid-table").jqGrid("getRowData",rowid);
			subIframeSelectData = rowData;
			setEditMatrailData(rowData);
			$("#addMaterialType").val("edit");
		    return (true);
		},
		gridComplete:function(){
//			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
//			var sum=0;
//			for(var i=0;i<rowDatas.length;i++){
//				sum=(Number(sum)+Number(rowDatas[i].amount)).toFixed(2);
//			}
//			$(grid_selector).footerData("set",{area:"合计",amount:sum});
		},
		pgbuttons:false,	//是否显示分页按钮
		pginput:false,	//是否显示分页输入框
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
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
			jQuery("#grid-table").resetSelection();
			$("#grid-table").jqGrid("delRowData",selectMaterialEditId);
			$("#addMaterialId").val("");
			$("#addMaterialName").val("");
			$("#storageId").val("");
			$("#storageName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#mark").val("");
			selectMaterialEditId = null;
			subIframeSelectData = null;
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
			var myid=$("#grid-table").jqGrid("getGridParam",'selarrrow');
			if(myid!=""){
				$("#addEditDiv").show();
				selectMaterialEditId = myid;
				var rowData = $("#grid-table").jqGrid("getRowData",myid);
				setEditMatrailData(rowData);
				$("#addMaterialType").val("edit");
				
				//
				$("#andEditText").html("选择入库商品信息");
			}else{
				fDialogAlert("请选择要修改的数据行");
			}
			
		}, 
		position: "first", 
		title:"修改入库商品", 
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
			$("#storageId").val("");
			$("#storageName").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			$("#mark").val("");
			selectMaterialEditId = null;
			subIframeSelectData = null;
			//subIframeSelectDataInventory = null;
			$("#andEditText").html("选择入库商品信息");
			$("#addMaterialType").val("edit");
		}, 
		position: "first", 
		title:"添加入库商品", 
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

function billSubmit(isSubmitStorage){
	
	if($("#orderId").val()==null||$("#orderId").val()==""){
		fDialogAlert("请选择一个订单再保存");
		return;
	}
	$.ajax({
		url:basePath+"erp/inorder/saveInorder",
		type:"post",
		datatype:"json",
		data:{"isSubmitStorage":isSubmitStorage,"type":$("#type").val(),"orderId":$("#orderId").val(),"note":$("#note").val()},
		success:function(data){
			if(data.message=="success"){
				window.location=basePath+"erp/toInorder";
			}else{
				fDialogAlert("保存失败");
			}
		}
	});
	
}

function billReset(){
	document.getElementById("addInorderForm").reset();
	$("input[name=contactId]").val("");
	$("input[name=orderIdHidden]").val("");
	jQuery("#grid-table").jqGrid().clearGridData();
}

function billReturn(){
	window.location=basePath+"erp/toInorder";
}

function addMaterialSubmit(){
	var data=null;
	var rowid=$("#grid-table").jqGrid("getGridParam",'selarrrow');
	if(rowid!=null&&rowid!=""){
		var rowData = $("#grid-table").jqGrid("getRowData",rowid);
		data=rowData;
	}
	
	var postdata={};
	
	console.log("data 1 : " + JSON.stringify(data));
	//(1)获取数据
	if(subIframeSelectData!=null){
		data = subIframeSelectData;
	}
	console.log("data 2 : " + JSON.stringify(data));
	var addMaterialPieces = $("#addMaterialPieces").val();
	var addMaterialSlices = $("#addMaterialSlices").val();
	var storageId=$("#storageId").val();
	var storageName=$("#storageName").val();
	var mark=$("#mark").val();
	
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
			fDialogAlert("商品总片数：请输入一个非负整数");
			return;
		}
	}else{
		addMaterialSlices=0;
	}
	
	if(addMaterialPieces==0&&addMaterialSlices==0){
		fDialogAlert("商品总片数不能为0");
		return;
	}
	//(2)设置数据
//	postdata.amount=(postdata.area*data.price).toFixed(2);
	postdata.name=data.name;
	postdata.num=data.num;
	postdata.spec=data.spec;
	postdata.color=data.color;
	postdata.productId=data.productId;
	postdata.pieces=addMaterialPieces;
	postdata.slices=addMaterialSlices;
	postdata.storageId=storageId;
	postdata.storageName=storageName;
	postdata.mark=mark;
	var addMaterialType = $("#addMaterialType").val()
	if(addMaterialType=="edit"){
		postdata.oneArea=data.oneArea;
		postdata.area=(Number(addMaterialSlices)*data.oneArea).toFixed(4);
		console.log("edit:"+JSON.stringify(postdata));
		jQuery("#grid-table").jqGrid().setRowData(selectMaterialEditId,postdata);
		
//		var rowDatas=jQuery("#grid-table").jqGrid().getRowData();
//		var sum=0;
//		for(var i=0;i<rowDatas.length;i++){
//			sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
//		}
//		$("#grid-table").footerData("set",{amount:"合计",discount:sum});
	}else{
		postdata.oneArea=data.area;
		postdata.area=((Number(data.silces*addMaterialPieces)+Number(addMaterialSlices))*postdata.oneArea).toFixed(4);
		console.log("add:"+JSON.stringify(postdata));
		jQuery("#grid-table").jqGrid().addRowData(getautoId(),postdata,"last");
	}
	
	
	//(3)清除数据并关闭dov
	$("#addMaterialType").val("");
	$("#addMaterialId").val("");
	$("#addMaterialName").val("");
	$("#addMaterialPieces").val("");
	$("#addMaterialSlices").val("");
	$("#storageId").val("");
	$("#storageName").val("");
	$("#mark").val("");
	subIframeSelectData=null;
}

function setEditMatrailData(data){
	$("#addMaterialId").val(data.productId);
	$("#addMaterialName").val(data.name);
	$("#addMaterialPieces").val(data.pieces);
	$("#addMaterialSlices").val(data.slices);
	$("#storageId").val(data.storageId);
	$("#storageName").val(data.storageName);
	$("#mark").val(data.mark);
}


//##################################################################
//弹出商品选择窗口
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

function subIframeSetSelectData(data){
	subIframeSelectData = data;
}


//##################################################################
//弹出仓库选择窗口
var selectStorageData = null;

function subIframeSetSelectDataStorage(storageData){
	selectStorageData =storageData;
}

function selectStorageOnclick(){
	$.fDialog({
		title : '<b>仓库信息列表</b>',
		content : basePath+'erp/selectStorage',
		module : 'iframe',
		w : '80%',
		h : '522',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(selectStorageData!=null){
				$("#storageName").val(selectStorageData.name);
				$("#storageId").val(selectStorageData.storageId);
				selectStorageData = null;
				return true;
			}else{
				fDialogAlert("请为商品设置仓库信息");
				return false;
			}
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}