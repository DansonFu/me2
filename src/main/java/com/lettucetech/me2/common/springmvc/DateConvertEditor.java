package com.lettucetech.me2.common.springmvc;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.lettucetech.me2.common.utils.StringUtil;

/**
 * spring中日期转换
 * 
 * <pre>
 * &#064;InitBinder
 * public void initBinder(WebDataBinder binder) {
 * 	binder.registerCustomEditor(Date.class, new DateConvertEditor());
 * 	// binder.registerCustomEditor(Date.class, new
 * 	// DateConvertEditor(&quot;yyyy-MM-dd&quot;));
 * 	binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
 * }
 * </pre>
 * 
 * 
 * @author 刘洋
 */
public class DateConvertEditor extends PropertyEditorSupport {
	private SimpleDateFormat sdf;

	public DateConvertEditor() {
		this.sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		this.sdf.applyPattern(StringUtil.DF_YMD_24);
		this.sdf.setLenient(false);
	}

	public DateConvertEditor(String format) {
		this.sdf = (SimpleDateFormat) DateFormat.getDateInstance();
		this.sdf.applyPattern(format);
		this.sdf.setLenient(false);
	}

	/** Date -> String */
	@Override
	public String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return this.sdf.format(getValue());
	}

	/** String -> Date */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isEmpty(text)) {
			setValue("");
		} else {
			try {
				if (text.length() == 10) {
					this.sdf.applyPattern(StringUtil.DF_YMD);
					setValue(this.sdf.parse(text));
				} else {
					setValue(this.sdf.parse(text));
				}
			} catch (ParseException e) {
				throw new IllegalArgumentException("不能被转换的日期字符串，请检查！", e);
			}
		}
	}
}
