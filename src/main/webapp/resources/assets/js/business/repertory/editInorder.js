var requestOrderId="";

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	var autoNum =0;
	function getautoId(){
		return autoNum++;
	}
	jQuery(grid_selector).jqGrid({
		//url:basePath+"erp/returnOrder/getOrderMaterialList",//获取数据的地址
		//mtype:"POST",
		datatype: "local",//从服务器端返回的数据类型
		height: 200,//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品编号', '产品名称','规格','色号','单价','件数', '片数', '面积','总金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%', editable:true, edittype:'select',editoptions: {dataUrl: basePath+'erp/sellOrder/getSelect'},searchoptions:{sopt:['eq','ne']}},
			{name:'product.num',index:'num', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.name',index:'name', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.spec',index:'spec', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.color',index:'color', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.price',index:'price', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'pieces',index:'pieces', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}},
			{name:'slices',index:'slices', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}},
			{name:'area',index:'area', width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'amount',index:'amount', width:'10%', hidden:true,editable:false,searchoptions:{sopt:['eq','ne']}}
		],//列属性
		viewrecords : true,//显示显示总记录数
		//rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
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
		caption: "入库产品信息",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
		gridComplete:function(){
			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
			var sum=0;
			for(var i=0;i<rowDatas.length;i++){
				sum=(Number(sum)+Number(rowDatas[i].amount)).toFixed(2);
			}
			$(grid_selector).footerData("set",{area:"合计",amount:sum});
		},
		pgbuttons:false,	//是否显示分页按钮
		pginput:false,	//是否显示分页输入框
	});
	
	//jQuery(grid_selector).jqGrid('filterToolbar');//显示搜索栏
	
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
			afterSubmit : onEditafterSubmit,
			beforeSubmit:function(postdata, formid) {
				var sta=1;
				var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
				if(postdata["product.productId"]==null||postdata["product.productId"]==""){
					return [false,"请选择产品"]
				}else{
					if((postdata.pieces==null||postdata.pieces=="")&&(postdata.slices==null||postdata.slices=="")){
						return [false,"片数和件数至少填写一个"]
					}else{
						$.ajax({
							async:false,
							url:basePath+"erp/returnOrder/getOrderMaterialInfo",
							type:"post",
							datatype:"json",
							data:{"orderMaterialId":myid},
							success:function(data){
								if(data.orderMaterial.pieces<postdata.pieces||data.orderMaterial.slices<postdata.slices||postdata["product.productId"]!=data.orderMaterial.product.productId){
									sta=2;
								}else{
									$.ajax({
										async:false,
										url:basePath+"erp/material/getMaterialById",
										type:"post",
										datatype:"json",
										data:{"productId":postdata["product.productId"]},
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
										}
								    });
									jQuery(grid_selector).jqGrid().setRowData(myid,postdata);
								}
							}
					    });
					}
				}
				if(sta==1){
					return [true];
				}else{
					return [false,"片数和件数不能超出原订单片数和件数,且不能修改产品"];
				}
			}
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

function billSubmit(){
//	var ss=1;
//	$.ajax({
//		async:false,
//		url:basePath+"erp/returnOrder/haveSameReturnOrder",
//		type:"post",
//		datatype:"json",
//		data:{"returnOrderId":requestOrderId},
//		success:function(data){
//			if(data.message==true){
//				ss=2;
//			}
//		}
//    });
//	if(ss==2){
//		alert("不能对同一个订单退货两次");
//		return;
//	}
//	var griddata=jQuery("#grid-table").jqGrid("getRowData");
//	for(var i=0;i<griddata.length;i++){
//		for(var name in griddata[i]){
//			if(name=="product.productId"){
//				griddata[i]["product"]={};
//				griddata[i]["product"]["productId"]=griddata[i][name];
//				break;
//			}
//		}
//	}
//	griddata = JSON.stringify(griddata);
//	var contactId=$("#contactId").val();
//	var phone=$("#phone").val();
//	var note=$("#note").val();
//	var address=$("#address").val();
//	var mobile=$("#mobile").val();
//	var needPay=$("#needPay").val();
//	var productType=$("#productType").val();
//	var amount=$("#grid-table").footerData("get").amount;
//	
//	if(contactId==null||contactId==""){
//		alert("客户不能为空");
//		return;
//	}
//	
//	if(phone!=null&&phone!=""){
//		var reg=RegExp("^([0-9]{3,4}-)?[0-9]{7,8}$");
//		if(!reg.test(phone)){
//			alert("请填写正确的电话号码");
//			return;
//		}
//	}
//	
//	if(mobile!=null&&mobile!=""){
//		var reg=RegExp("^[0-9]{11}$");
//		if(!reg.test(mobile)){
//			alert("请填写正确的手机号码");
//			return;
//		}
//	}
	
//	var dataobject = new Object();
//	dataobject.contact={};
//	dataobject.contact.contactId = contactId;
//	dataobject.phone = phone;
//	dataobject.note = note;
//	dataobject.address = address;
//	dataobject.mobile = mobile;
//	dataobject.needPay = needPay;
//	dataobject.productType = productType;
//	dataobject.returnOrderId = requestOrderId;
//	dataobject.amount = amount;
//	dataobject.griddata = griddata;
	
//	var jsondata = {"contact.contactId":contactId,"phone":phone,"note":note,"address":address,"mobile":mobile,"needPay":needPay,"productType":productType,"returnOrderId":requestOrderId,"amount":amount,"griddata":griddata};
//	
//    $.ajax({
//		url:basePath+"erp/returnOrder/saveOrder",
//		type:"post",
//		datatype:"json",
//		data:jsondata,
//		success:function(data){
//			if(data.message=="success"){
//				window.location=basePath+"erp/toReturnOrder";
//			}else{
//				alert("保存失败");
//			}
//		}
//    });
	
	if($("#orderId").val()==null||$("#orderId").val()==""){
		fDialogAlert("请选择一个订单再保存");
		return;
	}
  $.ajax({
	url:basePath+"erp/inorder/editInorder",
	type:"post",
	datatype:"json",
	data:{"type":$("#type").val(),"orderId":$("#orderId").val(),"note":$("#note").val()},
	success:function(data){
		if(data.message=="success"){
			window.location=basePath+"erp/toInorder";
		}else{
			fDialogAlert("编辑失败");
		}
	}
});
	
}

