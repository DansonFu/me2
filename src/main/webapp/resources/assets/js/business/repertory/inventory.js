jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/inventory/getListAll",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['产品Id','产品名称', '产品编号', '产品规格型号','色号', '产品单位', '单价','类型','单片面积','库存数量','总面积','库存id','仓库名称','备注','创建时间','最近更新时间'],//列显示名称
		colModel:[
			{name:'product.productId',width:'10%',searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true,key:true,},
			{name:'product.name',width:'10%',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.num',width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.spec', width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.color', width:'6%', edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.unit', width:'6%', hidden:true,edittype:'select', editoptions:{value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"}, formatter:'select'},
			{name:'product.price',index:'product.price', width:'8%', editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'product.statisticType',index:'product.statisticType', width:'8%', edittype:'select',editoptions:{value:"0:实木地板;1:实木多层地板;2:强化地板;3:实木踢脚线;4:辅料"},stype:'select', searchoptions:{sopt:['eq','ne'],value:"0:实木地板;1:实木多层地板;2:强化地板;3:实木踢脚线;4:辅料"}, formatter:'select'},
			{name:'product.area',index:'product.area', width:'8%', hidden:true,editable:false,search:false},
			{name:'count',index:'count', width:'12%', editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']},
				formatter: function (cellvalue, options, rowObject) {
					var spec = rowObject.product.spec;
					var specs = spec.split("/");
					var specSize = specs[1];
					specSize = parseInt(specSize);
					var jianshu = parseInt(cellvalue/specSize);
					var sanpian = cellvalue%specSize;
					var alert = rowObject.product.alert;
					var useAlert = rowObject.product.useAlert;
					var result = cellvalue+"(件数:"+jianshu+"件,散片:"+sanpian+"片)";
					if("1"== useAlert){
						if(cellvalue < alert){
							return "<font color='red'>"+result+"</font>";
						}
					}
					return  result;
				}
			},
			{name:'area',index:'area', width:'8%', editable:false,search:false,
				formatter: function (cellvalue, options, rowObject) {
					cellvalue = Number(rowObject.product.area*rowObject.count).toFixed(6);
					return cellvalue;
				}
			},
			{name:'storage.storageId',width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'storage.name',width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'mark',index:'mark', width:'8%',searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'createTime',index:'createTime', width:'16%', editable:false, hidden:true, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'updateTime',index:'updateTime', width:'16%', editable:false, hidden:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		rowList:[10,20,30],//可选显示记录条数
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
		caption: "商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector).jqGrid('resetSelection');
		    return (true);
		},
		onSelectRow:function(rowid,status){
			var rowData = $("#grid-table").jqGrid("getRowData",rowid);
			var productId  = rowData["product.productId"];
			var storageId  = rowData["storage.storageId"];
			jQuery("#grid-table3").jqGrid("setGridParam",{postData:{"productId":productId}}).trigger("reloadGrid");
			jQuery("#grid-table2").jqGrid("setGridParam",{postData:{"productId":productId,"storageId":storageId}}).trigger("reloadGrid");
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
			window.location = basePath + "erp/inventory/getListAllExport?postData="+postData;
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
	}
	
	function style_delete_form(form) {
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
		buttons.eq(1).prepend('<i class="icon-remove"></i>')
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
			showMessage("编辑提示","编辑成功");
			return [true];
		}else{
			return [false,"编辑失败"];
		}
	}
});


