var icons = "icon-key,icon-th,icon-user,icon-th-list,icon-asterisk,icon-book,icon-briefcase,icon-desktop,icon-long-arrow-left,icon-long-arrow-right,icon-truck,icon-money,icon-cog,icon-edit,icon-shopping-cart orange,icon-shopping-cart blue";
function getIcons(){
	var iconss = icons.split(",");
	var res= "";
	for(var i =0; i<iconss.length; i++){
		var tempIcon = iconss[i];
		res+= tempIcon+":<span class='"+tempIcon+"'/> "+tempIcon+";";
	} 
	res = res.substring(0, res.length - 1);
	return res;
}
	
	
	
	
	
		//"icon-th-list:<span class='icon-th-list'/> icon-th-list"


jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";

	jQuery(grid_selector).jqGrid({
		url: basePath+"admin/getPermission",
		mtype:"POST",
		datatype: "json",
		height: 394,
		colNames:[ '编号', '菜单名称', '菜单编码','菜单描述', '图标','类型', '排序','创建时间'/*, '操作'*/],
		colModel:[
			{name:'mId', key:true, index:'mId', width:80, editable: false, hidden:true,search:false, viewable:true},
			{name:'name',index:'name', width:80, editable: true,search:true,stype:'text',searchoptions:{sopt:['cn']}},
			{name:'code',index:'code', width:50, editable: true,search:true,stype:'text',searchoptions:{sopt:['cn']}},
			{name:'note',index:'description', width:80, editable: true,edittype:"textarea", search:false},
			{name:'icons',index:'icons', width:80, editable: true,edittype:'select',editoptions:{value:getIcons()}, formatter:'select'},
			{name:'type',index:'note', width:50, editable: true,search:true,edittype:'select',editoptions:{
				value:"1:菜单;2:按钮;3:数据权限"
			},formatter:function(val){
				if(val==1){return "菜单"}
				if(val==2){return "按钮"}
				if(val==3){return "数据权限"}
			}},
			{name:'num',index:'num', width:20, editable: true,search:false,stype:'text',searchoptions:{sopt:['cn']}},
			{name:'addTime',index:'addTime', width:60, editable:false, searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				}
			}}/*,
			{name:'myac',index:'操作', width:80, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter:'actions', 
				formatoptions:{ 
					keys:true,
					delOptions:{recreateForm: true, beforeShowForm:beforeDeleteCallback},
					editformbutton:true, editOptions:{recreateForm: true, closeAfterEdit: true,beforeShowForm:beforeEditCallback}
				}
			},*/
		], 
		sortname: 'name',//默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
		viewrecords : true,
		altRows:true,//隔行变色
		pager : pager_selector,//定义翻页用的导航栏
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
		},
		editurl: basePath +"admin/editPermission",//nothing is saved
		caption: "菜单信息管理列表",
		autowidth: true,
		autoScroll: false,
		treeGrid:true,
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
			edit: checkedButton('permissionEdit'),
			editicon : 'icon-pencil blue',
			//edittext:'修改',
			add: checkedButton('permissionAdd'),
			addicon : 'icon-plus-sign purple',
			//addtext:'新增',
			del: checkedButton('permissionDel'),
			delicon : 'icon-trash red',
			search: false,
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