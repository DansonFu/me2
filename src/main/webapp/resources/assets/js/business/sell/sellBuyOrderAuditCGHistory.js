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
		colNames:['ID', '产品', '产品名称','规格','产品编号','色号','单价','件数', '总片数', '面积','总金额','优惠金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
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
			{name:'discount',index:'discount', width:'10%'}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		//pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		caption: "销售采购订单商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
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
			$("#cb_grid-table").hide();
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