jQuery(function($) {
	var grid_selector = "#grid-table3";
	var pager_selector = "#grid-pager3";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/inventory/getListWithStorage",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['编号', '产品Id','产品名称', '产品编号', '产品规格型号','色号', '产品单位', '单价','类型','单片面积','总面积','库存id','仓库名称','库存数量','备注','创建时间','最近更新时间'],//列显示名称
		colModel:[
			{name:'id', width:'5%', editable:false,hidden:true,key:true,search:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.productId',width:'10%',search:false,searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'product.name',width:'10%',search:false,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.num',width:'6%', search:false,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.spec', width:'6%', search:false,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.color', width:'6%', search:false,edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.unit', width:'6%', search:false,hidden:true,edittype:'select', editoptions:{value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"}, formatter:'select'},
			{name:'product.price',index:'product.price',search:false, width:'8%', hidden:true,editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'product.statisticType',index:'product.statisticType', search:false,hidden:true,width:'8%', edittype:'select',editoptions:{value:"0:实木地板;1:实木多层地板;2:强化地板;3:实木踢脚线;4:辅料"},stype:'select', searchoptions:{sopt:['eq','ne'],value:"0:实木地板;1:实木多层地板;2:强化地板;3:实木踢脚线;4:辅料"}, formatter:'select'},
			{name:'product.area',index:'product.area', width:'8%',search:false, hidden:true,editable:false,search:false},
			{name:'area',index:'area', width:'8%', editable:false,search:false,
				formatter: function (cellvalue, options, rowObject) {
					cellvalue = Number(rowObject.product.area*rowObject.count).toFixed(6);
					return cellvalue;
				}
			},
			{name:'storage.storageId',width:'6%', search:false,searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'storage.name',width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'count',index:'count', width:'12%', editable:false,searchoptions:{sopt:['eq','ne']},
				formatter: function (cellvalue, options, rowObject) {
					var spec = rowObject.product.spec;
					var specs = spec.split("/");
					var specSize = specs[1];
					specSize = parseInt(specSize);
					var jianshu = parseInt(cellvalue/specSize);
					var sanpian = cellvalue%specSize;
					return  cellvalue+"(件数:"+jianshu+"件,散片:"+sanpian+"片)";
				}
			},
			
			{name:'mark',index:'mark', width:'8%',searchoptions:{sopt:['eq','ne','cn','nc']},hidden:true},
			{name:'createTime',index:'createTime', width:'16%', editable:false, hidden:true, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'updateTime',index:'updateTime', width:'16%', editable:false, hidden:false,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				//styleCheckbox(table);
				
				//updateActionIcons(table);
				updatePagerIcons(table);
				//enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "商品库存信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		//设置jqgrid复选框为单选形式
		multiselect: false,	//设置多选
		gridComplete: function(){	//隐藏表头复选框 id为：cb+jqgrid的id
			var rowDatas = $(grid_selector).jqGrid("getRowData");
			if(rowDatas.length==0){
				$("#inventordiv").hide();
			}else{
				$("#inventordiv").show();
			}
		},
		onSelectRow:function(rowid,status){
			var rowData = $("#grid-table3").jqGrid("getRowData",rowid);
			var productId  = rowData["product.productId"];
			var storageId  = rowData["storage.storageId"];
			jQuery("#grid-table2").jqGrid("setGridParam",{postData:{"productId":productId,"storageId":storageId}}).trigger("reloadGrid");
		},
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	//navButtons
	jQuery(grid_selector).jqGrid('navGrid', pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'icon-pencil blue',
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
		},{},{},{},{
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
		},{}
	);
	
	jQuery("#grid-table3").jqGrid('navGrid',"#grid-pager3").jqGrid('navButtonAdd',"#grid-pager3",{
		caption:"", 
		buttonicon:"icon-inbox purple", 
		onClickButton:function(){
			var postData=jQuery("#grid-table3").jqGrid().getGridParam("postData");
			postData = JSON.stringify(postData);
			window.location = basePath + "erp/inventory/getListWithStorageExport?postData="+postData;
		}, 
		position: "last", 
		title:"导出excel", 
		cursor: "pointer",
		id:"exportExcel"
	});
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.name', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
});

jQuery(function($) {
	var grid_selector = "#grid-table2";
	var pager_selector = "#grid-pager2";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/inoutorder/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['出入库编号', '仓库名称','订单号','订单编号','客户名称','产品id','产品名称', '产品规格型号','产品编号', '色号', '产品单位', '单价','数量','金额','备注','类型','状态','创建时间','修改时间'],//列显示名称
		colModel:[
			{name:'inoutorderId',index:'inoutorderId', width:'5%', hidden:true,editable:false,key:true,searchoptions:{sopt:['eq','ne']}},
			{name:'storage.name',width:'6%', searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'order.orderId',index:'orderNo',width:'8%',hidden:true,editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'order.orderNumber',width:'10%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'order.contact.name',index:'name',width:'8%', editable:false,searchoptions:{sopt:['eq','ne']}},
			{name:'product.productId',index:'productId',hidden:true, width:'5%', searchoptions:{sopt:['eq','ne']}},
			{name:'product.name',width:'10%',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.spec',index:'spec', width:'6%', hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.num',index:'num', width:'6%',hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.color',index:'color', width:'5%',hidden:true, edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.unit',index:'unit', width:'8%', hidden:true, edittype:'select', editoptions:{value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:米;1:件;2:箱;3:台;4:个;5:瓶;6:盒;7:只;8:元;9:平方米"}, formatter:'select'},
			{name:'price',index:'price', width:'8%',hidden:true, editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'proCount',index:'proCount', width:'8%', editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'amount',index:'amount', width:'8%',hidden:true, editable:false,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'mark',index:'mark', width:'8%',hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'type',index:'type', width:'8%', editable:false,edittype:'select',
				editoptions:{value:"1:销售入库单;2:采购入库单;3:销售退货入库单;4:销售出库单;5:采购退货出库单"},stype:'select',
				searchoptions:{sopt:['eq','ne'],value:"1:销售入库单;2:采购入库单;3:销售退货入库单;4:销售出库单;5:采购退货出库单"},formatter:'select'},
			{name:'status',index:'status', width:'8%', hidden:true,editable:false,edittype:'select',
					editoptions:{value:"1:初始录入;2:已入库;3:作废;4:已出库"},stype:'select',
					searchoptions:{sopt:['eq','ne'],value:"1:初始录入;2:已入库;3:作废;4:已出库"},
					formatter:'select'},
			{name:'createTime',index:'createTime', width:'16%', editable:false, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en'],
				searchhidden:true
			}},
			{name:'updateTime',index:'updateTime', width:'16%', editable:false, hidden:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en'],
				searchhidden:true
			}}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				//styleCheckbox(table);
				
				//updateActionIcons(table);
				updatePagerIcons(table);
				//enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "商品出入库单信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		//设置jqgrid复选框为单选形式
		multiselect: false,	//设置多选
		gridComplete: function(){	//隐藏表头复选框 id为：cb+jqgrid的id
			
			var rowDatas = $(grid_selector).jqGrid("getRowData");
			if(rowDatas.length==0){
				$("#inoutdiv").hide();
			}else{
				$("#inoutdiv").show();
			}
		}
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.name', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
	jQuery(grid_selector).jqGrid('navGrid', pager_selector,
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
		buttonicon:"icon-inbox purple", 
		onClickButton:function(){
			var postData=jQuery("#grid-table2").jqGrid().getGridParam("postData");
			postData = JSON.stringify(postData);
			window.location = basePath + "erp/inoutorder/getListExport?postData="+postData;
		}, 
		position: "last", 
		title:"导出excel", 
		cursor: "pointer",
		id:"exportExcel"
	});
});

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