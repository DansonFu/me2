var requestOrderId="";

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	var autoNum =0;
	function getautoId(){
		return autoNum++;
	}
	jQuery(grid_selector).jqGrid({
		datatype: "local",//从服务器端返回的数据类型
		height: 200,//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品','产品名称', '产品编号', '规格','色号','单价','件数', '片数', '面积','总金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%',hidden:true},
			{name:'product.name',index:'name', width:'10%', editable:false},
			{name:'product.num',index:'num', width:'10%', editable:false},
			{name:'product.spec',index:'spec', width:'10%', editable:false},
			{name:'product.color',index:'color', width:'10%', editable:false,edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.price',index:'price', width:'10%', editable:false},
			{name:'pieces',index:'pieces', width:'10%'},
			{name:'slices',index:'slices', width:'10%'},
			{name:'area',index:'area', width:'10%', editable:false},
			{name:'amount',index:'amount', width:'10%', hidden:true,editable:false}
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
		caption: "出库产品信息",//表格名称
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
	url:basePath+"erp/outorder/saveOutorder",
	type:"post",
	datatype:"json",
	data:{"isSubmitStorage":isSubmitStorage,"type":$("#type").val(),"orderId":$("#orderId").val(),"note":$("#note").val()},
	success:function(data){
		if(data.message=="success"){
			window.location=basePath+"erp/toOutorder";
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
	window.location=basePath+"erp/toOutorder";
}

//##################################################
//弹出选择销售订单页面
var subIframeSelectDataSellOrder = null;
function selectSellOrderOnclick(){
	$.fDialog({
		title : '待出库订单列表',
		content : basePath+'erp/selectSellOrder?type='+$("#type").val(),
		module : 'iframe',
		w : '80%',
		h : 526,
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataSellOrder){
				$("#phone").val(subIframeSelectDataSellOrder.phone);
				$("#needPay").val(subIframeSelectDataSellOrder.needPay);
				$("#productType").val(subIframeSelectDataSellOrder.productType);
				$("#address").val(subIframeSelectDataSellOrder.address);
				$("#mobile").val(subIframeSelectDataSellOrder.mobile);
				$("#orderId").val(subIframeSelectDataSellOrder.orderId);
				$("#orderNumber").val(subIframeSelectDataSellOrder.orderNumber);
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