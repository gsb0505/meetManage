package com.kd.manage.support;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;

import com.kd.manage.controller.util.ObjectUtil;
import com.kd.manage.controller.util.ReflectUtil;
import com.kd.manage.entity.BaseData;

/**
 * excel导出
 * @author zlm
 *
 */
public class ExcelSupport {
	/**
	 * 导出Excel 返回生成的excel名称
	 * 
	 * @param request
	 * @param list
	 * @param columns
	 * @param titles
	 * @return
	 * @throws IOException
	 * 
	 */
	public static String exportExcel(HttpServletRequest request, List<?> list, String[] columns, String[] titles,
			String fileName) throws IOException {
		FileOutputStream fos = null;
		try {
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();

			// 创建表头
			HSSFRow headRow = sheet.createRow(0);
			// 设置表格样式
			CellStyle cellStyle =createCellStyle(workbook);
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cellStyle.setFont(font);
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = headRow.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(titles[i]);
				sheet.setColumnWidth(i, 20 * 256);
			}

			for (int i = 1; i < list.size() + 1; i++) {
				Object obj = list.get(i - 1);
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < columns.length; j++) {
					Object value = ReflectUtil.getFieldValueNoCaseSensitive(obj, columns[j]);
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle);
					if (ObjectUtil.isNotNull(value)) {
						cell.setCellValue(value.toString());
					} else {
						cell.setCellValue("");
						cell.setCellStyle(cellStyle);
					}
				}
			}
			String savePath = request.getSession().getServletContext().getRealPath("/resources/excels");
			// 判断存放excel的文件夹是否存在，不存在创建新的文件夹
			File dir = new File(savePath);
			if (!dir.exists())
				dir.mkdirs();
			if (fileName != null && !fileName.equals(""))
				fileName = fileName + ".xls";
			else
				fileName = new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date()) + ".xls";
			File fExcel = new File(savePath + "/" + fileName);
			fos = new FileOutputStream(fExcel);
			workbook.write(fos);
			fos.flush();
			fos.close();
			return fileName;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static String exportExcelHB(HttpServletRequest request, List<?> list, String[] columns, String[] titles,
									   String fileName, int[] hb, String tradeDate, String reportName) throws IOException {
		if (hb == null) {
			throw new IOException();
		}
		FileOutputStream fos = null;
		try {
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			//创建表头
			HSSFRow headRow_title = sheet.createRow(0);
			HSSFCell cell_title = headRow_title.createCell(0);
			// 设置表格样式
			CellStyle cellStyle_title = createExportStyle(workbook);
			cell_title.setCellValue(new HSSFRichTextString(reportName));
			cell_title.setCellStyle(cellStyle_title);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, titles.length - 1);
			sheet.addMergedRegion(range);

			HSSFRow headRow_title2 = sheet.createRow(1);
			HSSFCell cell_title2 = headRow_title2.createCell(0);

			if(tradeDate != null){
				if(tradeDate.contains(";")){
					String[] tradeDate_sp = tradeDate.split(";");
					// 设置表格样式
					cell_title2.setCellValue(new HSSFRichTextString("会议时间：" + tradeDate_sp[0] + " 到 " + tradeDate_sp[1]));
				}else if(tradeDate.contains("至")){
					String[] tradeDate_sp = tradeDate.split("至");
					cell_title2.setCellValue(new HSSFRichTextString("会议时间：" + tradeDate_sp[0] + " 到 " + tradeDate_sp[1]));
				}else{
					cell_title2.setCellValue(new HSSFRichTextString("会议时间：" + tradeDate + " 到 " + tradeDate));
				}
			}


			cell_title2.setCellStyle(cellStyle_title);
			CellRangeAddress range1 = new CellRangeAddress(1, 1, 0, titles.length - 1);
			sheet.addMergedRegion(range1);

			HSSFRow headRow_title3 = sheet.createRow(2);
			HSSFCell cell_title3 = headRow_title3.createCell(0);
			// 设置表格样式
			cell_title3.setCellValue(new HSSFRichTextString("制表时间：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			cell_title3.setCellStyle(cellStyle_title);
			CellRangeAddress range2 = new CellRangeAddress(2, 2, 0, titles.length - 1);
			sheet.addMergedRegion(range2);

			// 创建表头
			HSSFRow headRow = sheet.createRow(3);
			// 设置表格样式
			CellStyle cellStyle =createCellStyle(workbook);

			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = headRow.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(titles[i]);
				sheet.setColumnWidth(i, 20 * 256);
			}
			int[] num_hb = new int[hb.length];


			for (int i = 4; i < list.size() + 4; i++) {
				Object obj = list.get(i - 4);
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < columns.length; j++) {

					Object value = ReflectUtil.getFieldValueNoCaseSensitive(obj, columns[j]);
					if ("amount".equals(columns[j])
							|| "money".equals(columns[j])||"deposit".equals(columns[j])||"factorage".equals(columns[j])||"cost".equals(columns[j])
							|| "totalMoney".equals(columns[j])|| "tOperMoney".equals(columns[j])||"tDeposit".equals(columns[j])
							|| columns[j].toLowerCase().contains("Money".toLowerCase()) || columns[j].toLowerCase().contains("amount".toLowerCase())
							|| columns[j].toLowerCase().contains("chargeAccount".toLowerCase())||columns[j].toLowerCase().contains("officialReceipt".toLowerCase())
							|| columns[j].toLowerCase().contains("selfHarming".toLowerCase())) {
						try {
							if(null!=value){
								if (!value.toString().trim().isEmpty()){
									value = Double.valueOf(value.toString()) / 100;
								}
							}
						} catch (NumberFormatException e) {
							continue;
						}
					}
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (ObjectUtil.isNotNull(value)) {
						cell.setCellValue(value.toString());
					} else {
						value = "";//如果空的表格不能合并，那么这里可以给value赋一个随机数
						cell.setCellValue("");
					}
					cell.setCellStyle(cellStyle);
					for (int k = 0; k < hb.length; k++) {
						if (j == hb[k]) {
							if (value.toString().equals(titles[j])) {
								num_hb[k]++;
							} else {
								if (num_hb[k] >= 1) {
									sheet.addMergedRegion(new CellRangeAddress(i - num_hb[k] - 1, i - 1, j, j));
								}
								titles[j] = value.toString();
								num_hb[k] = 0;
							}
							break;
						}
					}
				}

				if (i == list.size() + 3) {
					for (int k = 0; k < hb.length; k++) {
						if (num_hb[k] >= 1) {
							sheet.addMergedRegion(new CellRangeAddress(i - num_hb[k], i, hb[k], hb[k]));
						}
					}
				}

			}

			//表格尾部
			HSSFRow row = sheet.createRow(list.size()+4);
			for (int j = 0; j < columns.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(cellStyle_title);
				if(j==0){
				//	cell.setCellValue("出纳：                                        会计：                                        主管：                                     ");
					continue;
				}
				cell.setCellValue("");
			}
			sheet.addMergedRegion(new CellRangeAddress(list.size()+4, list.size()+4, 0, columns.length-1));

			String savePath = request.getSession().getServletContext().getRealPath("/resources/excels");
			// 判断存放excel的文件夹是否存在，不存在创建新的文件夹
			File dir = new File(savePath);
			if (!dir.exists())
				dir.mkdirs();
			if (fileName != null && !fileName.equals(""))
				fileName = fileName + ".xls";
			else
				fileName = new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date()) + ".xls";
			File fExcel = new File(savePath + "/" + fileName);
			fos = new FileOutputStream(fExcel);
			workbook.write(fos);
			fos.flush();
			fos.close();
			return fileName;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}


	/**
	 * 导出Excel 返回生成的excel名称
	 * 
	 * @param request
	 * @param list
	 * @param columns
	 * @param titles
	 * @param hb
	 *            合并列
	 * @return
	 * @throws IOException
	 * 
	 */
	public static String exportExcelHB2(HttpServletRequest request, List<?> list, String[] columns, String[] titles,
			String fileName, int[] hb, String reportName) throws IOException {
		if (hb == null) {
			throw new IOException();
		}
		FileOutputStream fos = null;
		try {
			// 产生工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 产生工作表对象
			HSSFSheet sheet = workbook.createSheet();
			//创建表头
			HSSFRow headRow_title = sheet.createRow(0);
			HSSFCell cell_title = headRow_title.createCell(0);
			// 设置表格样式
			CellStyle cellStyle_title = createExportStyle(workbook);
			cell_title.setCellValue(new HSSFRichTextString(reportName));
			cell_title.setCellStyle(cellStyle_title);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, titles.length - 1);
			sheet.addMergedRegion(range);


			HSSFRow headRow_title2 = sheet.createRow(1);
			HSSFCell cell_title2 = headRow_title2.createCell(0);
			// 设置表格样式
			cell_title2.setCellValue(new HSSFRichTextString("制表时间：" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
			cell_title2.setCellStyle(cellStyle_title);
			CellRangeAddress range1 = new CellRangeAddress(1, 1, 0, titles.length - 1);
			sheet.addMergedRegion(range1);


			// 创建表头
			HSSFRow headRow = sheet.createRow(2);
			// 设置表格样式
			CellStyle cellStyle =createCellStyle(workbook);
			
			for (int i = 0; i < titles.length; i++) {
				HSSFCell cell = headRow.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(titles[i]);
				sheet.setColumnWidth(i, 20 * 256);
			}
			int[] num_hb = new int[hb.length];

			
			for (int i = 3; i < list.size() + 3; i++) {
				Object obj = list.get(i - 3);
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < columns.length; j++) {
					Object value = ReflectUtil.getFieldValueNoCaseSensitive(obj, columns[j]);
					if ("amount".equals(columns[j]) 
							|| "money".equals(columns[j])||"deposit".equals(columns[j])||"factorage".equals(columns[j])||"cost".equals(columns[j])
							|| "totalMoney".equals(columns[j])|| "tOperMoney".equals(columns[j])||"tDeposit".equals(columns[j])
							|| columns[j].toLowerCase().contains("Money".toLowerCase()) || columns[j].toLowerCase().contains("amount".toLowerCase())) {
						try {
							if(null!=value){
								if (!value.toString().trim().isEmpty()){
									value = Double.valueOf(value.toString()) / 100;
								}
                            }
						} catch (NumberFormatException e) {
							continue;
						}
					}
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if (ObjectUtil.isNotNull(value)) {
						cell.setCellValue(value.toString());
					} else {
						value = "";//如果空的表格不能合并，那么这里可以给value赋一个随机数
						cell.setCellValue("");
					}
					cell.setCellStyle(cellStyle);
					for (int k = 0; k < hb.length; k++) {
						if (j == hb[k]) {
							if (value.toString().equals(titles[j])) {
								num_hb[k]++;
							} else {
								if (num_hb[k] >= 1) {
									sheet.addMergedRegion(new CellRangeAddress(i - num_hb[k] - 1, i - 1, j, j));
								}
								titles[j] = value.toString();
								num_hb[k] = 0;
							}
							break;
						}
					}
				}

				if (i == list.size() + 3) {
					for (int k = 0; k < hb.length; k++) {
						if (num_hb[k] >= 1) {
							sheet.addMergedRegion(new CellRangeAddress(i - num_hb[k], i, hb[k], hb[k]));
						}
					}
				}

			}
			
			//表格尾部
			HSSFRow row = sheet.createRow(list.size()+3);
			for (int j = 0; j < columns.length; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(cellStyle_title);
				if(j==0){
					cell.setCellValue("出纳：                                        会计：                                        主管：                                     ");
					continue;
				}
				cell.setCellValue("");
			}
			sheet.addMergedRegion(new CellRangeAddress(list.size()+3, list.size()+3, 0, columns.length-1));
			
			String savePath = request.getSession().getServletContext().getRealPath("/resources/excels");
			// 判断存放excel的文件夹是否存在，不存在创建新的文件夹
			File dir = new File(savePath);
			if (!dir.exists())
				dir.mkdirs();
			if (fileName != null && !fileName.equals(""))
				fileName = fileName + ".xls";
			else
				fileName = new SimpleDateFormat("yyyyMMddHHmmsssss").format(new Date()) + ".xls";
			File fExcel = new File(savePath + "/" + fileName);
			fos = new FileOutputStream(fExcel);
			workbook.write(fos);
			fos.flush();
			fos.close();
			return fileName;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static CellStyle createExportStyle(HSSFWorkbook workbook) {
		CellStyle cellStyle_title = workbook.createCellStyle();
		Font font_title = workbook.createFont();
		font_title.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle_title.setFont(font_title);
		cellStyle_title.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		return cellStyle_title;
	}

	public static CellStyle createCellStyle(HSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框

		return cellStyle;
	}
	
	/**
	 * 替换列值
	 * @param list
	 * @param map 替换的列名与索引code
	 * @param mapV 替换的列名<值，代替的内容>
	 * @param rep 根据值更换字段赋值名称
	 * @param 金额转换
	 * @return
	 */
	public static <T> List<T> replaceColumns(List<T> list,Map<String,String> map,Map<String,Map<Integer,String>> mapV,Map<String,String> rep,Map<String,String> mapAmount){
		if(list == null || map == null){
			return null;
		}
		Set<String> set = map.keySet();
		for (T t : list) {
			
			for (String s : set) {
				//替换传入值
				if(mapV != null && mapV.containsKey(s)){
					//field name in value
					String str = String.valueOf(ReflectUtil.getFieldValue(t,s));
					if(str == null || str.equals("null")){
						continue;
					}
					Integer value = Integer.valueOf(str);
					Map<Integer, String> arr = mapV.get(s);
					
					//取值赋值到指定字段中
					if(rep != null && rep.containsKey(s))
						ReflectUtil.setFieldValue(t, rep.get(s), arr.get(value));
					else
						ReflectUtil.setFieldValue(t, s, arr.get(value));
					continue;
				}
				//field name in value
				String value = String.valueOf(ReflectUtil.getFieldValue(t,s));
				
				//替换字典值
				//存在 field
				if(value == null || value.equals("null")){
					continue;
				}
				String index = map.get(s);
				if(index == null){
					continue;
				}
				Map<String,String> types = DataDictDefault.types;
				
				if(types.containsKey(index)){
					BaseData bd = DataDictDefault.getListValue(index,value);
					if(bd == null){
						continue;
					}
					ReflectUtil.setFieldValue(t, s, bd.getName());
				}
			}
			
			//替换金额单位
			Set<String> set2 = mapAmount.keySet();
			for (String s : set2) {
				//field name in value
				String value = String.valueOf(ReflectUtil.getFieldValue(t,s));
				if(mapAmount != null && mapAmount.containsKey(s)){
					ReflectUtil.setFieldValue(t, s, Integer.valueOf(value)/Integer.valueOf(mapAmount.get(s)));
					continue;
				}
			}
			
		}
		
		return list;
	}
}
