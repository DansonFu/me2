jQuery(function($) {
	var grid_selector = "#grid-table";
	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/buyOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称','规格','产品编号', '色号','单价','件数', '总片数', '面积','总金额', '优惠金额'],//列显示名称
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
		gridComplete:function(){
			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
			var sum=0;
			for(var i=0;i<rowDatas.length;i++){
				//sum=(Number(sum)+Number(rowDatas[i].amount)).toFixed(6);
				sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
			}
			$(grid_selector).footerData("set",{amount:"合计",discount:sum});
		}
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
	jQuery(grid_selector).jqGrid('setGroupHeaders', {
        useColSpanStyle: true, 
        groupHeaders:[
          {startColumnName: 'product.productId', numberOfColumns: 6, titleText: "<p style='text-align:center;'>商品信息</p>"},
        ]
	});
});
var selectStorageData = null;

function storageSettting(rowid){
	var rowData = $("#grid-table").jqGrid("getRowData",rowid);
	storageId = rowData.storageId;
	$.fDialog({
		title : '<b>仓库信息列表</b>',
		content : basePath+'erp/selectStorage?storageId='+storageId,
		module : 'iframe',
		w : '80%',
		h : '522',
		buttons : ['确定'],
		beforeRemove : function($f){
			if(selectStorageData!=null){
				$("#grid-table").jqGrid('setRowData', rowid, {storageId:selectStorageData.storageId, storage:selectStorageData.name}) ;
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

function subIframeSetSelectDataStorage(storageData){
	selectStorageData =storageData;
}