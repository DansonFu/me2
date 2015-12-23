jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/inventory/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品id','品种名称', '编号','规格', '片数', '色号','面积','所在仓库名称','所在仓库id','仓库总量'],//列显示名称
		colModel:[
			{name:'id',index:'id', editable:false,hidden:true,key:true},
			{name:'product.productId',index:'product.productId', width:100,hidden:true, searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.name',index:'product.name', width:100, searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.num',index:'product.num', width:60, searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.spec',index:'product.spec', width:80, searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'product.silces',index:'product.silces', width:60, hidden:true,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'product.color',index:'product.color', width:60, edittype:'select',editoptions:{value:"0:无;1:1;2:2;3:3"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:无;1:1;2:2;3:3"}, formatter:'select'},
			{name:'product.area',index:'product.area', width:80,hidden:true, searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'storage.name',index:'storage.name', width:80,  searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'storage.storageId',index:'storage.storageId', width:80,hidden:true, searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'count',index:'count', width:80, searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
			jQuery(grid_selector).jqGrid('resetSelection');
			
			//设置选中数据行
			var rowData = $(grid_selector).jqGrid("getRowData",rowid);
			parent.subIframeSetSelectDataInventory(rowData);
		    return (true);
		},
		gridComplete: function(){	//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table").hide();
			//回显设置
			if(requestInventoryId){
				jQuery(grid_selector).jqGrid('setSelection', requestInventoryId);
			}
		},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"erp/material/oper"//对表格增删改操作提交的url
		
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
			search: true,
			searchicon : 'icon-search orange',
			refresh: true,
			refreshicon : 'icon-refresh green',
			view: true,
			viewicon : 'icon-zoom-in grey',
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
	
	
});