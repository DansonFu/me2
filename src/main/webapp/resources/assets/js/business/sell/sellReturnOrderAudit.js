jQuery(function($) {
	var grid_selector2 = "#grid-table2";
	var pager_selector2 = "#grid-pager2";
	
	jQuery(grid_selector2).jqGrid({
		url:basePath+"erp/sellOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称', '规格','产品编号','色号','单价','每箱片数','件数', '总片数','面积','金额','优惠金额'],//列显示名称
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
			{name:'discount',index:'discount', width:'10%'}
		],//列属性
		viewrecords : true,//显示显示总记录数
		//pager : pager_selector2,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		caption: "退货商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		postData:{"orderId":requestOrderId},
		loadonce:true,
		footerrow:true,
		//multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	
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
	
});