jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
 
	var autoNum =0;
	function getautoId(){
		return autoNum++;
	}
	jQuery(grid_selector).jqGrid({
		//url:basePath+"erp/sellOrder/getList",//获取数据的地址
		//mtype:"POST",
		datatype: "local",//从服务器端返回的数据类型
		height: 200,//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '产品', '件数', '片数', '面积','总金额'],//列显示名称
		colModel:[
			{name:'orderMaterialId',index:'orderMaterialId', width:'5%', editable:false,hidden:true},
			{name:'productId',index:'productId', width:'5%', editable:true, edittype:'select',editoptions: {dataUrl: basePath+'erp/sellOrder/getSelect'},searchoptions:{sopt:['eq','ne']}},
			{name:'pieces',index:'pieces', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}},
			{name:'slices',index:'slices', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}},
			{name:'area',index:'area', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}},
			{name:'amount',index:'amount', width:'10%', editable:true,searchoptions:{sopt:['eq','ne']}}
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
		caption: "添加商品",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"getNull",//对表格增删改操作提交的url
		loadonce:true
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
			edit: true,
			editicon : 'icon-pencil blue',
			add: true,
			addicon : 'icon-plus-sign purple',
			del: true,
			delicon : 'icon-trash red',
			search: false,
			searchicon : 'icon-search orange',
			refresh: false,
			refreshicon : 'icon-refresh green',
			view: true,
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
			afterSubmit : onEditafterSubmit,
			beforeSubmit:function(postdata, formid) {
				var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
				if(postdata.productId==null||postdata.productId==""){
					return [false,"请选择产品"]
				}else{
					if((postdata.pieces==null||postdata.pieces=="")&&(postdata.slices==null||postdata.slices=="")){
						return [false,"片数和件数至少填写一个"]
					}else{
						$.ajax({
							async:false,
							url:basePath+"erp/material/getMaterialById",
							type:"post",
							datatype:"json",
							data:{"productId":postdata.productId},
							success:function(data){
								var a=0,b=0;
								if(postdata.pieces!=null&&postdata.pieces!=""){
									a=postdata.pieces;
								}
								if(postdata.slices!=null&&postdata.slices!=""){
									b=postdata.slices;
								}
								postdata.area=((Number(data.silces*a)+Number(b))*data.area).toFixed(6);
								postdata.amount=(postdata.area*data.price).toFixed(2);
							}
					    });
						jQuery(grid_selector).jqGrid().setRowData(myid,postdata);
						return[true];
					}
				}
			}
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
					return [true];
				}else{
					return [false];
				}
			},
			beforeSubmit:function(postdata, formid) {
				if(postdata.productId==null||postdata.productId==""){
					return [false,"请选择产品"]
				}else{
					if((postdata.pieces==null||postdata.pieces=="")&&(postdata.slices==null||postdata.slices=="")){
						return [false,"片数和件数至少填写一个"]
					}else{
						$.ajax({
							async:false,
							url:basePath+"erp/material/getMaterialById",
							type:"post",
							datatype:"json",
							data:{"productId":postdata.productId},
							success:function(data){
								var a=0,b=0;
								if(postdata.pieces!=null&&postdata.pieces!=""){
									a=postdata.pieces;
								}
								if(postdata.slices!=null&&postdata.slices!=""){
									b=postdata.slices;
								}
								postdata.area=((Number(data.silces*a)+Number(b))*data.area).toFixed(6);
								postdata.amount=(postdata.area*data.price).toFixed(2);
							}
					    });
						jQuery(grid_selector).jqGrid().addRowData(getautoId(),postdata,"last");
						return[true];
					}
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

function billSubmit(){
	var griddata=jQuery("#grid-table").jqGrid("getRowData");
	griddata = JSON.stringify(griddata);
	var contactId=$("#contactId2").val();
	var phone=$("#phone").val();
	var note=$("#note").val();
	var address=$("#address").val();
	var mobile=$("#mobile").val();
	var needPay=$("#needPay").val();
	var productType=$("#productType").val();
	
	if(contactId==null||contactId==""){
		fDialogAlert("客户不能为空");
		return;
	}
	
	if(phone!=null&&phone!=""){
		var reg=RegExp("^([0-9]{3,4}-)?[0-9]{7,8}$");
		if(!reg.test(phone)){
			fDialogAlert("请填写正确的电话号码");
			return;
		}
	}
	
	if(mobile!=null&&mobile!=""){
		var reg=RegExp("^[0-9]{11}$");
		if(!reg.test(mobile)){
			fDialogAlert("请填写正确的手机号码");
			return;
		}
	}
	
	var dataobject = new Object();
	dataobject.contactId = contactId;
	dataobject.phone = phone;
	dataobject.note = note;
	dataobject.address = address;
	dataobject.mobile = mobile;
	dataobject.needPay = needPay;
	dataobject.productType = productType;
	dataobject.griddata = griddata;
    $.ajax({
		url:basePath+"erp/buyReturnOrder/saveOrder",
		type:"post",
		datatype:"json",
//		contentType: "application/json;charset=utf-8",
//		data:{"contactId":contactId,"phone":phone,"note":note,"address":address,"mobile":mobile,"griddata":griddata},
		data:dataobject,//,"contactId="+contactId+"&phone="+phone+"&note="+note+"&address="+address+"&mobile="+mobile+"&griddata="+griddata,
		success:function(data){
			if(data.message=="success"){
				window.location=basePath+"erp/toBuyReturnOrder";
			}else{
				fDialogAlert("保存失败");
			}
		}
    });
}
