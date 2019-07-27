package com.acl.utils.util;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 *className:MachineExcelUtil
 *author:longxionga
 *createDate:2019年7月8日
 *department:铭锐达
 *description:
 */
public class MachineExcelUtil {

	/**
	 * 根据路径加载解析Excel
	 * 
	 * @param path
	 * @return
	 */
	public static List<Map<String, Object>> parseExcel(String path) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		File file = null;
		InputStream input = null;
		Workbook workBook = null;
		Sheet sheet = null;
		if (path != null && path.length() > 7) {
			// 判断文件是否是Excel(2003、2007)
			String suffix = path
					.substring(path.lastIndexOf("."), path.length());
			if (".xls".equals(suffix) || ".xlsx".equals(suffix)) {// 2003后缀或2007后缀
				file = new File(path);
				try {
					input = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					System.out.println("未找到指定的文件！");
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("读取Excel文件发生异常！");
					e.printStackTrace();
				}
				if (!input.markSupported()) {
					input = new PushbackInputStream(input, 8);
				}
				try {
					if (POIFSFileSystem.hasPOIFSHeader(input)
							|| POIXMLDocument.hasOOXMLHeader(input)) {
						workBook = WorkbookFactory.create(input);
					} else {
						System.out.println("非法的输入流：当前输入流非OLE2流或OOXML流！");
					}
				} catch (IOException e) {
					System.out.println("创建表格工作簿对象发生IO异常！原因：" + e.getMessage());
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					// Your InputStream was neither an OLE2 stream, nor an OOXML
					// stream.
					System.out.println("非法的输入流：当前输入流非OLE2流或OOXML流！");
					e.printStackTrace();
				}
				try {
					if (workBook != null) {
						int numberSheet = workBook.getNumberOfSheets();
						if (numberSheet > 0) {
							sheet = workBook.getSheetAt(0);// 获取第一个工作簿(Sheet)的内容【注意根据实际需要进行修改】
							list = getExcelContent(sheet);
						} else {
							System.out.println("目标表格工作簿(Sheet)数目为0！");
						}
					}
					input.close();
				} catch (IOException e) {
					System.out.println("关闭输入流异常！" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				System.out.println("非法的Excel文件后缀！");
			}
		} else {
			System.out.println("非法的文件路径!");
		}
		return list;
	}

	/**
	 * 解析(读取)Excel内容
	 * 
	 * @param sheet
	 * @return
	 */
	public static List<Map<String, Object>> getExcelContent(Sheet sheet) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int rowCount = sheet.getPhysicalNumberOfRows();// 总行数
		if (rowCount > 1) {
			Row titleRow = sheet.getRow(0);// 标题行
			for (int i = 1; i < rowCount; i++) {// 遍历行，略过标题行，从第二行开始
				Row row = sheet.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);
					if(cell !=null&&!cell.toString().equals("")&&cell.toString()!=null){
						Map<String, Object> map = new HashMap<String, Object>();
						int type = cell.getCellType();
						if(type==HSSFCell.CELL_TYPE_NUMERIC){
							if(HSSFDateUtil.isCellDateFormatted(cell)){
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								//return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
								map.put(titleRow.getCell(j).getStringCellValue(),
										sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
							}else{
								DecimalFormat df = new DecimalFormat("#");
								map.put(titleRow.getCell(j).getStringCellValue(),
										df.format(cell.getNumericCellValue()));
							}
						}else if(type==HSSFCell.CELL_TYPE_STRING){
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getStringCellValue());
						}
						/*switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							
							break;
							//return df.format(cell.getNumericCellValue());
						case HSSFCell.CELL_TYPE_STRING:
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getStringCellValue());
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getCellFormula());
						case HSSFCell.CELL_TYPE_BLANK:
							map.put(titleRow.getCell(j).getStringCellValue(),"");
						case HSSFCell.CELL_TYPE_BOOLEAN:
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getBooleanCellValue() + "");
						case HSSFCell.CELL_TYPE_ERROR:
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getErrorCellValue() + "");
						}*/
						/*if(HSSFDateUtil.isCellDateFormatted(cell)){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							
							map.put(titleRow.getCell(j).getStringCellValue(),
									sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString());
							map.put(titleRow.getCell(j).getStringCellValue(),cell.getStringCellValue());
						}else{
							map.put(titleRow.getCell(j).getStringCellValue(),
									cell.toString());
						}*/
						
						map.put("total", row.getLastCellNum());
						list.add(map);
					}
					
				}
			}
		}
		return list;
	}


	/**
	 * 生成2007 Excel
	 */
	public static void buildXSLXExcel(List<Map<String, Object>> list,
			String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("主键")){
				cellTitle[0] = k ;
			}else if (k.equals("批次号")){
				cellTitle[1] = k ;
			}else if (k.equals("所属品牌")){
				cellTitle[2] = k ;

			}else if (k.equals("机具编号")){
				cellTitle[3] = k ;
			}else if (k.equals("机具序列号")){
				cellTitle[4] = k ;
			}else if (k.equals("归属员工编号")){
				cellTitle[5] = k ;
			}else if (k.equals("归属员工名称")){
				cellTitle[6] =k ;
			}else if (k.equals("商户编号")){
				cellTitle[7] = k ;
			}else if (k.equals("商户名称")){
				cellTitle[8] =k ;
			}else if (k.equals("是否绑定")){
				cellTitle[9] = k ;
			}
			else if (k.equals("机具类型")){
				cellTitle[10] = k ;
			}
			else if (k.equals("是否已缴纳押金")){
				cellTitle[11] = k ;
			}
