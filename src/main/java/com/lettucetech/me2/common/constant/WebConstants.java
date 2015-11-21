package com.lettucetech.me2.common.constant;

/**
 * 系统共通常量
 * 
 * @author 刘洋
 */
public interface WebConstants {
	/** 超时提醒 */
	public static final String TIME_OUT = "{\"timeout\":true,\"msg\":\"登录超时，请重新登录！\"}";

	/** 保存session中的用户信息key */
	public static final String CURRENT_USER = "GSGL_CURRENT_USER";

	/** 保存session中的用户登陆类型 */
	public static final String LOGIN_USER_TYPE = "LOGIN_USER_TYPE";

	/** 保存session中的SSO Token Key */
	public static final String SSO_TOKEN = "sso_token";

	/** 保存session中的xjjSSO Token Key */
	public static final String XJJ_SSO_TOKEN = "xjj_sso_token";

	/** 用户类型 */
	public interface USER_TYPE {
		/** 0:系统内 */
		Short IN_SYS = 0;

		/** 1:系统外 */
		Short OUT_SYS = 1;
	}

	/** 功能菜单主键 */
	public interface MODULE_SID {

		// 系统管理功能菜单
		/** 系統管理 */
		Long SYS = 700L;

		/** 用户权限管理 */
		Long SYS_USER = 701L;

		/** 角色管理 */
		Long SYS_ROLE = 702L;

		/** 数据字典管理 */
		Long SYS_CODE = 703L;

		/** 部门管理 */
		Long SYS_DEP = 704L;

		/** GIS图层元素管理 */
		Long SYS_GID_LAYER = 705L;

		/** 操作日志 */
		Long SYS_BIZ_LOG = 706L;
		/** 监控分中心 */
		Long SYS_RMC = 707L;
		/** 监控组 */
		Long SYS_MONITOR_GROUP = 708L;
		/** 参数管理 */
		Long SYS_CONFIGURATION = 709L;

		// 报表统计功能菜单
		/** 报表统计 */
		Long REPORT = 800L;

		// 视频监控
		/** 实时视频 */
		Long VIDEO_REALTIME = 101L;
		/** 历史视频 */
		Long VIDEO_HISTORY = 102L;
		/** GIS视频 */
		Long VIDEO_GIS = 103L;

		// 事件管理
		/** 实时事件 */
		Long EVENT_REALTIME = 210L;
		/** 历史事件 */
		Long EVENT_HISTORY = 220L;

		/** 历史事件 */
		Long MSGBOARD_INOUT = 401L;
		/** 历史事件 */
		Long MSGBOARD_AUDIT = 402L;
		/** 历史事件 */
		Long MSGBOARD_QUERY_REALTIME = 404L;
		/** 历史事件 */
		Long MSGBOARD_QUERY_HISTORY = 405L;

		// 设备监控
		Long DEVICE_STATUS = 600L;
	}

	/** 附件类型 */
	public interface ATTACHMENT_TYPE {

		/** 1：文档 */
		String DOCUMENT = "1";

		/** 2：音频文件 */
		String MUSIC = "2";

		/** 3：视频文件 */
		String MOVIE = "3";

		/** 4：图片 */
		String IMAGE = "4";

		/** 9：其他 */
		String OTHER = "9";
	}

	/** 设备状态 */
	public interface DEV_STATUS {
		/** 未启用 0 */
		short STOP = 0;

		/** 已启用 1 */
		short RUN = 1;
	}

	/**
	 * 消息代码
	 */
	public interface MSG_CD {
		/* 提示消息 */
		/** info.insert.success=创建成功！ */
		String INFO_INSERT_SUCCESS = "info.insert.success";

		/** info.update.success=更新成功！ */
		String INFO_UPDATE_SUCCESS = "info.update.success";

		/** info.publish.success=发布成功！ */
		String INFO_PUBLISH_SUCCESS = "info.publish.success";

		/** info.delete.success=删除成功！ */
		String INFO_DELETE_SUCCESS = "info.delete.success";

		/** info.relation.success=关联操作成功！ */
		String INFO_RELATION_SUCCESS = "info.relation.success";

		/** info.relation.success=取消关联操作成功！ */
		String INFO_UNRELATION_SUCCESS = "info.unrelation.success";

		/** info.approve.success=审核成功！ */
		String INFO_APPROVE_SUCCESS = "info.approve.success";

		/** info.message.success=发送成功！ */
		String INFO_MESSAGE_SUCCESS = "info.message.success";

		/** info.inventory.success=盘点成功！ */
		String INFO_INVENTORY_SUCCESS = "info.inventory.success";