function billReset(){
	
}

function billReturn(){
	window.location=basePath+"erp/toInorder";
}

//$('#orderId').combogrid({
//	panelWidth:500,
//	idField:'orderId',
//	textField:'orderId',
//	url:basePath+"erp/inorder/getOrderInfoList",
//	pagination:true,//是否分页
//	rownumbers:true,//序号
//	collapsible:false,//是否可折叠的
//	queryParams:{"type":$("#type").val()},
//	//fit: true,//自动大小
//	//queryParams:"test",//当请求远程数据时，发送的额外参数
//	pageSize: 10,//每页显示的记录条数，默认为10
//    pageList: [10,20,30],//可以设置每页记录条数的列表
//	columns:[[
//	    {field:'orderId',title:'订单号',width:50},
//		{field:'contact.name',title:'客户',width:50},
//		{field:'address',title:'地址',address:200},
//		{field:'phone',title:'电话',width:100},
//		{field:'mobile',title:'手机',width:100}
//	]],
//	keyHandler:{
//        up: function(){},
//        down: function(){},
//        enter: function(){
//        },
//        query:function(q){
//        	$('#orderId').combogrid("grid").datagrid("reload", { 'keyword': q });
//        	$('#orderId').combogrid("setValue", q);
//        	return false;
//        }
//   },
//   onSelect:function(){
//       var grid=$("#orderId").combogrid("grid");//获取表格对象
//       var row = grid.datagrid('getSelected');//获取行数据
//       requestOrderId=row.orderId;
//       $("#phone").val(row.phone);
//       $("#address").val(row.address);
//       $("#mobile").val(row.mobile);
//       $("#note").val(row.note);
//       $("#needPay").val(row.needPay);
//       $("#productType").val(row.productType);
//       $("#contactId").val(row.contact.contactId);
//       jQuery("#grid-table").jqGrid().clearGridData();
//       $.ajax({
//			url:basePath+"erp/returnOrder/getOrderMaterialList",
//			type:"post",
//			datatype:"json",
//			data:{"orderId":row.orderId},
//			success:function(data){
//				for(var i=0;i<data.rows.length;i++){
//					jQuery("#grid-table").jqGrid().addRowData(data.rows[i].orderMaterialId,data.rows[i],"last");
//				}
//			}
//	    });
//  }
//   });