//			cellTitle[keyInt] = k;
//			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				XSSFCellStyle style = workBook.createCellStyle();// 创建格式
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					for (int j = 0, length = cellTitle.length; j < length; j++) {
						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null 
								? "" : list.get(i).get(cellTitle[j]).toString());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(style);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
																				// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void buildXSLXExcelModel(List<Map<String, Object>> list,
			String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			cellTitle[keyInt] = k;
			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
																				// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * 商户达标信息导出
	 * 生成2007 Excel
	 */
	public static void MerchantbuildXSLXExcel(List<Map<String, Object>> list,
											  String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("所属品牌")){
				cellTitle[0] = k ;
			}else if (k.equals("机具序列号")){
				cellTitle[1] = k ;
			}else if (k.equals("商户编号")){
				cellTitle[2] = k ;

			}else if (k.equals("商户名称")){
				cellTitle[3] = k ;
			}else if (k.equals("入网时间")){
				cellTitle[4] = k ;
			}else if (k.equals("商户类型")){
				cellTitle[5] = k ;
			}else if (k.equals("商户状态")){
				cellTitle[6] =k ;
			}else if (k.equals("审批状态")){
				cellTitle[7] = k ;
			}else if (k.equals("费率类型")){
				cellTitle[8] =k ;
			}else if (k.equals("员工编号")){
				cellTitle[9] = k ;
			}
			else if (k.equals("员工名称")){
				cellTitle[10] = k ;
			}
			else if (k.equals("交易大于100交易")){
				cellTitle[11] = k ;
			}else if (k.equals("是否达标")){
				cellTitle[12] = k ;
			}else if (k.equals("达标日期")){
				cellTitle[13] = k ;
			}
//			cellTitle[keyInt] = k;
//			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				XSSFCellStyle style = workBook.createCellStyle();// 创建格式
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					for (int j = 0, length = cellTitle.length; j < length; j++) {
						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null
								? "" : list.get(i).get(cellTitle[j]).toString());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(style);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
			// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 员工流水信息导出
	 * 生成2007 Excel
	 */
	public static void TradebuildXSLXExcel(List<Map<String, Object>> list,
										   String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("机具品牌")){
				cellTitle[0] = k ;
			}else if (k.equals("所属部门")){
				cellTitle[1] = k ;
			}else if (k.equals("员工名称")){
				cellTitle[2] = k ;

			}else if (k.equals("交易量")){
				cellTitle[3] = k ;
			}else if (k.equals("流水合计")){
				cellTitle[4] = k ;
			}
//			cellTitle[keyInt] = k;
//			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				XSSFCellStyle style = workBook.createCellStyle();// 创建格式
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					for (int j = 0, length = cellTitle.length; j < length; j++) {
						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null
								? "" : list.get(i).get(cellTitle[j]).toString());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(style);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
			// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}




	/***
	* @Description: 交易流水导出
	* @Param: [list, fileName, response]
	* @return: void
	* @Author: 易昙
	* @Date: 2019/7/25
	*/

	public static void TradwaterXSLXExcel(List<Map<String, Object>> list,
										   String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("品牌名称")){
				cellTitle[0] = k ;
			}else if (k.equals("日期")){
				cellTitle[1] = k ;
			}else if (k.equals("交易总量")){
				cellTitle[2] = k ;

			}else if (k.equals("支付宝")){
				cellTitle[3] = k ;
			}else if (k.equals("微信支付")){
				cellTitle[4] = k ;
			}else if (k.equals("银联二维码")){
				cellTitle[5] = k ;
			}else if (k.equals("贷记卡")){
				cellTitle[6] = k ;
			}else if (k.equals("准贷记卡")){
				cellTitle[7] = k ;
			}else if (k.equals("借记卡")){
				cellTitle[8] = k ;
			}
