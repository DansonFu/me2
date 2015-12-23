jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/payReceiver/getPayReceiverRecordList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '订单编号', '支付金额（单位：元）', '记录人', '支付时间'],//列显示名称
		colModel:[
			{name:'id',index:'id', width:60, editable:false,hidden:true,key:true},
			{name:'order.orderNumber',index:'orderId', width:100},
			{name:'amount',index:'amount', width:150},
			{name:'creater.name',index:'creater', width:100},
			{name:'createTime',index:'createTime', width:100}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		//设置jqgrid复选框为单选形式
		//multiselect: true,	//设置多选
		postData:{"payId":requestPayId},
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "",//表格名称
		autowidth: true
	});
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
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
	
});