		/** info.in.success=入库成功！ */
		String INFO_IN_SUCCESS = "info.in.success";

		/** info.out.success=出库成功！ */
		String INFO_OUT_SUCCESS = "info.out.success";

		/** info.copy.success=复制成功！ */
		String INFO_COPY_SUCCESS = "info.copy.success";

		/* 警告消息 */

		/* 错误消息 */
		/** error.insert.failure=创建失败！ */
		String ERROR_INSERT_FAILURE = "error.insert.failure";

		/** error.message.failure=发送失败！ */
		String ERROR_MESSAGE_FAILURE = "error.message.failure";

		/** error.update.failure=更新失败，数据已被更新或删除！ */
		String ERROR_UPDATE_FAILURE = "error.update.failure";

		/** error.publish.success=发布失败！ */
		String ERROR_PUBLISH_FAILURE = "error.publish.failure";

		/** error.delete.failure=删除失败，数据已被更新或删除！ */
		String ERROR_DELETE_FAILURE = "error.delete.failure";

		/** error.approve.failure=审核失败！ */
		String ERROR_APPROVE_FAILURE = "error.approve.failure";

		/** error.inventory.failure=盘点失败！ */
		String ERROR_INVENTORY_FAILURE = "error.inventory.failure";

		/** error.in.failure=入库失败！ */
		String ERROR_IN_FAILURE = "error.in.failure";

		/** error.out.failure=出库失败！ */
		String ERROR_OUT_FAILURE = "error.out.failure";

		/** error.copy.failure=复制失败！ */
		String ERROR_COPY_FAILURE = "error.copy.failure";

		/** error.relation.failure=关联操作失败，数据已被更新或删除！ */
		String ERROR_RELATION_FAILURE = "error.relation.failure";

		/** error.data.exist={0}已经存在，请重新填写！ */
		String ERROR_DATA_EXIST = "error.data.exist";

		/** error.data.relation={0}，不能进行删除！ */
		String ERROR_DATA_RELATION = "error.data.relation";

		/** error.file.oversize=您选择的文件过大，请重新选择！ */
		String ERROR_FILE_OVERSIZE = "error.file.oversize";

		/** error.plan.published=该名称预案已经发布！ */
		String ERROR_PLAN_PUBLISHED = "error.plan.published";

		/** error.plan.published=该预案已经停用！ */
		String ERROR_PLAN_DISABLED = "error.plan.disabled";

		/** error.board.miss.template=该设备还未配置模板！ */
		String ERROR_BOARD_MISS_TEMPLATE = "error.board.miss.template";

		/** error.relation.failure=发生异常，操作失败！ */
		String ERROR_CONTROLLER_EXCEPTION = "error.controller.exception";
	}

	/** 文件上传类型 */
	public interface FILE_UPLOAD_TYPE {
		/** 业务 */
		String BIZ = "biz";
		/** 消息 */
		String MSG = "msg";
	}

	/** 情报板消息来源 */
	public interface BOARD_MSG_SOURCE_FLAG {
		/** 内部 */
		String INSIDE = "0";

		/** 应急 */
		String EMERGENCY = "1";

		/** 出行 */
		String TRIP = "2";
	}

	/** 情报板外部消息处理状态 */
	public interface BOARD_OUT_MSG_STATUS {
		/** 待处理 */
		String INIT = "0";

		/** 已处理 */
		String DEAL = "1";

	}

	/** 情报板发布状态 */
	public interface BOARD_MSG_STATUS {
		/** 草稿 */
		String DRAFT = "0";

		/** 待审核 */
		String AFTER_SAVE = "1";

		/** 审核拒绝待修改 */
		String AUDIT_REJECT = "2";

		/** 待发送 */
		String AUDIT_PASS = "3";

		/** 已发送 */
		String SENT = "4";

		/** 外部数据未处理 */
		String OUTSIDE = "99";
	}

	/** 情报板消息审批结果 */
	public interface BOARD_MSG_AUDIT_RESULT {
		/** 初始 */
		String APPROVE = "1";

		/** 待审核 */
		String REJECT = "0";
	}

	/** 情报板单条消息状态 */
	public interface BOARD_MSG_SEND_STATUS {
		/** 初始 */
		String INIT = "0";

		/** 待发送 */
		String SENDING = "1";

		/** 已发送 */
		String SENT = "2";
	}

	/** 路况状态 */
	public interface ROAD_STATUS {
		/** 畅通 1 */
		String SMOOTH = "1";

		/** 缓行 2 */
		String AMBLE = "2";

		/** 拥堵 3 */
		String BLOCK = "3";
	}

