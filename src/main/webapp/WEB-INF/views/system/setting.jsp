<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/jquery-ui-timepicker-addon.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/assets/css/ui.jqgrid.css" />
		<jsp:include page="../script.jsp"></jsp:include>
	</head>

	<body>
		<div class="page-content">
			<div class="row">
				<div class="alert alert-info">
					<i class="icon-hand-right"></i>
					在此你可以对系统邮件、订单过期等内容进行设置！
					<button class="close" data-dismiss="alert">
						<i class="icon-remove"></i>
					</button>
				</div>
			</div>
			<form class="form-horizontal" role="form" id="mail">
				<div class="row" id="addEditDiv">
					<div class="col-xs-12">
					<h3 class="header smaller lighter blue" id="andEditText">系统邮件发送信息设置</h3>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 邮件服务器 </label>
							<div class="col-sm-9">
								<input type="text" name="host" id="host" placeholder="邮件服务器地址" class="col-xs-10 col-sm-8" value="${host }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 发件人地址</label>
							<div class="col-sm-9">
								<input type="text" name="formName" id="formName" placeholder="邮件发送人邮件地址" class="col-xs-10 col-sm-8" value="${formName }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 发件人密码</label>
							<div class="col-sm-9">
								<input type="text" name="formPassword" id="formPassword" placeholder="邮件发送人邮件密码" class="col-xs-10 col-sm-8" value="${formPassword }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 收件人地址</label>
							<div class="col-sm-9">
								<input type="text" name="replayAddress" id="replayAddress" placeholder="接收系统发送邮件的回复地址" class="col-xs-10 col-sm-8" value="${replayAddress }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="center ">
							<div>
								<button class="btn btn-info" type="button" onclick="mailSubmit();">
									<i class="icon-ok bigger-50"></i>
									提交
								</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset">
									<i class="icon-minus bigger-50"></i>
									取消
								</button>
							</div>
						</div>
						<h3 class="header smaller lighter blue"></h3>
					</div>
				</div>
			</form>
			
			<form class="form-horizontal" role="form" id="remind">
				<div class="row" id="addEditDiv">
					<div class="col-xs-12">
					<h3 class="header smaller lighter blue" id="andEditText">订单过期提醒设置</h3>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 销售订单（时间段）</label>
							<div class="col-sm-9">
								<input type="text" name="sellTimeSolt" id="sellTimeSolt" placeholder="提醒时间段，如：8-23(早上8点到晚上12点)" class="col-xs-10 col-sm-8" value="${sellTimeSolt }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 销售订单（间隔）</label>
							<div class="col-sm-9">
								<input type="text" name="sellInterval" id="sellInterval" placeholder="提醒间隔，如：10(每10分钟提醒一次)" class="col-xs-10 col-sm-8" value="${sellInterval }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 采购订单（时间段）</label>
							<div class="col-sm-9">
								<input type="text" name="buyTomeSolt" id="buyTomeSolt" placeholder="提醒时间段，如：8-23(早上8点到晚上12点)" class="col-xs-10 col-sm-8" value="${buyTomeSolt }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 采购订单（间隔）</label>
							<div class="col-sm-9">
								<input type="text" name="buyInterval" id="buyInterval" placeholder="提醒间隔，如：10(每10分钟提醒一次)" class="col-xs-10 col-sm-8" value="${buyInterval }"/>
							</div>
						</div>
						<div class="space-4"></div>
						<div class="center ">
							<div>
								<button class="btn btn-info" type="button" onclick="remindSubmit();">
									<i class="icon-ok bigger-50"></i>
									提交
								</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset">
									<i class="icon-minus bigger-50"></i>
									取消
								</button>
							</div>
						</div>
						<h3 class="header smaller lighter blue"></h3>
					</div>
				</div>
			</form>
		</div><!-- /.page-content -->
		<script src="<%=basePath %>resources/assets/js/business/setting.js"></script>
	</body>
</html>