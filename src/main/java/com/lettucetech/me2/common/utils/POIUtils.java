package com.lettucetech.me2.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI excel 工具类
 * 
 * @author 戢培贵
 * 
 */
public class POIUtils {
	String filePath;

	// 声明一个xls的excel工作表
	HSSFWorkbook wb;
	// 声明一个xlsx的excel工作表
	XSSFWorkbook wbx;

	// 构造方法一
	public POIUtils() {
		wb = new HSSFWorkbook();
	}

	// 构造方法二
	public POIUtils(String filePath, boolean isXlsx) {
		this.filePath = filePath;
		if (!isXlsx) {
			POIFSFileSystem fs;
			try {
				fs = new POIFSFileSystem(new FileInputStream(filePath));
				wb = new HSSFWorkbook(fs);

			} catch (FileNotFoundException e) {
				throw new RuntimeException(e.getMessage() + filePath, e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage() + filePath, e);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage() + filePath, e);
			}
		} else {
			try {
				FileInputStream fp = new FileInputStream(filePath);
				wbx = new XSSFWorkbook(fp);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给后缀为.xls的excel模板中的单元格赋值
	 * 
	 * @param sheetNo
	 *            工作表index
	 * @param obj
	 * @return List<Cell>
	 * */
	public void ObjToExcel(int sheetNo, HashMap<String, String> map) {
		List<Cell> list = new ArrayList<Cell>();
		// 得到当前的工作表
		HSSFSheet sheet = wb.getSheetAt(sheetNo);
		// 遍历得到该工作表下面的所有单元格Cell
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			// 得到row行
			HSSFRow row = (HSSFRow) rit.next();
			for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {

				// 得到单元格
				HSSFCell cell = (HSSFCell) cit.next();
				list.add(cell);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			String value = list.get(i).toString();
			int index = value.indexOf("$");
			if (index >= 0) {
				String newIndex = value.substring(1);
				list.get(i).setCellValue(map.get(newIndex));
			}
		}
	}

	/**
	 * 给后缀为.xlsx的excel模板中的单元格赋值
	 * 
	 * @param sheetNo
	 *            工作表index
	 * @param obj
	 * @return List<Cell>
	 * */
	public void ObjToExcelXlsx(int sheetNo, HashMap<String, String> map) {
		List<Cell> list = new ArrayList<Cell>();
		// 得到当前的工作表
		XSSFSheet sheet = wbx.getSheetAt(0);
		// 遍历得到该工作表下面的所有单元格Cell
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			// 得到row行
			XSSFRow row = (XSSFRow) rit.next();
			for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {

				// 得到单元格
				XSSFCell cell = (XSSFCell) cit.next();
				list.add(cell);
			}
		}
		for (int i = 0; i < list.size(); i++) {
			String value = list.get(i).toString();
			int index = value.indexOf("$");
			if (index >= 0) {
				String newIndex = value.substring(1);
				list.get(i).setCellValue(map.get(newIndex));
			}
		}
	}

	/**
	 * 获得单元格的值,自动转换数据类型
	 * 
	 * @param cell
	 * @return
	 */
	public Object getCellValue(HSSFCell cell) {
		Object value = null;
		if (cell != null) {
			int cellType = cell.getCellType();
			HSSFCellStyle style = cell.getCellStyle();
			short format = style.getDataFormat();
			switch (cellType) {
			case HSSFCell.CELL_TYPE_NUMERIC:
				double numTxt = cell.getNumericCellValue();
				if (format == 22 || format == 14)
					value = HSSFDateUtil.getJavaDate(numTxt);
				else
					value = numTxt;
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				boolean booleanTxt = cell.getBooleanCellValue();
				value = booleanTxt;
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				value = null;
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				HSSFFormulaEvaluator eval = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
				eval.evaluateInCell(cell);
				value = getCellValue(cell);
				break;
			case HSSFCell.CELL_TYPE_STRING:
				HSSFRichTextString rtxt = cell.getRichStringCellValue();
				if (rtxt == null) {
					break;
				}
				String txt = rtxt.getString();
				value = txt;
				break;
			default:

			}
		}
		return value;
	}

	/**
	 * 保存后缀为.xls的excel。
	 * 
	 * @param out
	 */
	public void save(String filePath) {// 将excel导出去
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			write(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void write(OutputStream out) {
		try {
			wb.write(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 保存后缀为.xlsx的excel。
	 * 
	 * @param out
	 */
	public void saveXlsx(String filePath) {// 将excel导出去
		try {
			OutputStream out = new FileOutputStream(new File(filePath));
			writeXlsx(out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void writeXlsx(OutputStream out) {
		try {
			wbx.write(out);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
