jQuery(function($) {
	var grid_selector = "#grid-table";
	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/buyOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品',  '产品名称','规格','产品编号','色号','单价','件数', '总片数', '面积','总金额', '仓库编号','存储仓库','操作'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%', hidden:true},
			{name:'product.name',index:'name', width:'10%'},
			{name:'product.spec',index:'spec', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.color',index:'color', width:'10%'},
			{name:'product.price',index:'price', width:'10%',hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%'},
			{name:'area',index:'area', width:'10%'},
			{name:'amount',index:'amount', width:'10%',hidden:true},
			{name:'storageId',index:'amount', width:'10%',hidden:true},
			{name:'storage.name', width:'5%',hidden:true,},
			{name:'myac',index:'', width:80,hidden:true, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter: function (cellvalue, options, rowObject) {
					var rowid = options.rowId;
					var buttons;
					buttons =  "<a href=\"#\" class=\"icon-film red\" onclick=\"storageSettting(" + rowid+")\" title=\"仓库设置\"></a>";
					return  buttons;
				}
			}
		],//列属性
//		afterSaveCell: function (rowid, cellname, value, iRow, iCol) {
//            if (cellname == 'storage') { //为联动的select列编辑，更新cityid，传入的value桉树为option的value，不是text
//               $(grid_selector).jqGrid('setRowData', rowid, {storageId:value}) ;
//            }
//        },
//		cellEdit:true,
//		cellsubmit:"clientArray",
		viewrecords : true,//显示显示总记录数
		altRows: true,//设置表格 zebra-striped 值
		caption: "采购订单商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
//		gridComplete:function(){
//			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
//			var sum=0;
//			for(var i=0;i<rowDatas.length;i++){
//				sum=(Number(sum)+Number(rowDatas[i].amount)).toFixed(6);
//			}
//			$(grid_selector).footerData("set",{area:"合计",amount:sum});
//		}
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
	    useColSpanStyle: true, 
	    groupHeaders:[
	      {startColumnName: 'product.productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
	    ]
	});
});

jQuery(function($) {
	var grid_selector2 = "#grid-table2";
	jQuery(grid_selector2).jqGrid({
		url:basePath+"erp/inoutorder/getListByOrder",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品',  '产品名称','规格','产品编号','色号','单价','件数', '总片数', '面积','总金额',"出库名称"],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%',hidden:true},
			{name:'product.name',index:'name', width:'10%'},
			{name:'product.spec',index:'spec', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.color',index:'color', width:'10%'},
			{name:'product.price',index:'price', width:'10%',hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'proCount',index:'proCount', width:'10%'},
			{name:'area',index:'area', width:'10%',hidden:true},
			{name:'amount',index:'amount', width:'10%',hidden:true},
			{name:'storage.name',index:'amount', width:'10%'}
		],//列属性
		viewrecords : true,//显示显示总记录数
		altRows: true,//设置表格 zebra-striped 值
		caption: "商品入库信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true//分页上添加一行，用于显示统计信息
	});
	$(grid_selector2).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector2).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.productId', numberOfColumns: 5, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
});