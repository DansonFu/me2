var subIframeSelectDataInventory=null;
var selectMaterialEditId = "";
var autoNum =-1;
function getautoId(){
	return autoNum--;
}

jQuery(function($) {
	var grid_selector = "#grid-table";
	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/sellOrder/getOrderMaterialList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称','产品编号', '规格','色号','单价','每箱片数','件数', '片数', '总片数','面积','总金额','优惠金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'productId', width:'5%',hidden:true},
			{name:'product.name',index:'name', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.spec',index:'spec', width:'10%'},
			{name:'product.color',index:'color', width:'10%'},
			{name:'product.price',index:'price', width:'10%',hidden:true},
			{name:'product.silces',index:'silces', width:'10%',hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%',hidden:true},
			{name:'allSlices',index:'allSlices', width:'10%',
				formatter: function (cellvalue, options, rowObject) {
					cellvalue = Number(rowObject.pieces*rowObject.product.silces)+Number(rowObject.slices);
					return cellvalue;
				}},
			{name:'area',index:'area', width:'10%'},
			{name:'amount',index:'amount', width:'10%',hidden:true},
			{name:'discount',index:'discount', width:'10%',hidden:true}
		],//列属性
		viewrecords : true,//显示显示总记录数
		altRows: true,//设置表格 zebra-striped 值
		caption: "订单商品信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		loadonce:true,
		postData:{"orderId":requestOrderId},
		footerrow: true,//分页上添加一行，用于显示统计信息
		gridComplete:function(){
//			var rowDatas=jQuery(grid_selector).jqGrid().getRowData();
//			var sum=0;
//			for(var i=0;i<rowDatas.length;i++){
//				sum=(Number(sum)+Number(rowDatas[i].amount)-Number(rowDatas[i].discount)).toFixed(2);
//			}
//			$(grid_selector).footerData("set",{amount:"合计",discount:sum});
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

jQuery(function($) {
	var grid_selector2 = "#grid-table2";
	var pager_selector2 = "#grid-pager2";
	
	jQuery(grid_selector2).jqGrid({
//		url:basePath+"erp/sellOrder/getOrderMaterialList",//获取数据的地址
//		mtype:"POST",
		datatype: "local",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '产品名称','产品编号', '规格','色号','每箱片数','件数', '片数','总片数','所在仓库名称','所在仓库id','仓库总量'],//列显示名称
		colModel:[
			{name:'id',index:'id', width:'5%', editable:false,hidden:true},
			{name:'product.productId',index:'product.productId', width:'5%',hidden:true},
			{name:'product.name',index:'product.name', width:'10%'},
			{name:'product.num',index:'product.num', width:'10%'},
			{name:'product.spec',index:'product.spec', width:'10%'},
			{name:'product.color',index:'product.color', width:'10%'},
			{name:'product.silces',index:'product.silces', width:'10%',hidden:true},
			{name:'pieces',index:'pieces', width:'10%',hidden:true},
			{name:'slices',index:'slices', width:'10%',hidden:true},
			{name:'allSlices',index:'allSlices', width:'10%',
				formatter: function (cellvalue, options, rowObject) {
					cellvalue = Number(rowObject.pieces*rowObject.product.silces)+Number(rowObject.slices);
					return cellvalue;
				}},
			{name:'storage.name',index:'storage.name', width:'10%'},
			{name:'storage.storageId',index:'storage.storageId', width:'10%',hidden:true},
			{name:'count',index:'count', width:'10%',hidden:true}
		],//列属性
		viewrecords : true,//显示显示总记录数
		pager : pager_selector2,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		caption: "商品出库信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true,
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector2).jqGrid('resetSelection');
		    
		    //设置编辑数据
		    selectMaterialEditId = rowid;
			var rowData = $("#grid-table2").jqGrid("getRowData",rowid);
			setEditMatrailData(rowData);
			subIframeSelectDataInventory=rowData;
			$("#editType").val("edit");
		    return (true);
		},
		gridComplete:function(){
			//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table2").hide();
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
	
	jQuery(grid_selector2).jqGrid('navGrid',pager_selector2,
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
		buttonicon:"icon-trash red", 
		onClickButton:function(){
			var myid=$("#grid-table2").jqGrid("getGridParam",'selarrrow');
			$("#grid-table2").jqGrid("delRowData",myid);
			$("#inventoryId").val("");
			$("#productName").val("");
			$("#inventoryStorageName").val("");
			$("#inventoryStorageId").val("");
			$("#inventoryCount").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			subIframeSelectDataInventory = null;
			selectMaterialEditId = null;
			$("#editType").val("");
		}, 
		position: "first", 
		title:"删除商品", 
		cursor: "pointer",
		id:"delButton2"
	});
	
	jQuery("#grid-table2").jqGrid('navGrid',"#grid-pager2").jqGrid('navButtonAdd',"#grid-pager2",{
		caption:"", 
		buttonicon:"icon-pencil blue", 
		onClickButton:function(){
			var myid=$("#grid-table2").jqGrid("getGridParam",'selarrrow');
			if(myid!=""){
				$("#addEditDiv").show();
				selectMaterialEditId = myid;
				var rowData = $("#grid-table2").jqGrid("getRowData",myid);
				setEditMatrailData(rowData);
				$("#editType").val("edit");
				
				//
				$("#andEditText").html("修改购买商品信息");
			}else{
				fDialogAlert("请选择要修改的数据行");
			}
			
		}, 
		position: "first", 
		title:"修改商品", 
		cursor: "pointer",
		id:"editButton2"
	});
	
	jQuery("#grid-table2").jqGrid('navGrid',"#grid-pager2").jqGrid('navButtonAdd',"#grid-pager2",{
		caption:"", 
		buttonicon:"icon-plus-sign green", 
		onClickButton:function(){
			$("#addEditDiv").show();
			$("#addMaterialPieces").focus();
			
			jQuery(grid_selector2).resetSelection();
			
			$("#inventoryId").val("");
			$("#productName").val("");
			$("#inventoryStorageName").val("");
			$("#inventoryStorageId").val("");
			$("#inventoryCount").val("");
			$("#addMaterialPieces").val("");
			$("#addMaterialSlices").val("");
			
			subIframeSelectDataInventory = null;
			$("#andEditText").html("选择商品仓库信息");
			$("#editType").val("add");
		}, 
		position: "first", 
		title:"添加商品", 
		cursor: "pointer",
		id:"addButton2"
	});
	
});

//############################################################
function selectInventoryOnclick(){
	$.fDialog({
		title : '<b>库存信息列表</b>',
		content : basePath+'erp/selectInventory?inventoryId='+$("#inventoryId").val(),
		module : 'iframe',
		w : '80%',
		h : '523',
		x :  $(parent.window).width()/2-$(parent.window).width()*0.8/2,
		y : $(parent.window).height()/2-523/2+$(parent.document).scrollTop()-$(parent.document).find("#navbar").height()-$(parent.document).find("#breadcrumbs").height(),
		buttons : ['确定'],
		beforeRemove : function($f){
			if(subIframeSelectDataInventory){
				$("#inventoryId").val(subIframeSelectDataInventory.id);
				$("#productName").val(subIframeSelectDataInventory['product.name']);
				$("#inventoryStorageName").val(subIframeSelectDataInventory['storage.name']);
				$("#inventoryStorageId").val(subIframeSelectDataInventory['product.id']);
				$("#inventoryCount").val(subIframeSelectDataInventory.count);
			}
			return true;
		},
		drag:true,
		footer : true, //不显示按钮栏
		shade : true //显示遮挡层
	});
}

function addMaterialSubmit(){
	var postdata={};
	//(1)获取数据
	var data = subIframeSelectDataInventory;
	
	var addMaterialPieces = $("#addMaterialPieces").val();
	var addMaterialSlices = $("#addMaterialSlices").val();
	
	if(addMaterialPieces!=null&&addMaterialPieces!=""){
		if(!((!isNaN(addMaterialPieces))&&(parseInt(addMaterialPieces)==addMaterialPieces)&&(addMaterialPieces>=0))){
			fDialogAlert("商品件数：请输入一个非负整数");
			return;
		}
	}else{
		addMaterialPieces=0;
	}
	
	if(addMaterialSlices!=null&&addMaterialSlices!=""){
		if(!((!isNaN(addMaterialSlices))&&(parseInt(addMaterialSlices)==addMaterialSlices)&&(addMaterialSlices>=0))){
			fDialogAlert("商品散片数：请输入一个非负整数");
			return;
		}
	}else{
		addMaterialSlices=0;
	}
	
	if(addMaterialPieces==0&&addMaterialSlices==0){
		fDialogAlert("商品总片数不能为0");
		return;
	}
	
	if((Number(addMaterialPieces*data['product.silces'])+Number(addMaterialSlices))>data.count){
		fDialogAlert("所选商品数量大于仓库总量");
		return;
	}
	
	//(2)设置数据
	postdata.product={};
	postdata.product.name=data['product.name'];
	postdata.product.num=data['product.num'];
	postdata.product.spec=data['product.spec'];
	postdata.product.color=data['product.color'];
	postdata.product.productId=data['product.productId'];
	postdata.product.silces=data['product.silces'];
	postdata.pieces=addMaterialPieces;
	postdata.slices=addMaterialSlices;
	postdata.count=data.count;
	postdata.storage={};
	postdata.storage.storageId=data['storage.storageId'];
	postdata.storage.name=data['storage.name'];
	var editType = $("#editType").val()
	if(editType=="edit"){
		jQuery("#grid-table2").jqGrid().setRowData(selectMaterialEditId,postdata);
	}else{
		jQuery("#grid-table2").jqGrid().addRowData(getautoId(),postdata,"last");
	}
	
	
	//(3)清除数据并关闭dov
	$("#editType").val("");
	$("#inventoryId").val("");
	$("#productName").val("");
	$("#inventoryStorageName").val("");
	$("#inventoryStorageId").val("");
	$("#inventoryCount").val("");
	$("#addMaterialPieces").val("");
	$("#addMaterialSlices").val("");
	subIframeSelectDataInventory=null;
}

function subIframeSetSelectData(data){
	subIframeSelectData = data;
}

function setEditMatrailData(data){
	$("#inventoryId").val(data.id);
	$("#productName").val(data["product.name"]);
	$("#inventoryStorageName").val(data["storage.name"]);
	$("#inventoryStorageId").val(data["storage.storageId"]);
	$("#inventoryCount").val(data.count);
	$("#addMaterialPieces").val(data.pieces);
	$("#addMaterialSlices").val(data.slices);
}
//############################################################

function subIframeSetSelectDataInventory(data){
	subIframeSelectDataInventory = data;
}

//############################################################
//提交操作
function billSubmit(){
	var needData = $("#grid-table").jqGrid("getRowData");
	var selectData = $("#grid-table2").jqGrid("getRowData");
	
	//合并相同的商品
	var tmpData=new Array();
	if(needData!=null&&needData.length>0){
		for(var i=0;i<needData.length;i++){
			var isFind=false;
			for(var j=0;j<tmpData.length;j++){
				if(needData[i]['product.productId']==tmpData[j]['product.productId']){
					tmpData[j]['pieces']=Number(tmpData[j]['pieces'])+Number(needData[i]['pieces']);
					tmpData[j]['slices']=Number(tmpData[j]['slices'])+Number(needData[i]['slices']);
					isFind=true;
					break;
				}
			}
			if(isFind==false){
				tmpData[tmpData.length]=needData[i];
			}
		}
	}
	needData=tmpData;
	tmpData=new Array();
	if(selectData!=null&&selectData.length>0){
		for(var i=0;i<selectData.length;i++){
			var isFind=false;
			for(var j=0;j<tmpData.length;j++){
				if(selectData[i]['product.productId']==tmpData[j]['product.productId']){
					tmpData[j]['pieces']=Number(tmpData[j]['pieces'])+Number(selectData[i]['pieces']);
					tmpData[j]['slices']=Number(tmpData[j]['slices'])+Number(selectData[i]['slices']);
					isFind=true;
					break;
				}
			}
			if(isFind==false){
				tmpData[tmpData.length]=selectData[i];
			}
		}
	}
	selectData=tmpData;
	
	//检查所选出库商品数量是否和所需出库商品数量一致
	var isEquals=true;
	if(needData.length==selectData.length){
		for(var i=0;i<needData.length;i++){
			for(var j=0;j<selectData.length;j++){
				if(needData[i]['product.productId']==selectData[j]['product.productId']){
					var needTotal=needData[i]['pieces']*needData[i]['product.silces']+Number(needData[i]['slices']);
					var selectTotal=selectData[j]['pieces']*needData[i]['product.silces']+Number(selectData[j]['slices']);
					if(needTotal==selectTotal){
						
					}else{
						isEquals=false;
					}
					break;
				}else{
					if(j==(selectData.length-1)){
						isEquals=false;
					}
				}
			}
			if(isEquals==false){
				break;
			}
		}
	}else{
		isEquals=false;
	}
	
	if(isEquals==false){
		fDialogAlert("所选出库商品数量与所需出库商品数量不一致");
		return false;
	}else{
		return true;
	}
}