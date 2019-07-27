package com.acl.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by handsome on 2016/7/11.
 */
public class ParsingExcelUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParsingExcelUtil.class);


    /**
     * 以及标题
     */
    private static Map<String,MergedRowTitle> mapTitle = new HashMap<String,MergedRowTitle>();

    public  List<BasicInfo> getParsingExcelResult(File file){
        //titleIndex:{序号=0, 本月业绩达标=8, 备注=28, 入职时间=5, 请假天数=7, 姓名=2, 手机号=1, 应扣工资=24,23,22,21,20,19,18, 预支工资=26, 薪资及绩效提成=17,16,15,14,13,12,11,10, 上班工作日=6, 应发工资=25, 身份证号=3, 本月未达标=9, 岗位=4, 实发工资=27}
        Map<String,String> titleIndex = new HashMap<>();
        Map<String,String> titleResult = readExcelToObjIndex(file,1);
        //titleResult:{0=序号, 1=手机号, 2=姓名, 3=身份证号, 4=岗位, 5=入职时间, 6=上班工作日, 7=请假天数, 8=本月业绩达标, 9=本月未达标, 10=薪资及绩效提成, 11=薪资及绩效提成, 12=薪资及绩效提成, 13=薪资及绩效提成, 14=薪资及绩效提成, 15=薪资及绩效提成, 16=薪资及绩效提成, 17=薪资及绩效提成, 18=应扣工资, 19=应扣工资, 20=应扣工资, 21=应扣工资, 22=应扣工资, 23=应扣工资, 24=应扣工资, 25=应发工资, 26=预支工资, 27=实发工资, 28=备注}
        LOGGER.info("titleResult:"+titleResult.toString());
        for(String s:titleResult.keySet()){
            //LOGGER.info("合并的 key : "+s+" value : "+titleResult.get(s).toString());
            String value = titleIndex.get(titleResult.get(s).toString());
            if(value!=null){
                titleIndex.put(titleResult.get(s).toString(),s+","+value);
            }else{
                titleIndex.put(titleResult.get(s).toString(),s);
            }
        }
        LOGGER.info("titleIndex:"+titleIndex.toString());
//        for(String s:titleIndex.keySet()){
//            //System.out.println("   AAAAAAAA : "+s+" value : "+titleIndex.get(s).toString());
//        }
        Map<String,String> titleRowResult = readExcelToObjIndex(file,2);
        //{0=序号, 1=手机号, 2=姓名, 3=身份证号, 4=岗位, 5=入职时间, 6=上班工作日, 7=请假天数, 8=本月业绩达标, 9=本月未达标, 10=基本工资, 11=岗位工资, 12=全勤, 13=奖金, 14=服务绩效, 15=测试, 16=提成, 17=合计, 18=请假, 19=旷工, 20=迟到, 21=社保, 22=罚款, 23=未达标机具成本, 24=合计, 25=应发工资, 26=预支工资, 27=实发工资, 28=备注}
        LOGGER.info("titleRowResult:"+titleRowResult.toString());

        //mapTitle:{11=MergedRowTitle{key='null', columnIndex=11, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 22=MergedRowTitle{key='null', columnIndex=22, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 12=MergedRowTitle{key='null', columnIndex=12, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 23=MergedRowTitle{key='null', columnIndex=23, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 13=MergedRowTitle{key='null', columnIndex=13, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 24=MergedRowTitle{key='null', columnIndex=24, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 14=MergedRowTitle{key='null', columnIndex=14, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 15=MergedRowTitle{key='null', columnIndex=15, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 16=MergedRowTitle{key='null', columnIndex=16, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 17=MergedRowTitle{key='null', columnIndex=17, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 18=MergedRowTitle{key='null', columnIndex=18, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 19=MergedRowTitle{key='null', columnIndex=19, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 20=MergedRowTitle{key='null', columnIndex=20, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}, 10=MergedRowTitle{key='null', columnIndex=10, columnName='薪资及绩效提成', currentColumnName='null', columnValue='null', firstColumn=10, lastColumn=17, firstRow=1, lastRow=1}, 21=MergedRowTitle{key='null', columnIndex=21, columnName='应扣工资', currentColumnName='null', columnValue='null', firstColumn=18, lastColumn=24, firstRow=1, lastRow=1}}
        LOGGER.info("mapTitle:"+mapTitle.toString());


        ArrayList<Map<String,String>> result = readExcelToObj(file);
        //excel:{0=1, 1=13986087110, 2=何琼, 3=420902199209194563, 4=财务, 5=20160621, 6=25, 7=2, 8=0, 9=0, 10=1750, 11=1550, 12=1500, 13=200, 15=101, 17=5101, 18=232.13, 19=, 20=10, 21=357.16, 24=599.29, 25=4501.71, 26=, 27=4501.71}
        List<BasicInfo> basicInfoList = new ArrayList<>();
        for(Map<String,String> map:result){
            LOGGER.info("excel:"+map.toString());
            BasicInfo basicInfo = new BasicInfo();
            for (String str:titleResult.keySet()){
                String colName = titleResult.get(str);
                if( titleIndex.containsKey(colName) && colName.equals("序号")){
                    basicInfo.setId(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("手机号")){
                    basicInfo.setMobile(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("姓名")){
                    basicInfo.setName(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("身份证号")){
                    basicInfo.setIdCard(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("岗位")){
                    basicInfo.setJob(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("入职时间")){
                    basicInfo.setJobDay(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("上班工作日")){
                    basicInfo.setWordDays(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("请假天数")){
                    basicInfo.setLeaveDays(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("本月业绩达标")){
                    basicInfo.setMonthCheck(map.get(str));
                    continue;
                }
                if( titleIndex.containsKey(colName) && colName.equals("本月未达标")){
                    basicInfo.setMonthUnCheck(map.get(str));
                    continue;
                }
                if(titleIndex.containsKey("薪资及绩效提成") &&  colName.equals("薪资及绩效提成")){
                    List<String> strList = Arrays.asList(StringUtils.split(titleIndex.get(colName),","));
                    Collections.sort(strList);
                    Map mapStrs =basicInfo.getTotalMap();
                    if(mapStrs == null){
                        mapStrs  = new LinkedHashMap();
                    }
                    for (String keys:strList){
                        if(keys.equals(str)){
                            //MergedRowTitle mergedRowTitle = mapTitle.get(keys);
                            MergedRowTitle mergedRowTitle = new MergedRowTitle();
                            mergedRowTitle.setKey(keys);
                            mergedRowTitle.setColumnValue(map.get(keys));
                            mergedRowTitle.setColumnName(titleResult.get(keys));
                            mergedRowTitle.setCurrentColumnName(titleRowResult.get(keys));
                            LOGGER.info("薪资及绩效提成，从行数据中解析的名字："+titleRowResult.get(keys)+",key:"+keys+",value:"+map.get(keys));
                            mapStrs.put(keys,mergedRowTitle);
                            basicInfo.setTotalMap(mapStrs);
                        }
                    }
                    continue;
                }

                if(titleIndex.containsKey("应扣工资") &&  colName.equals("应扣工资")){
                    List<String> strList = Arrays.asList(StringUtils.split(titleIndex.get(colName),","));
                    Collections.sort(strList);
                    Map mapStrs = basicInfo.getDeductMap();
                    if(mapStrs == null){
                        mapStrs  = new LinkedHashMap();
                    }
                    for (String keys:strList){
                        if(keys.equals(str)){
                            MergedRowTitle mergedRowTitle = new MergedRowTitle();
                            mergedRowTitle.setKey(keys);
                            mergedRowTitle.setColumnValue(map.get(keys));
                            mergedRowTitle.setColumnName(titleResult.get(keys));
                            mergedRowTitle.setCurrentColumnName(titleRowResult.get(keys));
                            LOGGER.info("应扣工资，从行数据中解析的名字："+titleRowResult.get(keys)+",key:"+keys+",value:"+map.get(keys));
                            mapStrs.put(keys,mergedRowTitle);
                            basicInfo.setDeductMap(mapStrs);
                        }
                    }
                    continue;
                }

                if(titleIndex.containsKey(colName) && colName.equals("应发工资")){
                    basicInfo.setTotalAmount(map.get(str));
                    continue;
                }

                if(titleIndex.containsKey(colName) && colName.equals("预支工资")){
                    basicInfo.setDeductAmount(map.get(str));
                    continue;
                }

                if(titleIndex.containsKey(colName) && colName.equals("实发工资")){
                    basicInfo.setRealAmount(map.get(str));
                    continue;
                }

                if(titleIndex.containsKey(colName) && colName.equals("备注")){
                    basicInfo.setNotes(map.get(str));
                    continue;
                }
            }

            basicInfoList.add(basicInfo);
        }
        basicInfoList.forEach(basicInfo -> {
            LOGGER.info(basicInfo.toString());
        });
        return basicInfoList;
    }


    public static void main(String[] args){
        ParsingExcelUtil excelUtil = new ParsingExcelUtil();
        //读取excel数据
        String path = "D:\\Maoda\\查看工资分润程序需求\\查看工资分润程序需求\\直营团队工资表模板3.xlsx";
        File file = new File(path) ;
        Map<String,String> titleIndex = new HashMap<>();
        Map<String,String> titleResult = excelUtil.readExcelToObjIndex(file,1);
        for(String s:titleResult.keySet()){
            //LOGGER.info("合并的 key : "+s+" value : "+titleResult.get(s).toString());
            String value = titleIndex.get(titleResult.get(s).toString());
            if(value!=null){
                titleIndex.put(titleResult.get(s).toString(),s+","+value);
            }else{
                titleIndex.put(titleResult.get(s).toString(),s);
            }
        }
        for(String s:titleIndex.keySet()){
           //LOGGER.info("   AAAAAAAA : "+s+" value : "+titleIndex.get(s).toString());
        }
        Map<String,String> titleRowResult = excelUtil.readExcelToObjIndex(file,2);
        for(String s:titleRowResult.keySet()){
            MergedRowTitle mergedRowTitle = mapTitle.get(s);
            if(mergedRowTitle!=null){
               // LOGGER.info("交叉比对索引："+s+",值:"+titleRowResult.get(s).toString()+" ,合并名称："+mergedRowTitle.getColumnName());
            }else{
                //LOGGER.info("交叉比对索引："+s+",值:"+titleRowResult.get(s).toString()+" ,存在合并"+mapTitle.containsKey(s));
            }
        }

        ArrayList<Map<String,String>> result = excelUtil.readExcelToObj(file);
        List<BasicInfo> basicInfoList = new ArrayList<>();
        for(Map<String,String> map:result){
            BasicInfo basicInfo = new BasicInfo();
            for(String s:map.keySet()){
                String indexValue = titleIndex.get("序号");
                if(indexValue!=null){
                    basicInfo.setId(map.get(indexValue));
                }
                indexValue = titleIndex.get("手机号");
                if(indexValue!=null){
                    basicInfo.setMobile(map.get(indexValue));
                }
                indexValue = titleIndex.get("姓名");
                if(indexValue!=null){
                    basicInfo.setName(map.get(indexValue));
                }
                indexValue = titleIndex.get("身份证号");
                if(indexValue!=null){
                    basicInfo.setIdCard(map.get(indexValue));
                }

                indexValue = titleIndex.get("岗位");
                if(indexValue!=null){
                    basicInfo.setJob(map.get(indexValue));
                }

                indexValue = titleIndex.get("入职时间");
                if(indexValue!=null){
                    basicInfo.setJobDay(map.get(indexValue));
                }

                indexValue = titleIndex.get("上班工作日");
                if(indexValue!=null){
                    basicInfo.setWordDays(map.get(indexValue));
                }
                indexValue = titleIndex.get("请假天数");
                if(indexValue!=null){
                    basicInfo.setLeaveDays(map.get(indexValue));
                }
                indexValue = titleIndex.get("本月业绩达标");
                if(indexValue!=null){
                    basicInfo.setMonthCheck(map.get(indexValue));
                }

                indexValue = titleIndex.get("本月未达标");
                if(indexValue!=null){
                    basicInfo.setMonthUnCheck(map.get(indexValue));
                }

                indexValue = titleIndex.get("薪资及绩效提成");
                if(indexValue!=null){
                    List<String> strList = Arrays.asList(StringUtils.split(indexValue,","));
                    Collections.sort(strList);
                    Map mapStrs = new LinkedHashMap();
                    for (String keys:strList){
                        MergedRowTitle mergedRowTitle = mapTitle.get(keys);
                        mergedRowTitle.setKey(keys);
                        mergedRowTitle.setColumnValue(map.get(keys));
                        mapStrs.put(keys,mergedRowTitle);
                    }
                   basicInfo.setTotalMap(mapStrs);
                }

                indexValue = titleIndex.get("应扣工资");
                if(indexValue!=null){
                    List<String> strList = Arrays.asList(StringUtils.split(indexValue,","));
                    Collections.sort(strList);
                    Map mapStrs = new LinkedHashMap();
                    for (String keys:strList){
                        MergedRowTitle mergedRowTitle = mapTitle.get(keys);
                        mergedRowTitle.setKey(keys);
                        mergedRowTitle.setColumnValue(map.get(keys));
                        mapStrs.put(keys,mergedRowTitle);
                    }
                    basicInfo.setDeductMap(mapStrs);
                }

                indexValue = titleIndex.get("应发工资");
                if(indexValue!=null){
                    basicInfo.setTotalAmount(map.get(indexValue));
                }

                indexValue = titleIndex.get("预支工资");
                if(indexValue!=null){
                    basicInfo.setDeductAmount(map.get(indexValue));
                }

                indexValue = titleIndex.get("实发工资");
                if(indexValue!=null){
                    basicInfo.setRealAmount(map.get(indexValue));
                }

                indexValue = titleIndex.get("备注");
                if(indexValue!=null){
                    basicInfo.setNotes(map.get(indexValue));
                }
                //System.out.println("   key : "+s+" value : "+map.get(s).toString());
            }
            basicInfoList.add(basicInfo);
        }
        basicInfoList.forEach(basicInfo -> {
            LOGGER.info(basicInfo.toString());
        });


    }

    /**
     * 读取excel数据
     * @param file
     */
    private Map<String,String> readExcelToObjIndex(File file,Integer startReadLine) {

        Workbook wb = null;
        Map<String,String> result = null;
        try {
            wb = WorkbookFactory.create(file);
            result = readExcelIndex(wb, 0, startReadLine, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取excel数据
     * @param file
     */
    private ArrayList<Map<String,String>> readExcelToObj(File file) {

        Workbook wb = null;
        ArrayList<Map<String,String>> result = null;
        try {
            wb = WorkbookFactory.create(file);
            result = readExcel(wb, 0, 3, 0);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 读取excel文件
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
    private Map<String,String> readExcelIndex(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        row = sheet.getRow(startReadLine);

        Map<String,String> map = new LinkedHashMap<String,String>();
        for(Cell c : row) {
            String returnStr = "";
            boolean isMerge = isMergedRegion(sheet, startReadLine, c.getColumnIndex());
            //判断是否具有合并单元格
            if(isMerge) {
                String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                LOGGER.info(c.getColumnIndex()+": "+ rs + "------ ");
                returnStr = rs;
                MergedRowTitle excelTitle = getMergedRowExcelTitle(sheet, startReadLine, c.getColumnIndex());
                if(excelTitle!=null){
                    excelTitle.setColumnName(returnStr);
                    mapTitle.put(excelTitle.getColumnIndex()+"",excelTitle);
                }
            }else {
                LOGGER.info("   "+c.getColumnIndex() +" "+  c.getCellType()+ ":"+getCellValue(c)+"++++ ");
                returnStr = getCellValue(c).trim();
            }
            map.put(c.getColumnIndex()+"",returnStr);
        }
        return map;

    }
    /**
     * 读取excel文件
     * @param wb
     * @param sheetIndex sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine 去除最后读取的行
     */
    private ArrayList<Map<String,String>> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        ArrayList<Map<String,String>> result = new ArrayList<Map<String,String>>();
        for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {

            row = sheet.getRow(i);
            if(row == null) {
                continue;
            }
            Map<String,String> map = new LinkedHashMap<String,String>();
            for(Cell c : row) {
                String returnStr = "";

                boolean isMerge = isMergedRegion(sheet, i, c.getColumnIndex());
                //System.out.println("是否合并了行："+isMergeRow);
                //判断是否具有合并单元格
                if(isMerge) {
                    String rs = getMergedRegionValue(sheet, row.getRowNum(), c.getColumnIndex());
                  // System.out.println(c.getColumnIndex()+": "+ rs + "------ ");
                    returnStr = rs;
                }else {
                   //System.out.println("   "+c.getColumnIndex() +" "+  c.getCellType()+ ":"+getCellValue(c)+"++++ ");
                    returnStr = getCellValue(c).trim();
                }
                map.put(c.getColumnIndex()+"",returnStr);

            }
            result.add(map);
        }
        return result;

    }

    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private String getMergedRegionValue(Sheet sheet , int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private MergedRowTitle getMergedRowExcelTitle(Sheet sheet, int row , int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    LOGGER.info("   columnIndex:"+column+"是否合并了行："+true+" ,firstColumn:"+firstColumn +",lastColumn:"+lastColumn+",firstRow:"+firstRow+",lastRow:"+lastRow);
                    MergedRowTitle excelTitle = new MergedRowTitle();
                    excelTitle.setFirstColumn(firstColumn);
                    excelTitle.setLastColumn(lastColumn);
                    excelTitle.setFirstRow(firstRow);
                    excelTitle.setLastRow(lastRow);
                    excelTitle.setColumnIndex(column);
                    return excelTitle;
                }
            }
        }
        return null;
    }



    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet, int row , int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    private boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    private void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell){

        if(cell == null) return "";

        if(cell.getCellType() == Cell.CELL_TYPE_STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){
            String strCell = "";
            try {
                /*
                 * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中cell
                 * .getNumericCellValue();方法会抛出java.lang.NumberFormatException异常
                 */
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    Integer c = (date.getMonth() + 1);
                    if(c>9){
                        strCell = (date.getYear() + 1900) + "" + c +"" + date.getDate();
                    }else{
                        strCell = (date.getYear() + 1900) + "0" + c +"" + date.getDate();
                    }
                } else {
                    Double d = cell.getNumericCellValue();
                    DecimalFormat df = new DecimalFormat("#.##");
                    String value = df.format(d);
                    return value;
                }
            } catch (IllegalStateException e) {
                strCell = String.valueOf(cell.getRichStringCellValue());
            }
            return strCell ;

        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            String strCell = "";
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                Integer c = (date.getMonth() + 1);
                if(c>9){
                    strCell = (date.getYear() + 1900) + "" + c +"" + date.getDate();
                }else{
                    strCell = (date.getYear() + 1900) + "0" + c +"" + date.getDate();
                }
                return strCell ;
            }else{
                Double d = cell.getNumericCellValue();
                DecimalFormat df = new DecimalFormat("#.##");
                String value = df.format(d);
                return value;
            }
        }
        return "";
    }


    /**
     * 获取单元格的值
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell, FormulaEvaluator formulaEvaluator){

        if(cell == null) return "";

        if(cell.getCellType() == Cell.CELL_TYPE_STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){

            return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());

        }else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }
    /**
     * 从excel读取内容
     */
    private static void readContent(String fileName)  {
        boolean isE2007 = false;    //判断是否是excel2007格式
        if(fileName.endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(fileName);  //建立输入流
            Workbook wb  = null;
            //根据文件格式(2003或者2007)来初始化
            if(isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据
                LOGGER.info("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    LOGGER.info("Cell #" + cell.getColumnIndex());
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            System.out.println(cell.getCellFormula());
                            break;
                        default:
                            System.out.println("unsuported sell type======="+cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    static public class BasicInfo implements Serializable{
        private String id ;
        private String mobile;
        private String name;
        private String idCard;
        //岗位
        private String job;

        //入职时间
        private String jobDay;

        //上班工作日
        private String wordDays;

        //请假天数
        private String leaveDays;

        //本月业绩达标
        private String monthCheck ;

        //本月未达标
        private String monthUnCheck ;

        //薪资及绩效提成
        private Map totalMap ;

        //应扣工资
        private Map deductMap ;

        //应发工资
        private String totalAmount ;
        //预支工资
        private String deductAmount ;
        //实发工资
        private String realAmount ;

        //备注
        private String notes;

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getJobDay() {
            return jobDay;
        }

        public void setJobDay(String jobDay) {
            this.jobDay = jobDay;
        }

        public String getWordDays() {
            return wordDays;
        }

        public void setWordDays(String wordDays) {
            this.wordDays = wordDays;
        }

        public String getLeaveDays() {
            return leaveDays;
        }

        public void setLeaveDays(String leaveDays) {
            this.leaveDays = leaveDays;
        }

        public String getMonthCheck() {
            return monthCheck;
        }

        public void setMonthCheck(String monthCheck) {
            this.monthCheck = monthCheck;
        }

        public String getMonthUnCheck() {
            return monthUnCheck;
        }

        public void setMonthUnCheck(String monthUnCheck) {
            this.monthUnCheck = monthUnCheck;
        }

        public Map getTotalMap() {
            return totalMap;
        }

        public void setTotalMap(Map totalMap) {
            this.totalMap = totalMap;
        }

        public Map getDeductMap() {
            return deductMap;
        }

        public void setDeductMap(Map deductMap) {
            this.deductMap = deductMap;
        }

        public String getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getDeductAmount() {
            return deductAmount;
        }

        public void setDeductAmount(String deductAmount) {
            this.deductAmount = deductAmount;
        }

        public String getRealAmount() {
            return realAmount;
        }

        public void setRealAmount(String realAmount) {
            this.realAmount = realAmount;
        }

        @Override
        public String toString() {
            return "BasicInfo{" +
                    "id='" + id + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", name='" + name + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", job='" + job + '\'' +
                    ", jobDay='" + jobDay + '\'' +
                    ", wordDays='" + wordDays + '\'' +
                    ", leaveDays='" + leaveDays + '\'' +
                    ", monthCheck='" + monthCheck + '\'' +
                    ", monthUnCheck='" + monthUnCheck + '\'' +
                    ", totalMap=" + totalMap +
                    ", deductMap=" + deductMap +
                    ", totalAmount='" + totalAmount + '\'' +
                    ", deductAmount='" + deductAmount + '\'' +
                    ", realAmount='" + realAmount + '\'' +
                    ", notes='" + notes + '\'' +
                    '}';
        }
    }

    static  public class MergedRowTitle implements Serializable{

        private String key ;
        private Integer columnIndex;
        private String columnName ;
        private String currentColumnName ;
        private String columnValue ;
        private Integer firstColumn;
        private Integer lastColumn;
        private Integer firstRow;
        private Integer lastRow;

        @Override
        public String toString() {
            return "MergedRowTitle{" +
                    "key='" + key + '\'' +
                    ", columnIndex=" + columnIndex +
                    ", columnName='" + columnName + '\'' +
                    ", currentColumnName='" + currentColumnName + '\'' +
                    ", columnValue='" + columnValue + '\'' +
                    ", firstColumn=" + firstColumn +
                    ", lastColumn=" + lastColumn +
                    ", firstRow=" + firstRow +
                    ", lastRow=" + lastRow +
                    '}';
        }


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getColumnIndex() {
            return columnIndex;
        }

        public void setColumnIndex(Integer columnIndex) {
            this.columnIndex = columnIndex;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public String getCurrentColumnName() {
            return currentColumnName;
        }

        public void setCurrentColumnName(String currentColumnName) {
            this.currentColumnName = currentColumnName;
        }

        public String getColumnValue() {
            return columnValue;
        }

        public void setColumnValue(String columnValue) {
            this.columnValue = columnValue;
        }

        public Integer getFirstColumn() {
            return firstColumn;
        }

        public void setFirstColumn(Integer firstColumn) {
            this.firstColumn = firstColumn;
        }

        public Integer getLastColumn() {
            return lastColumn;
        }

        public void setLastColumn(Integer lastColumn) {
            this.lastColumn = lastColumn;
        }

        public Integer getFirstRow() {
            return firstRow;
        }

        public void setFirstRow(Integer firstRow) {
            this.firstRow = firstRow;
        }

        public Integer getLastRow() {
            return lastRow;
        }

        public void setLastRow(Integer lastRow) {
            this.lastRow = lastRow;
        }
    }
 
}