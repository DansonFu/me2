jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url:basePath+"admin/userInfo/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '账号',  '姓名', '所属部门', '电话','性别','qq','邮箱','地址','上级领导','状态','身份证号','密码','所属部门','岗位编号','上级领导','创建时间','修改时间','删除标记'/*,'操作'*/],//列显示名称
		colModel:[
			{name:'userId',index:'userId', width:'5%', editable:false,hidden:true,key:true},
			{name:'account',index:'account', width:'10%', formoptions: {colpos: 1, rowpos: 2},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{required:true}},
			{name:'name',index:'name', width:'10%', formoptions: {colpos: 1, rowpos: 3},editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{required:true}},
			{name:'department.depId',hidden:true, width:'10%',formoptions: {colpos: 2, rowpos: 3}, editable:true, viewable:false,edittype:'select',editrules:{edithidden:true},editoptions: {dataUrl: basePath+'admin/userInfo/getDepId'},searchoptions:{sopt:['eq','ne','cn','nc'],stype:'select',dataUrl: basePath+'admin/userInfo/getDepId'}},
			{name:'phone',index:'phone', width:'8%',formoptions: {colpos: 1, rowpos: 4}, editable:true, edittype:'text', hidden:true,editrules:{edithidden:true,required:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'sex',index:'sex', width:'8%', formoptions: {colpos: 2, rowpos: 4},editable:true, edittype:'select', editoptions:{value:"0:男;1:女"},hidden:true,stype:'select',editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc'],value:"0:男;1:女"}, formatter:'select'},
			{name:'qq',index:'qq', width:'8%',formoptions: {colpos: 1, rowpos: 5}, editable:true, edittype:'text', hidden:true,editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'email',index:'email', width:'8%', formoptions: {colpos: 1, rowpos: 6},editable:true, edittype:'text', hidden:false,editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'address',index:'address', width:'8%', formoptions: {colpos: 1, rowpos: 7},editable:true, edittype:'text', hidden:false,editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'leader.userId',hidden:true, width:'8%', formoptions: {colpos: 2, rowpos: 5},editable:true, viewable:false,edittype:'select',editrules:{edithidden:true}, editoptions: {dataUrl: basePath+'admin/userInfo/getLeader'},hidden:true,editrules:{edithidden:true},searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'status',index:'status', width:'8%', formoptions: {colpos: 2, rowpos: 6},editable:true, edittype:'select',editoptions:{value:"0:启用;1:禁用"},stype:'select', searchoptions:{sopt:['eq','ne'],value:"0:启用;1:禁用"}, formatter:'select'},
			{name:'idcard',index:'idcard', hidden:true, width:'8%', formoptions: {colpos: 2, rowpos: 2},editable:true, viewable:true,edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{edithidden:true,custom:true,custom_func:function(value,colname){
				if(value!=null&&value!=""){
					var reg=RegExp("^[0-9]{18}$");
					if(reg.test(value)==true)
						return [true];
					else
						return [false,"请输入正确的身份证号码"];
				}else{
					return [true];
				}
			}}},
			
			{name:'password',index:'password', width:'10%', editable:false, edittype:'text', hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']},formoptions: {colpos: 1, rowpos: 1}},
			{name:'department.depName',width:'8%', editable: false, search:false,formoptions: {colpos: 2, rowpos: 3}},
			{name:'postId',index:'postId', width:'10%', editable:false, hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']},formoptions: {colpos: 2, rowpos: 1}},
			{name:'leader.name',width:'8%', editable: false, search:false,formoptions: {colpos: 2, rowpos: 5}},
			
			{name:'createTime',index:'createTime', width:'16%', editable:false, hidden:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'updateTime',index:'updateTime', width:'16%', editable:false, hidden:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']
			}},
			{name:'deleteState',index:'deleteState', width:'8%', editable:false, edittype:'text', hidden:true,searchoptions:{sopt:['eq','ne','cn','nc']}}/*,
			{name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter:'actions', 
				formatoptions:{ 
					keys:true,
					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback, afterSubmit:onAfterSubmit},
					editformbutton:true, 
					editOptions:{width:620,closeAfterEdit: true, recreateForm: true, beforeShowForm:beforeEditCallback, afterSubmit : onEditafterSubmit,beforeSubmit:onEditBeforeSubmit}
				}
			}*/
		],//列属性
		viewrecords : true,//显示显示总记录数
		pgbuttons:true,	//是否显示分页按钮
		pginput:true,	//是否显示分页输入框
		rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
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
		caption: "用户信息管理列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		editurl:basePath+"admin/userInfo/oper"//对表格增删改操作提交的url
	});
	
	//jQuery(grid_selector).jqGrid('filterToolbar');//显示搜索栏
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
			edit: checkedButton('userEdit'),
			editicon : 'icon-pencil blue',
			add: checkedButton('userAdd'),
			addicon : 'icon-plus-sign purple',
			del: checkedButton('userDel'),
			delicon : 'icon-trash red',
			search: checkedButton('userSearch'),
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
			afterSubmit : onEditafterSubmit,
			beforeSubmit:onEditBeforeSubmit
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
			},
			beforeSubmit:function(postdata, formid) {
				var userExist=false;
				 $.ajax({
					 	async:false,
						url:basePath+"admin/userInfo/getUserByAccount",
						type:"post",
						datatype:"json",
						data: {"account":postdata.account},
						success:function(data){
							if(data.user!=null){
								userExist=true;
							}
						}
				    });
				 if(userExist==true){
					 return [false,"该账号已经存在"];
				 }else{
					 return [true];
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
	function onEditBeforeSubmit(postdata, formid) {
		if(postdata["grid-table_id"]!=postdata.leader){
			var rowData = $("#grid-table").jqGrid("getRowData",postdata['grid-table_id']);
			if(postdata.account==rowData.account){
				return [true];
			}
			var userExist=false;
			 $.ajax({
				 	async:false,
					url:basePath+"admin/userInfo/getUserByAccount",
					type:"post",
					datatype:"json",
					data: {"account":postdata.account},
					success:function(data){
						if(data.user!=null){
							userExist=true;
						}
					}
			    });
			 if(userExist==true){
				 return [false,"该账号已经存在"];
			 }else{
				 return [true];
			 }
		}else{
			return[false,"不能选择自己作为上级领导"];
		}
	}
});

function writeObj(obj){ 
    var description = ""; 
    for(var i in obj){   
        var property=obj[i];   
        description+=i+" = "+property+"\n";  
    }   
    fDialogAlert(description); 
} 