	/** 设备安装方式 */
	public interface INSTALL_TYPE {
		/** 路边 0 */
		String ROAD_SIDE = "0";

		/** 中间隔离带 1 */
		String MEDIAN = "1";
	}

	/** 摄像头接入方式 */
	public interface ACCESS_MODE {
		/** 模拟 1 */
		String ANALOG = "1";

		/** IP 2 */
		String IP = "2";
	}

	/** 摄像头编码格式 */
	public interface CODING_FORMAT {
		/** H.264 0 */
		String H264 = "0";

		/** H.263 1 */
		String H263 = "1";

		/** MP4 2 */
		String MP4 = "2";

		/** MP2 3 */
		String MP2 = "3";
	}

	/** 摄像头像素 */
	public interface RESOLUTION {
		/** D1 0 */
		String D1 = "0";

		/** 720P 1 */
		String P720 = "1";

		/** 1080P 2 */
		String P1080 = "2";
	}

	/** 报表模板定义 */
	public interface ReportTmp {
		// 交通事件报表
		public interface TrafficEvent {
			// 事件分类报表
			String EVENT_TYPE = "te-event-type-report-tmp";
			// 事件等级报表
			String EVENT_LEVEL = "te-event-level-report-tmp";
			// 事件统计报表
			String EVENT_STATISTICS = "te-event-statistics-report-tmp";
			// 事件分类汇总报表
			String EVENT_TYPE_SUM = "te-event-type-sum-report-tmp";
			// 气象信息报表
			String WEATHER_INFO = "te-event-type-sum-report-tmp";
		}

		// 交通信息报表
		public interface TrafficInfo {
			// 气象信息报表
			String WEATHER_INFO = "ti-weather-info-report-tmp";
			// 平均车速日表
			String AVG_SPEED_DAY = "ti-speed-day-statistics-report-tmp";
			// 平均车速月表
			String AVG_SPEED_MONTH = "ti-speed-month-statistics-report-tmp";
			// 平均车速季度表
			String AVG_SPEED_QUARTER = "ti-speed-quarter-statistics-report-tmp";
			// 平均车速年表
			String AVG_SPEED_YEAR = "ti-speed-year-statistics-report-tmp";
			// 车道占有率年表
			String RATIO_YEAR = "ti-ratio-year-report-tmp";
			// 车道占有率统计月表
			String RATIO_MONTH31 = "ti-ratio-month31-report-tmp";
			// 车道占有率统计月表
			String RATIO_MONTH30 = "ti-ratio-month30-report-tmp";
			// 车道占有率统计月表
			String RATIO_MONTH29 = "ti-ratio-month29-report-tmp";
			// 车道占有率计月表
			String RATIO_MONTH28 = "ti-ratio-month28-report-tmp";
			// 车道占有率统计日表
			String RATIO_DAY = "ti-ratio-day-report-tmp";
			// 车道占有率统计季表
			String RATIO_QUARTER = "ti-ratio-quarter-report-tmp";
			// 车流量年表
			String AVG_FLOW_YEAR = "ti-flow-year-statistics-report-tmp";
			// 车流量季度表
			String AVG_FLOW_QUARTER = "ti-flow-quarter-statistics-report-tmp";
			// 车流量月表
			String AVG_FLOW_MOUTH = "ti-flow-mouth-statistics-report-tmp";
			// 车流量日表
			String AVG_FLOW_DAY = "ti-flow-day-statistics-report-tmp";
			// 交通实时信息查询表
			String REAL_QUERY = "ti-real-query-report-tmp";
		}

		// 交通事件报表
		public interface TrafficStation {
			// 国家高速公路交通量报表
			String NATIONAL_TRAFFIC = "ts-national-highway-traffic-report-tmp";
			// 小时交通量记录及日交通量统计报表
			String HOUR_TRAFFIC_RECORDS = "ts-hour-traffic-records-report-tmp";
			// 小时交通量记录及日交通量统计报表
			String ROUTE_AVG_FLOW_SPEED = "ts-route-speed-flow-report-tmp";
			// 路段平均日交通量统计报表
			String SECTION_TRAFFIC_FLUX = "ts-section-day-avg-flux-report-tmp";
		}
	}

	/** 统计类型 */
	public interface STATISTICS_TYPE {
		/** 日表 */
		String DAY = "day";

		/** 月表 */
		String MONTH = "month";

		/** 季度表 */
		String QUARTER = "quarter";

		/** 年表 */
		String YEAR = "year";
	}

	/** 系统参数类型 */
	public interface CONFIG_CATEGORY {
		/** 严重事件类型 */
		String SERIOUS_EVENT_TYPE = "SERIOUS_EVENT_TYPE";
	}
}