//			cellTitle[keyInt] = k;
//			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			XSSFCellStyle fontStyle = workBook.createCellStyle();




			XSSFCellStyle fontStyle2 = workBook.createCellStyle();

			//内容
			fontStyle2 = workBook.createCellStyle();
			XSSFFont font2 = workBook.createFont();
			font2.setFontName("黑体");
			font2.setFontHeightInPoints((short)12);
			fontStyle2.setFont(font2);
			fontStyle2.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
			fontStyle2.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			fontStyle2.setBorderTop(XSSFCellStyle.BORDER_THIN);//右边框
			fontStyle2.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);//居中


			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("黑体");
			font.setFontHeightInPoints((short) 16);//字体大小

			fontStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);//下边框
			fontStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
			fontStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);//右边框
			fontStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
			fontStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//居中
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					row.setHeight((short)400);
					sheet.setColumnWidth(i, 90*50);
					for (int j = 0, length = cellTitle.length; j < length; j++) {

						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null
								? "" : list.get(i).get(cellTitle[j]).toString());
						fontStyle2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(fontStyle2);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
			// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}




	/**
	 * 下级员工流水合计导出
	 * 生成2007 Excel
	 */
	public static void ManageTradebuildXSLXExcel(List<Map<String, Object>> list,
										   String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("机具品牌")){
				cellTitle[0] = k ;
			}else if (k.equals("所属部门")){
				cellTitle[1] = k ;
			}else if (k.equals("员工名称")){
				cellTitle[2] = k ;
			}else if (k.equals("交易总金额")){
				cellTitle[3] = k ;
			}else if (k.equals("贷记卡")){
				cellTitle[4] = k ;
			}else if (k.equals("准贷记卡")){
				cellTitle[5] = k ;
			}else if (k.equals("借记卡")){
				cellTitle[6] = k ;
			}else if (k.equals("支付宝")){
				cellTitle[7] = k ;
			}else if (k.equals("微信支付")){
				cellTitle[8] = k ;
			}else if (k.equals("银联二维码")){
				cellTitle[9] = k ;
			}
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				XSSFCellStyle style = workBook.createCellStyle();// 创建格式
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					for (int j = 0, length = cellTitle.length; j < length; j++) {
						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null
								? "" : list.get(i).get(cellTitle[j]).toString());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(style);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
			// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 商户信息导出
	 * 生成2007 Excel
	 */
	public static void MerchantInfobuildXSLXExcel(List<Map<String, Object>> list,
										   String fileName, HttpServletResponse response) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		String now = format.format(new Date());
		String exportFileName = fileName + now + ".xlsx";// 导出文件名

		XSSFWorkbook workBook = null;
		Set<String> keySet = list.get(0).keySet();
		String[] cellTitle = new String[keySet.size()];
		Iterator<String> iter = keySet.iterator();
		int keyInt = 0;
		while (iter.hasNext()) {// 遍历二次,速度慢
			String k = (String) iter.next();
			if (k.equals("机具品牌")){
				cellTitle[0] = k ;
			}else if (k.equals("商户编号")){
				cellTitle[1] = k ;
			}else if (k.equals("商户名称")){
				cellTitle[2] = k ;
			}else if (k.equals("入网时间")){
				cellTitle[3] = k ;
			}else if (k.equals("归属员工名称")){
				cellTitle[4] = k ;
			}else if (k.equals("商户类型")){
				cellTitle[5] = k ;
			}else if (k.equals("商户状态")){
				cellTitle[6] = k ;
			}else if (k.equals("机具类型")){
				cellTitle[7] = k ;
			}else if (k.equals("审核状态")){
				cellTitle[8] = k ;
			}else if (k.equals("费率类型")){
				cellTitle[9] = k ;
			}else if (k.equals("创建时间")){
				cellTitle[10] = k ;
			}else if (k.equals("更新时间")){
				cellTitle[11] = k ;
			}
//			cellTitle[keyInt] = k;
//			keyInt++;
		}

		try {
			workBook = new XSSFWorkbook();// 创建工作薄
			XSSFSheet sheet = workBook.createSheet();
			workBook.setSheetName(0, fileName);// 工作簿名称
			XSSFFont font = workBook.createFont();
			font.setColor(XSSFFont.COLOR_NORMAL);
			font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			XSSFCellStyle cellStyle = workBook.createCellStyle();// 创建格式
			cellStyle.setFont(font);
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 创建第一行标题
			XSSFRow titleRow = sheet.createRow((short) 0);// 第一行标题
			for (int i = 0, size = cellTitle.length; i < size; i++) {// 创建第1行标题单元格
				XSSFCell cell = titleRow.createCell(i, 0);
				cell.setCellStyle(cellStyle);
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(cellTitle[i]);
			}

			if (list != null && !list.isEmpty()) {
				XSSFCellStyle style = workBook.createCellStyle();// 创建格式
				for (int i = 0, size = list.size(); i < size; i++) {
					XSSFRow row = sheet.createRow((short) i + 1);
					for (int j = 0, length = cellTitle.length; j < length; j++) {
						XSSFCell cell = row.createCell(j, 0);// 在上面行索引0的位置创建单元格
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);// 定义单元格为字符串类型
						cell.setCellValue(list.get(i).get(cellTitle[j]) == null
								? "" : list.get(i).get(cellTitle[j]).toString());
						style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
						cell.setCellStyle(style);
					}
				}
			}

			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String((exportFileName).getBytes(), "ISO-8859-1"));
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");// 定义输出类型
			// //
			OutputStream outStream = response.getOutputStream();
			workBook.write(outStream);
			outStream.flush();
		} catch (IOException e) {
			System.out.println("生成人员信息Excel发生IO 异常！" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("文件导出发生异常！异常原因：" + e.getMessage());
			e.printStackTrace();
		}
	}
}