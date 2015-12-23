jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/returnOrder/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['订单号', '订单编号','客户','客户', '地址', '电话', '手机', '总金额','创建时间','修改时间','类型','创建人','状态','付款方式','供应类别','日期','备注','回馈','操作'],//列显示名称
		colModel:[
			{name:'orderId', width:'5%', editable:false,hidden:true,key:true},
			{name:'orderNumber',width:'8%',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contact.name',width:'8%', editable:false,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contact.contactId',hidden:true, width:'5%', editable:true, edittype:'text',searchoptions:{sopt:['eq','ne']},editrules:{required:true}},
			{name:'address',width:'10%', editable:true,hidden:true, edittype:'text',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'phone', width:'6%', editable:true,hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'mobile',width:'6%', editable:true,hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'amount',width:'6%', editable:true,searchoptions:{sopt:['eq','ne','lt','le','gt','ge']}},
			{name:'createTime',width:'8%', editable:false, hidden:false, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'updateTime',width:'16%', editable:false, hidden:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'type',width:'8%', editable:true,hidden:true,searchoptions:{sopt:['eq','ne']}},
			{name:'creator.name',width:'8%', editable:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'status',width:'8%', editable:true,edittype:'select',editoptions:{value:"1:初始录入;2:审核中;3:审核完成;4:作废"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"1:初始录入;2:审核中;3:审核完成;4:作废"},formatter:'select'},
			{name:'needPay',width:'8%', editable:true,edittype:'select',editoptions:{value:"0:直接付款;1:赠送;2:赊账;3:从余款中扣除;4:其他"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:直接付款;1:赠送;2:赊账;3:从余款中扣除;4:其他"},formatter:'select'},
			{name:'productType',width:'8%', editable:true,edittype:'select',editoptions:{value:"0:库存;1:外购需检;2:外购免检"},stype:'select',searchoptions:{sopt:['eq','ne'],value:"0:库存;1:外购需检;2:外购免检"},formatter:'select'},
			{name:'date',width:'8%', editable:true,hidden:true,searchoptions:{sopt:['bw','ew','bn','en']}},
			{name:'note',width:'8%', editable:true,search:false},
			{name:'feedback',width:'8%', editable:true,hidden:true,searchoptions:{sopt:['eq','ne']}},
			{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter: function (cellvalue, options, rowObject) {
					var orderSubmit = "<a class=\"icon-edit grey\" title=\"提交申请\" disabled></a>";
					var viewOrderAudit = "<a href=\"#\" class=\"icon-folder-open blue\" onclick=\"viewOrderProcessAudit(" + rowObject.status+","+ rowObject.orderId+ ")\" title=\"查看审核记录\"></a>";
					if(rowObject.status=="1"){
						orderSubmit = "<a href=\"#\" class=\"icon-edit red\" onclick=\"orderProcessSubmit(" + rowObject.status+","+ rowObject.orderId+ ")\" title=\"提交申请\"></a>";
						viewOrderAudit = "<a class=\"icon-folder-open grey\" title=\"查看审核记录\" disabled></a>";
					}
					return  orderSubmit+"&nbsp;" +viewOrderAudit;
				}
			}
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum:10,//设置初始显示记录条数
		rowList:[10,20,30],//可选显示记录条数
		pager : pager_selector,//定义翻页用的导航栏
		altRows: true,//设置表格 zebra-striped 值
		//设置jqgrid复选框为单选形式
		multiselect: true,	//设置多选
		beforeSelectRow: function(rowid, e){	//设置单选
		    jQuery(grid_selector).jqGrid('resetSelection');
		    return (true);
		},
		gridComplete: function(){	//隐藏表头复选框 id为：cb+jqgrid的id
			$("#cb_grid-table").hide();
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
		caption: "销售退货订单管理",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
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
	
//	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
//		caption:"", 
//		buttonicon:"icon-exchange grey", 
//		onClickButton:function(){
//			var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
//			if(myid==null||myid==""){
//				alert("请选择一行记录");
//				return;
//			}
//			if($("#grid-table").jqGrid("getRowData",myid).status!="1"){
//				alert("只能作废初始录入的订单");
//				return;
//			}
//			$.ajax({
//				url:basePath+"erp/sellOrder/cancelOrder",
//				type:"post",
//				datatype:"json",
//				data:{"orderId":myid},
//				success:function(data){
//					if(data.message=="success"){
//						alert("作废成功");
//						jQuery("#grid-table").jqGrid().trigger("reloadGrid");
//					}else{
//						alert("作废失败");
//					}
//				}
//		    });
//		}, 
//		position: "first", 
//		title:"作废", 
//		cursor: "pointer",
//		id:"cancelButton"
//	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-trash red", 
		onClickButton:function(){
			var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
			if(myid==null||myid==""){
				fDialogAlert("请选择一行记录");
				return;
			}
			if($("#grid-table").jqGrid("getRowData",myid).status!="1"){
				fDialogAlert("只能删除初始录入的订单");
				return;
			}
			$.fDialog.alert("您确定要删除该订单信息？","question",{
				title : "系统提示信息",
				buttons : [{text:'提交',value:'yes'},{text:'取消',cls:'gray',value:'no'}],
				buttonClick : function(v){
					if(v=='yes'){
						$.ajax({
							url:basePath+"erp/sellOrder/delOrder",
							type:"post",
							datatype:"json",
							data:{"orderId":myid},
							success:function(data){
								if(data.message=="success"){
									fDialogAlert("删除成功");
									jQuery("#grid-table").jqGrid().trigger("reloadGrid");
								}else{
									fDialogAlert("删除失败");
								}
							}
					    });
					}
					return true;
				}
			});
		}, 
		position: "first", 
		title:"删除订单", 
		cursor: "pointer",
		id:"delButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-pencil blue", 
		onClickButton:function(){
			var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
			if(myid==null||myid==""){
				fDialogAlert("请选择一行记录");
				return;
			}
			if($("#grid-table").jqGrid("getRowData",myid).status!="1"){
				fDialogAlert("只能修改初始录入的订单");
				return;
			}
			window.location=basePath+"erp/toEditReturnOrder?orderId="+myid;
		}, 
		position: "first", 
		title:"修改订单", 
		cursor: "pointer",
		id:"editButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-plus-sign purple", 
		onClickButton:function(){
			window.location=basePath+"erp/toAddReturnOrder";
		}, 
		position: "first", 
		title:"新增订单", 
		cursor: "pointer",
		id:"addButton"
	});
	
	jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
		caption:"", 
		buttonicon:"icon-inbox purple", 
		onClickButton:function(){
			var postData=jQuery("#grid-table").jqGrid().getGridParam("postData");
			postData = JSON.stringify(postData);
			window.location = basePath + "erp/returnOrder/getListExport?postData="+postData;
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
			fDialogAlert("编辑成功");
			return [true];
		}else{
			return [false,"编辑失败"];
		}
	}
});

function orderProcessSubmit(status, orderId){
	//alert("status : " + status+", orderId : " + orderId);
	if(status!=1){
		fDialogAlert("该销售记录申请已提交或完成！");
		return;
	}
	//询问框是否提交
	$.fDialog.alert("您确定要为该订单提交申请？","question",{
		drag:true,
		title : "系统提示信息",
		buttons : [{text:'提交',value:'yes'},{text:'取消',cls:'gray',value:'no'}],
		buttonClick : function(v){
			if(v=='yes'){
				$.ajax({
					url:basePath+"erp/sellReturnOrder/startProcess",
					type:"post",
					datatype:"json",
					data:{"id":orderId},
					success:function(data){
						$("#grid-table").trigger("reloadGrid");
					}
			    });
			}
			return true;
		}
	});
}

function viewOrderProcessAudit(status, orderId){
	if(status==1){
		fDialogAlert("该销售订单未提交！");
		return;
	}
	window.location=basePath+"/erp/sellReturnOrder/sellReturnOrderAuditHis?id="+orderId;
}