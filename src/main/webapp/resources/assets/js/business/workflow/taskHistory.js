function formatSeconds(value) {
    var theTime = parseInt(value/1000);// 秒
    var theTime1 = 0;// 分
    var theTime2 = 0;// 小时
    var theTime3 = 0;// 天
    if(theTime > 60) {
        theTime1 = parseInt(theTime/60);
        theTime = parseInt(theTime%60);
        if(theTime1 > 60) {
        	theTime2 = parseInt(theTime1/60);
        	theTime1 = parseInt(theTime1%60);
        }
        if(theTime2 > 24) {
        	theTime3 = parseInt(theTime2/24);
        	theTime2 = parseInt(theTime2%24);
        }
    }
    var result = ""+parseInt(theTime)+"秒";
    if(theTime1 > 0) {
    	result = ""+parseInt(theTime1)+"分"+result;
    }
    if(theTime2 > 0) {
    	result = ""+parseInt(theTime2)+"小时"+result;
    }
    if(theTime3 > 0) {
    	result = ""+parseInt(theTime3)+"天"+result;
    }
    return result;
}


jQuery(function($) {
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	jQuery(grid_selector).jqGrid({
		url: basePath+"erp/processTaskHistoryGrid",
		mtype:"POST",
		datatype: "json",
		height: "auto",
		colNames:[ '任务编号', '流程名称', '任务单号','任务名称','办理开始时间','办理结束时间','办理用时','办理人','办理人电话','操作'],
		colModel:[
			{name:'id',key:true,index:'id', width:60, editable: false,viewable:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'processDefinition.name', width:60, editable: true,stype:'text',searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'processInstance.businessKey',width:60, editable: false, viewable:true,searchoptions:{sopt:['eq','ne','cn','nc']},
				formatter:function(cellvalue, options, rowObject){
					var orderId = cellvalue.split("\.")[1];
					var ordernumber = cellvalue.split("\.")[2];
					return "<a class=\"red\"  href=\""+basePath+"erp/viewTaskHistoryForm?taskId="+rowObject.id+"\" title=\"查看任务单号信息\">"+ordernumber+"</a>";
					 
				}
			},
			{name:'name',width:60, editable: false, viewable:true,searchoptions:{sopt:['eq','ne','cn','nc']}},
			{name:'startTime',width:60, editable: false, viewable:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']}
			},
			{name:'endTime',width:60, editable: false, viewable:true,searchoptions:{
				dataInit : function (elem){
					$(elem).datetimepicker({timeFormat: "HH:mm:ss",dateFormat: "yy-mm-dd" , autoclose:true});
				},
				sopt:['bw','ew','bn','en']}
			},
			{name:'durationInMillis',width:60, editable: false, search:false, viewable:true,
				formatter:function(cellvalue, options, rowObject){
					return formatSeconds(cellvalue);
				}
			},
			{name:'assignee.name',width:60, editable: false, search:false, viewable:true},
			{name:'assignee.phone',width:60, editable: false, search:false, viewable:true},
			{name:'myac',index:'操作', width:80, fixed:true, sortable:false, resize:false,search:false,viewable:false,
				formatter: function (cellvalue, options, rowObject) {
					var buttons =  "<a class=\"icon-edit red\"  href=\""+basePath+"erp/viewTaskHistoryForm?taskId="+rowObject.id+"\" title=\"查看任务信息\"></a>";
					buttons+="&nbsp;" + "<a class=\"icon-folder-open blue\" href=\""+basePath+"erp/viewCurrentImage?historyTaskId=true&taskId="+rowObject.id+"\" title=\"查看流程图\"></a>";
					return  buttons;
				}
			},
		], 
		//sortname: 'depName',默认的排序列。可以是列名称或者是一个数字，这个参数会被提交到后台
		viewrecords : true,
		rowNum:10,
		//rowList:[10,20,30],
		pager : pager_selector,
		altRows: true,
		//toppager: true,
		multiselect: true,
		//multikey: "ctrlKey",
        multiboxonly: true,
        loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
			}, 0);
		},//当从服务器返回响应时执行
		caption: "办理历史任务信息列表",
		autowidth: true
	});
	//jQuery(grid_selector).jqGrid('filterToolbar',{searchOperators : true});
	$(grid_selector).closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });

	//navButtons
	jQuery(grid_selector).jqGrid('navGrid', pager_selector,
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
		},{},{},{},{
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
		},{}
	);
	
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
	
	function style_search_form(form) {
		var dialog = form.closest('.ui-jqdialog');
		var buttons = dialog.find('.EditTable')
		buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
		buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
		buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
	}
	
	function style_search_filters(form) {
		form.find('.delete-rule').val('X');
		form.find('.add-rule').addClass('btn btn-xs btn-primary');
		form.find('.add-group').addClass('btn btn-xs btn-success');
		form.find('.delete-group').addClass('btn btn-xs btn-danger');
	}
});