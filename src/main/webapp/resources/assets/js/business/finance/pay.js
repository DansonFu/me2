jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/payReceiver/getPayList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['编号', '订单编号','内容', '供应商姓名','供应商电话', '应付款金额（单位：元）','已付款金额（单位：元）','未付款金额（单位：元）','本次付款金额（单位：元）','状态','类型','创建时间','订单号', '客户编号', '操作'],//列显示名称
		colModel:[
			{name:'payId', width:'5%', editable:false,hidden:true,key:true,searchoptions:{sopt:['eq','ne']}},
			{name:'order.orderNumber', width:'10%', formoptions: {rowpos: 1,colpos: 1},editable:true,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'name',width:'10%', formoptions: {rowpos: 1,colpos: 2},hidden:true,editable:false,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contact.name', formoptions: {rowpos: 2,colpos: 1},width:'6%', editable:true,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contact.mobile', formoptions: {rowpos: 2,colpos: 2},width:'8%', editable:true,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'amount', width:'8%', formoptions: {rowpos: 3,colpos: 1},editable:true,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'paid', width:'8%', formoptions: {rowpos: 4,colpos: 1},editable:true,editoptions:{readonly:true},editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'unpaid', width:'8%', formoptions: {rowpos: 4,colpos: 2},editable:true,editoptions:{readonly:true},searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'thisPaid', width:'8%', formoptions: {rowpos: 5,colpos: 2},editable:true,hidden:true,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']},editrules:{number:true,edithidden:true,custom:true,
				custom_func:function(value,colname){
					var rowId = $(grid_selector).jqGrid("getGridParam",'selrow');
					var rowData = $(grid_selector).jqGrid("getRowData", rowId);
					var unpaid = rowData.unpaid;
					if(value==""){
						return [false, "请输入付款金额"];
					}
					if(Number(unpaid)<Number(value)){
						return [false, "付款金额不能大于未支付金额"];
					}else{
						return [true];
					}
				}
			}},
			{name:'status',index:'status', formoptions: {rowpos: 3,colpos: 2},width:'8%', editable:true, edittype:'select',editoptions:{value:"1:未支付;2:部分支付;3:已结清"},stype:'select', searchoptions:{sopt:['eq','ne'],value:"1:未支付;2:部分支付;3:已结清"}, formatter:'select'},
			{name:'type', width:'8%', formoptions: {rowpos: 5,colpos: 1},editable:true, edittype:'select',editoptions:{value:"2:销售退货付款;3:采购付款;5:销售采购付款"},stype:'select', searchoptions:{sopt:['eq','ne'],value:"2:销售退货付款;3:采购付款;5:销售采购付款"}, formatter:'select'},
			{name:'creatTime',index:'creatTime', width:'10%', editable:false, hidden:false, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'order.orderId',width:'5%', editable:true,hidden:true,editrules:{edithidden:false},editoptions:{readonly:true}, edittype:'text',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contact.contactId', width:'6%', hidden:true,editable:true, editrules:{edithidden:false},editoptions:{readonly:true}},
			{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter:'editactions', 
				formatoptions:{ 
					keys:true,
					delbutton:false, 
					//editbutton: true,
					editformbutton:true,
					edittitle:"付款",
					editicon:"icon-edit red",
					editOptions:{width:640,closeAfterEdit: true, recreateForm: true, beforeShowForm:beforeEditCallback, afterSubmit : onEditafterSubmit}
				}
			}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		//altRows: true,//设置表格 zebra-striped 值
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "应付款记录信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"erp/payReceiver/payReceiverOper",
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector).jqGrid('resetSelection');
		    return (true);
		},
		gridComplete: function(){	//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table").hide();
		}
	});
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.name', numberOfColumns: 7, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
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
			editicon : 'icon-edit red',
			edittext:"",
			edittitle:"付款",
			add: false,
			addicon : 'icon-plus-sign purple',
			del: false,
			delicon : 'icon-trash red',
			search: true,
			searchicon : 'icon-search orange',
			refresh: true,
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
					fDialogAlert("添加成功");
					return [true];
				}else{
					return [false,"添加失败"];
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
				//alert(1);
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
		buttonicon:"icon-inbox purple", 
		onClickButton:function(){
			var postData=jQuery("#grid-table").jqGrid().getGridParam("postData");
			postData = JSON.stringify(postData);
			window.location = basePath + "erp/payReceiver/getPayListExport?postData="+postData;
		}, 
		position: "last", 
		title:"导出excel", 
		cursor: "pointer",
		id:"exportExcel"
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
		
		//设置下拉不可用
		form.find("#type").prop("disabled","disabled");
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
			fDialogAlert("删除成功");
			return [true];
		}else{
			return [false,"删除失败"];
		}
	} 
	
	function onEditafterSubmit(response, postdata){
		if(response.responseJSON.message=="success"){
			return [true];
		}else{
			return [false,"编辑失败"];
		}
	}
});

function viewPayReceiverRecord(rowid){
	$.fDialog({
		title : '<b>付款记录信息列表</b>',
		content : basePath+'erp/payReceiver/toPayReceiverRecordList?payId='+rowid,
		module : 'iframe',
		w : '80%',
		h : '522',
		buttons : ['确定'],
		beforeRemove : function($f){
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}