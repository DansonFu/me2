package com.lettucetech.me2.common.constant;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lettucetech.me2.common.utils.JsonUtil;

public class CodeConstants {

	private static final Map<String, Map<String, String>> ALL_CODE_MAP = new HashMap<String, Map<String, String>>();

	// 情报板数据来源
	private static final Map<String, String> BOARD_DATA_SOURSE = new LinkedHashMap<String, String>();
	static {
		BOARD_DATA_SOURSE.put("1", "人工录入");
		BOARD_DATA_SOURSE.put("2", "数据同步");
	}

	// 情报板文字字体
	private static final Map<String, String> BOARD_TEXT_FONT = new LinkedHashMap<String, String>();
	static {
		BOARD_TEXT_FONT.put("1", "黑体");
		BOARD_TEXT_FONT.put("2", "楷体");
		BOARD_TEXT_FONT.put("3", "宋体");
		BOARD_TEXT_FONT.put("4", "仿宋");
	}

	// 情报板文字大小
	private static final Map<String, String> BOARD_TEXT_SIZE = new LinkedHashMap<String, String>();
	static {
		BOARD_TEXT_SIZE.put("1", "16点阵");
		BOARD_TEXT_SIZE.put("2", "24点阵");
		BOARD_TEXT_SIZE.put("3", "32点阵");
		BOARD_TEXT_SIZE.put("4", "48点阵");
	}

	// 情报板文字颜色
	private static final Map<String, String> BOARD_TEXT_COLOR = new LinkedHashMap<String, String>();
	static {
		BOARD_TEXT_COLOR.put("1", "红");
		BOARD_TEXT_COLOR.put("2", "黄");
		BOARD_TEXT_COLOR.put("3", "绿");
	}

	// 情报板出字方式
	private static final Map<String, String> BOARD_SHOW_MODE = new LinkedHashMap<String, String>();
	static {
		BOARD_SHOW_MODE.put("1", "立即");
		BOARD_SHOW_MODE.put("2", "闪烁");
		BOARD_SHOW_MODE.put("3", "左移");
		BOARD_SHOW_MODE.put("4", "右移");
	}

	// 情报板图标类型
	private static final Map<String, String> BOARD_ICO_TYPE = new LinkedHashMap<String, String>();
	static {
		BOARD_ICO_TYPE.put("1", "人工录入");
		BOARD_ICO_TYPE.put("2", "数据同步");
	}

	// 情报板内容类型
	private static final Map<String, String> BOARD_MSG_TYPE = new LinkedHashMap<String, String>();
	static {
		BOARD_MSG_TYPE.put("1", "事故");
		BOARD_MSG_TYPE.put("2", "路况");
		BOARD_MSG_TYPE.put("3", "气象");
		BOARD_MSG_TYPE.put("4", "提示");
	}

	// 是否禁用菜单或页面
	private static final Map<String, String> IS_DISABLE = new LinkedHashMap<String, String>();
	static {
		IS_DISABLE.put("不禁用", "0");
		IS_DISABLE.put("禁用", "1");
	}

	/**
	 * 初始化所有Code，此方法必须放在最后
	 */
	static {
		CodeConstants codeConstants = new CodeConstants();
		Field[] fieldArray = CodeConstants.class.getDeclaredFields();

		try {
			for (Field field : fieldArray) {
				Object value = field.get(codeConstants);
				if (value instanceof Map) {
					ALL_CODE_MAP.put(field.getName(), (Map) value);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getCodeMap(String codeCategory) {
		return ALL_CODE_MAP.get(codeCategory);
	}

	public static String getCodeJson(String codeCategory) {
		return getCodeJson(codeCategory, "codeKey", "codeValue");
	}

	public static String getCodeJson(String codeCategory, String value, String display) {
		Map<String, String> codeMap = ALL_CODE_MAP.get(codeCategory);
		List<Map<String, String>> codeList = new ArrayList<Map<String, String>>();
		Set<Entry<String, String>> entrySet = codeMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			Map<String, String> code = new HashMap<String, String>();
			code.put("codeKey", entry.getKey().toString());
			code.put("codeValue", entry.getValue().toString());
			codeList.add(code);
		}
		return JsonUtil.Encode(codeList);
	}

	public static void main(String[] args) {
		System.out.println(getCodeJson("BOARD_DATA_SOURSE"));
	}
}
