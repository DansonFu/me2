var selected = "";
jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	
	jQuery(grid_selector).jqGrid({
		url:basePath+"admin/rolePower/getList",//获取数据的地址
		mtype:"POST",
		datatype: "json",//从服务器端返回的数据类型
		height: "auto",//表格高度
		rownumbers:true,//显示行号
		colNames:['ID', '角色名', '说明'],//列显示名称
		colModel:[
			{name:'roleId',index:'roleId', width:'5%', editable:false,hidden:true,key:true},
			{name:'name',index:'name', width:'10%', editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']},editrules:{required:true}},
			{name:'detail',index:'detail', width:'30%', editable:true, edittype:'text', searchoptions:{sopt:['eq','ne','cn','nc']}},
		],//列属性
		viewrecords : true,//显示显示总记录数
		rowNum: "all",
		//rowNum:10,//设置初始显示记录条数
		//rowList:[10,20,30],//可选显示记录条数
		//pager : pager_selector,//定义翻页用的导航栏
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
		rowNum: -1,
		caption: "角色信息列表",//表格名称
		autowidth: true,//首次被创建时会根据父元素比例重新调整表格宽度
		//editurl:basePath+"admin/role/oper",//对表格增删改操作提交的url
		onSelectRow:function(rowid){
			//jQuery("#grid-table-2").jqGrid("setGridParam",{postData:{"selectedId":rowid}}).trigger("reloadGrid");
			//alert("rowid : " + rowid);
			jQuery("#grid-table-2").trigger("reloadGrid");
			selected = rowid;
		}
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

jQuery(function($) {
	var grid_selector = "#grid-table-2";
	var pager_selector = "#grid-pager-2";

	jQuery(grid_selector).jqGrid({
		url: basePath+"admin/rolePower/getPermission",
		mtype:"POST",
		datatype: "json",
		height: 300,
		minheight:600,
		rowNum:-1,
		colNames:[ '编号', '菜单名称', '菜单编码','菜单描述'],
		colModel:[
			{name:'mId', key:true, index:'mId', width:80, editable: false, hidden:true,search:false, viewable:true},
			{name:'name',index:'name', width:80, editable: true,search:true,stype:'text',searchoptions:{sopt:['cn']},
				formatter:function(cellvalue, options, rowObject){
					var spacewhile="";
					for(var lp=0; lp<rowObject.level;lp++){
						spacewhile+="   ";
					}
					return spacewhile+"|-"+cellvalue;
			}},
			{name:'code',index:'code', width:50, editable: true,search:true,stype:'text',searchoptions:{sopt:['cn']}},
			{name:'note',index:'description', width:80, editable: true,edittype:"textarea", search:false}
		], 
		sortname: 'name',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
		viewrecords : true,
		altRows:true,//隔行变色
		pager : pager_selector,//定义翻页用的导航栏
		pgbuttons:false,
		pginput:false,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,

		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				styleCheckbox(table);
				
				updateActionIcons(table);
				updatePagerIcons(table);
				enableTooltips(table);
			}, 0);
			if(selected!=""){
				$.ajax({
					async:true,
					cache:false,
					url:basePath+"/admin/rolePower/getPerSelected",
					type:"post",
					datatype:"json",
					data:{"selectedId":selected},
					success:function(data){
						var rows = data.rows;
						//alert("length : " + rows.length);
						$.each(rows, function (index, roleMenu) {
							jQuery("#grid-table-2").jqGrid('setSelection', roleMenu.mId);
						});
					}
				});
				
			}
			
		},
		//editurl: basePath +"admin/editPermission",//nothing is saved
		caption: "系统菜单信息列表",
		autowidth: true,
		autoScroll: false,
		treeGrid:false,
		treeGridModel:'adjacency',
		ExpandColumn:'name',
		ExpandColClick:true,
		treedatatype:'json',
		tree_root_level:0,
		treeReader:{
			level_field:'level',//层数
			parent_id_field:'pId',//父id
			leaf_field:'isLeaf',//是否为叶子节点
			expanded_field:"expanded",//父节点收起时其下的子节点是否一并收起
			loaded:'loaded'//为true时点击叶子节点不会加载数据
		},
		treeIcons:{
        	plus:'icon-folder-close',minus:'icon-folder-open',leaf:'icon-leaf'
        }
	});
	//jQuery(grid_selector).jqGrid('filterToolbar',{searchOperators : true});
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
	//enable search/filter toolbar
	//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

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
	jQuery(grid_selector).jqGrid('navGrid', pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'icon-pencil blue',
			//edittext:'修改',
			add: false,
			addicon : 'icon-plus-sign purple',
			//addtext:'新增',
			del: false,
			delicon : 'icon-trash red',
			search: false,
			searchicon : 'icon-search orange',
			refresh: false,
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
			afterSubmit : onEditafterSubmit
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
			afterSubmit:onDeleteAfterSubmit
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
			},
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
	
	jQuery("#grid-table-2").jqGrid('navGrid',"#grid-pager-2").jqGrid('navButtonAdd',"#grid-pager-2",{
		caption:"保存", 
		buttonicon:"icon-save blue", 
		onClickButton:function(){
			if(selected!=""){
				var selectedIds=$("#grid-table-2").jqGrid('getGridParam','selarrrow');
//				var checkedIds = [];
//				$.each(selectedIds, function(i, e) {
//					checkedIds.push(e);
//				});
//				console.log(selectedIds.join(","));
				$.ajax({
					async:true,
					cache:false,
					url:basePath+"admin/rolePower/savePerSelected",
					type:"post",
					datatype:"json",
					data:{"selectedIds":selectedIds.join(","),"selectedId":selected},
					success:function(data){
						if(data.message=="success"){
							fDialogAlert("修改成功");
						}else{
							fDialogAlert("修改失败");
						}
					}
				});
			}
		}, 
		position: "last", 
		title:"设置权限", 
		cursor: "pointer",
		id:"buttonId"
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
		
		form.find('#isHead').addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
		form.find('#description').addClass('autosize-transition form-control"');
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

	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
	function onEditafterSubmit(response, postdata){
		if(response.responseJSON.message=="success"){
			fDialogAlert("编辑成功");
			$(grid_selector).trigger("reloadGrid");
			return [true];
		}else{
			return [false,"编辑失败"];
		}
	}
	
	function onDeleteAfterSubmit(response, postdata){
		if(response.responseJSON.message=="success"){
			fDialogAlert("删除成功");
			$(grid_selector).trigger("reloadGrid");
			return [true];
		}else{
			return [false,"含有子节点信息，删除失败！"];
		}
	}
	
});