//jQuery(function($) {
//    $( "#orderId").autocomplete({
//    	source: function (request, response) {
//	    	$.ajax({
//	    		url:basePath+"erp/inorder/getOrderInfo",
//	    		type:"post",
//	    		datatype:"json",
//	    		data: {keyword:  $( "#orderId").val(),"type":$("#type").val()},
//	    		success:function(data){
//	    			response(data);
//	    		},error:function(response){
//	    		}
//	        });
//    	},
//    	select: function (event, ui) {
//    		$( "#contactId" ).val( ui.item.contactId );
//            $( "#phone" ).val( ui.item.phone );
//            $( "#mobile" ).val( ui.item.mobile );
//            $( "#address" ).val( ui.item.address );
//            switch (ui.item.productType) {
//			case "0":
//				$( "#productType" ).val("库存");
//				break;
//			case "1":
//				$( "#productType" ).val("外购需检");
//				break;
//			case "2":
//				$( "#productType" ).val("外购免检");
//				break;
//			}
//            switch (ui.item.needPay) {
//			case "0":
//				$( "#needPay" ).val("直接付款");
//				break;
//			case "1":
//				$( "#needPay" ).val("赠送");
//				break;
//			case "2":
//				$( "#needPay" ).val("赊账");
//				break;
//			case "3":
//				$( "#needPay" ).val("从余款中扣除");
//				break;
//			case "4":
//				$( "#needPay" ).val("其他");
//				break;
//			}
//            $( "#orderIdHidden" ).val( ui.item.orderId );
//            $.ajax({
//				async:false,
//				url:basePath+"erp/inorder/getOrderMaterialList",
//				type:"post",
//				datatype:"json",
//				data:{"orderId":$("#orderIdHidden").val()},
//				success:function(data){
//					jQuery("#grid-table").jqGrid().clearGridData();
//					for(var i=0;i<data.rows.length;i++){
//						jQuery("#grid-table").jqGrid().addRowData(data.rows[i].orderMaterialId,data.rows[i],"last");
//					}
//				}
//		    });
//		}
//    });
//
//});

$(function(){
	$.ajax({
		async:false,
		url:basePath+"erp/inorder/getOrderMaterialList",
		type:"post",
		datatype:"json",
		data:{"orderId":$("#orderId").val()},
		success:function(data){
			jQuery("#grid-table").jqGrid().clearGridData();
			for(var i=0;i<data.rows.length;i++){
				jQuery("#grid-table").jqGrid().addRowData(data.rows[i].orderMaterialId,data.rows[i],"last");
			}
		}
    });
	
//	$.ajax({
//		async:false,
//		url:basePath+"erp/material/getMaterialById",
//		type:"post",
//		datatype:"json",
//		data:{"productId":postdata["product.productId"]},
//		success:function(data){
//			var a=0,b=0;
//			if(postdata.pieces!=null&&postdata.pieces!=""){
//				a=postdata.pieces;
//			}
//			if(postdata.slices!=null&&postdata.slices!=""){
//				b=postdata.slices;
//			}
//			postdata.area=((Number(data.silces*a)+Number(b))*data.area).toFixed(6);
//			postdata.amount=(postdata.area*data.price).toFixed(2);
//		}
//    });
})

//##################################################
//弹出选择销售订单页面
var subIframeSelectDataSellOrder = null;
function selectSellOrderOnclick(){
	$.fDialog({
		title : '销售订单信息列表',
		content : basePath+'erp/selectSellOrder?orderId='+$("#orderId").val(),
		module : 'iframe',
		w : '80%',
		h : '52%',
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataSellOrder){
				$("#phone").val(subIframeSelectDataSellOrder.phone);
				$("#needPay").val(subIframeSelectDataSellOrder.needPay);
				$("#productType").val(subIframeSelectDataSellOrder.productType);
				$("#address").val(subIframeSelectDataSellOrder.address);
				$("#mobile").val(subIframeSelectDataSellOrder.mobile);
				$("#orderId").val(subIframeSelectDataSellOrder.orderId);
				$("#contactId").val(subIframeSelectDataSellOrder["contact.contactId"]);
				$("#name").val(subIframeSelectDataSellOrder["contact.name"]);
				switch (subIframeSelectDataSellOrder.productType) {
				case "0":
					$( "#productType" ).val("库存");
					break;
				case "1":
					$( "#productType" ).val("外购需检");
					break;
				case "2":
					$( "#productType" ).val("外购免检");
					break;
				}
	            switch (subIframeSelectDataSellOrder.needPay) {
				case "0":
					$( "#needPay" ).val("直接付款");
					break;
				case "1":
					$( "#needPay" ).val("赠送");
					break;
				case "2":
					$( "#needPay" ).val("赊账");
					break;
				case "3":
					$( "#needPay" ).val("从余款中扣除");
					break;
				case "4":
					$( "#needPay" ).val("其他");
					break;
				}
	            $.ajax({
					async:false,
					url:basePath+"erp/inorder/getOrderMaterialList",
					type:"post",
					datatype:"json",
					data:{"orderId":$("#orderId").val()},
					success:function(data){
						jQuery("#grid-table").jqGrid().clearGridData();
						for(var i=0;i<data.rows.length;i++){
							jQuery("#grid-table").jqGrid().addRowData(data.rows[i].orderMaterialId,data.rows[i],"last");
						}
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

function subIframeSetSelectDataSellOrder(data){
	subIframeSelectDataSellOrder = data;
}

function writeObj(obj){ 
    var description = ""; 
    for(var i in obj){   
        var property=obj[i];   
        description+=i+" = "+property+"\n";  
    }   
    alert(description); 
} 