$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
	_title: function(title) {
		var $title = this.options.title || '&nbsp;'
		if( ("title_html" in this.options) && this.options.title_html == true )
			title.html($title);
		else title.text($title);
	}
}));

jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"erp/supplierInfo/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: 'auto',//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '名称', '地址', '电话', '手机号', '电子邮箱','邮编','联系人','QQ','传真','税号','开户行','账号','发票邮寄地址','备注','创建时间','修改时间'],//列显示名称
		colModel:[
			{name:'contactId',index:'contactId', width:'5%', editable:false,hidden:true,key:true},
			{name:'name',index:'name', width:'10%', formoptions: {colpos: 1, rowpos: 1},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{required:true}},
			{name:'address',index:'address', width:'20%', formoptions: {colpos: 1, rowpos: 2},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{required:true}},
			{name:'phone',index:'phone', width:'8%', formoptions: {colpos: 1, rowpos: 3},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{custom:true,custom_func:function(value,colname){
				if(value!=null&&value!=""){
					var reg=RegExp("^([0-9]{3,4}-)?[0-9]{7,8}$");
					if(reg.test(value)==true)
						return [true];
					else
						return [false,"请输入正确的电话号码"];
				}else{
					return [true];
				}
			}}},
			{name:'mobile',index:'mobile', width:'8%', formoptions: {colpos: 1, rowpos: 4},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{custom:true,custom_func:function(value,colname){
				if(value!=null&&value!=""){
					var reg=RegExp("^[0-9]{11}$");
					if(reg.test(value)==true)
						return [true];
					else
						return [false,"请输入正确的手机号码"];
				}else{
					return [true];
				}
			}}},
			{name:'email',index:'email', width:'8%', formoptions: {colpos: 1, rowpos: 5},editable:true, searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'zipcode',index:'zipcode', width:'8%', formoptions: {colpos: 1, rowpos: 6},editable:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'contactPerson',index:'contactPerson', width:'8%',search:false,formoptions: {colpos: 1, rowpos: 7},hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'qq',index:'qq', width:'8%', search:false,formoptions: {colpos: 2, rowpos: 1},hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'fax',index:'fax', width:'8%',search:false,formoptions: {colpos: 2, rowpos: 2}, hidden:true,editable:true,editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'taxNumber',index:'taxNumber', width:'8%',search:false,formoptions: {colpos: 2, rowpos: 3}, hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'openBank',index:'openBank', width:'8%', search:false,formoptions: {colpos: 2, rowpos: 4},hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'account',index:'account', width:'8%', search:false,formoptions: {colpos: 2, rowpos: 5},hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'invoiceAddress',index:'invoiceAddress', search:false,formoptions: {colpos: 2, rowpos: 6},hidden:true,width:'8%', editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'note',index:'note', width:'8%', search:false,formoptions: {colpos: 2, rowpos: 7},hidden:true,editable:true, editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],searchhidden:true}},
			{name:'createTime',index:'createTime', width:'16%',search:false, hidden:true,editable:false, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en'],
				searchhidden:true
			}},
			{name:'updateTime',index:'updateTime', width:'16%',search:false, hidden:true,editable:false, searchoptions:{
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
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "供应商信息管理列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"erp/supplierInfo/oper"//对表格增删改操作提交的url
	});
	
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	
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
			edit: false,
			editicon : 'icon-pencil blue',
			add: false,
			addicon : 'icon-plus-sign purple',
			del: false,
			delicon : 'icon-trash red',
			search: checkedButton('supplierSearch'),
			searchicon : 'icon-search orange',
			refresh: true,
			refreshicon : 'icon-refresh green',
			view: true,
			viewicon : 'icon-zoom-in grey',
		},
		{
			//edit record form
			closeAfterEdit: true,
			recreateForm: true,
			width: 620,
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
			width: 620,
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
			width: 620,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			}
		}
	)
	
	$(function(){
		if(checkedButton('supplierImport')){
			jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
				caption:"", 
				buttonicon:"icon-cloud-upload purple", 
				onClickButton:function(){
					$( "#importExcelDiv" ).removeClass('hide').dialog({
						resizable: false,
						modal: true,
						title: "<div class='widget-header'><h4 class='smaller'>上传excel文件</h4></div>",
						title_html: true,
						width:600,
						buttons: [
							{
								html: "<i class='icon-save bigger-110'></i>提交",
								"class" : "btn btn-primary btn-xs",
								click: function() {
									//document.getElementById("importExcelForm").submit();
									$('#importExcelForm').ajaxSubmit({
										url: basePath+"erp/supplierInfo/importExcel",
										type:"post",
										dataType:'json',
										iframe: true,
										success:function(data){
											if(data.message=="success"){
												fDialogAlert("导入成功");
												jQuery("#grid-table").jqGrid().trigger("reloadGrid");
											}else{
												fDialogAlert("导入失败，请联系管理员");
											}
										},
										error: function(arg1, arg2, ex) {
											fDialogAlert("导入失败，请联系管理员");
										}
									});

									$( this ).dialog( "close" );
								}
							}
							,
							{
								html: "<i class='icon-remove bigger-110'></i>取消",
								"class" : "btn btn-xs",
								click: function() {
									$( this ).dialog( "close" );
								}
							}
						]
					});
				}, 
				position: "last", 
				title:"导入excel", 
				cursor: "pointer",
				id:"importExcel"
			});
		}
		
		jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
			caption:"", 
			buttonicon:"icon-inbox purple", 
			onClickButton:function(){
				var postData=jQuery("#grid-table").jqGrid().getGridParam("postData");
				postData = JSON.stringify(postData);
				window.location = basePath + "erp/supplierInfo/getListAllExport?postData="+postData;
			}, 
			position: "last", 
			title:"导出excel", 
			cursor: "pointer",
			id:"exportExcel"
		});
		
		if(checkedButton('supplierDel')){
			jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
				caption:"", 
				buttonicon:"icon-trash red", 
				onClickButton:function(){
					var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
					if(myid==null||myid==""){
						fDialogAlert("请选择一行记录");
						return;
					}
					$.ajax({
						url:basePath+"erp/supplierInfo/del",
						type:"post",
						datatype:"json",
						data: {"contactId":myid},
						success:function(data){
							if(data.message=="success"){
								fDialogAlert("删除成功");
								jQuery("#grid-table").jqGrid().trigger("reloadGrid");
							}else{
								if(data.message=="error"){
									fDialogAlert("删除失败");
								}else{
									fDialogAlert(data.message);
								}
							}
						}
				    });
				}, 
				position: "first", 
				title:"删除供应商", 
				cursor: "pointer",
				id:"delButton"
			});
		}
		
		if(checkedButton('supplierEdit')){
			jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
				caption:"", 
				buttonicon:"icon-pencil blue", 
				onClickButton:function(){
					var myid=$("#grid-table").jqGrid("getGridParam",'selrow');
					if(myid==null||myid==""){
						fDialogAlert("请选择一行记录");
						return;
					}
					window.location=basePath+"erp/supplierInfo/toEditContact?contactId="+myid;
				}, 
				position: "first", 
				title:"修改供应商", 
				cursor: "pointer",
				id:"editButton"
			});
		}
		
		if(checkedButton('supplierAdd')){
			jQuery("#grid-table").jqGrid('navGrid',"#grid-pager").jqGrid('navButtonAdd',"#grid-pager",{
				caption:"", 
				buttonicon:"icon-plus-sign green", 
				onClickButton:function(){
					window.location=basePath+"erp/supplierInfo/toAddContact";
				}, 
				position: "first", 
				title:"添加供应商", 
				cursor: "pointer",
				id:"addButton"
			});
		}
		
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

$('#file').ace_file_input({
	style:'well',
	btn_choose:'将文件拖到此处或单击选择（请上传xls或xlsx格式的文件）',
	btn_change:null,
	no_icon:'icon-cloud-upload',
	droppable:true,
	thumbnail:'small'//large | fit
	//,icon_remove:null//set null, to hide remove/reset button
	/**,before_change:function(files, dropped) {
		//Check an example below
		//or examples/file-upload.html
		return true;
	}*/
	/**,before_remove : function() {
		return true;
	}*/
	,
	preview_error : function(filename, error_code) {
		//name of the file that failed
		//error_code values
		//1 = 'FILE_LOAD_FAILED',
		//2 = 'IMAGE_LOAD_FAILED',
		//3 = 'THUMBNAIL_FAILED'
		//alert(error_code);
	}

}).on('change', function(){
	//console.log($(this).data('ace_input_files'));
	//console.log($(this).data('ace_input_method'));
});