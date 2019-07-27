package com.acl.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 */
public class ParsingStaffAttendanceExcelUtil {

    /**
     * 以及标题
     */
    private static Map<String,MergedRowTitle> mapTitle = new HashMap<String,MergedRowTitle>();


    public  BasicExcelDataEntity getParsingExcelResult(File file){
        Map<String,String> titleIndex = new HashMap<>();
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> titleResult = this.readExcelToObjIndex(wb,3);
        for(String s:titleResult.keySet()){
            System.out.println("表格头的 key : "+s+" value : "+titleResult.get(s).toString());
            String value = titleIndex.get(titleResult.get(s).toString());
            if(value!=null){
                titleIndex.put(titleResult.get(s).toString(),s+","+value);
            }else{
                titleIndex.put(titleResult.get(s).toString(),s);
            }
        }
        ArrayList<Map<String,String>> result = this.readExcelToObj(wb);
        BasicExcelDataEntity excelDataEntity = new BasicExcelDataEntity();
        excelDataEntity.setExcelData(result);
        excelDataEntity.setExcelTitleIndex(titleIndex);
        excelDataEntity.setExcelTitleMap(titleResult);
        return excelDataEntity;
    }

    /**
     * 读取excel数据
     * @param wb
     */
    private Map<String,String> readExcelToObjIndex( Workbook wb,Integer startReadLine) {

        Map<String,String> result = null;
        result = readExcelIndex(wb, 0, startReadLine, 0);
        return result;
    }

    /**
     * 读取excel数据
     * @param wb
     */
    private ArrayList<Map<String,String>> readExcelToObj( Workbook wb) {
        ArrayList<Map<String,String>> result = null;
         result = readExcel(wb, 0, 4, 0);
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
                //System.out.println(c.getColumnIndex()+": "+ rs + "------ ");
                returnStr = rs;
                MergedRowTitle excelTitle = getMergedRowExcelTitle(sheet, startReadLine, c.getColumnIndex());
                if(excelTitle!=null){
                    excelTitle.setColumnName(returnStr);
                    mapTitle.put(excelTitle.getColumnIndex()+"",excelTitle);
                }
            }else {
               // System.out.println("   "+c.getColumnIndex() +" "+  c.getCellType()+ ":"+getCellValue(c)+"++++ ");
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
                    System.out.println("   columnIndex:"+column+"是否合并了行："+true+" ,firstColumn:"+firstColumn +",lastColumn:"+lastColumn+",firstRow:"+firstRow+",lastRow:"+lastRow);
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
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    System.out.println("Cell #" + cell.getColumnIndex());
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



    public static void main(String[] args){
        ParsingStaffAttendanceExcelUtil excelUtil = new ParsingStaffAttendanceExcelUtil();
        //读取excel数据
        String path = "D:\\Maoda\\刷宝透析汇总数据\\刷宝透析汇总数据\\20190307-hkfgs-cx66963-商户数据分析列表--2019-02-01-2019-02-28.xls";
        File file = new File(path) ;
        Map<String,String> titleIndex = new HashMap<>();
//        Map<String,String> titleResult = excelUtil.readExcelToObjIndex(file,1);
//        for(String s:titleResult.keySet()){
//            System.out.println("表格头的 key : "+s+" value : "+titleResult.get(s).toString());
//            String value = titleIndex.get(titleResult.get(s).toString());
//            if(value!=null){
//                titleIndex.put(titleResult.get(s).toString(),s+","+value);
//            }else{
//                titleIndex.put(titleResult.get(s).toString(),s);
//            }
//        }
//       // System.out.println("表格头的 key : "+titleIndex.toString());
//        ArrayList<Map<String,String>> result = excelUtil.readExcelToObj(file);
//        HashMap<String, Integer> counter = new HashMap<String, Integer>();
//
//        for(Map<String,String> map:result){
//            System.out.println(map);
//            for (String str:titleResult.keySet()){
//                String colName = titleResult.get(str);
//                if( titleIndex.containsKey(colName) && colName.equals("直属代理商编号")){
//                    String w =map.get(str) ;
//                    if(counter.containsKey(w)){
//                        int oldValue = counter.get(w);
//                        counter.put(w, oldValue+1);
//                    } else {
//                        counter.put(w, 1);
//                    }
//                }
//            }
//        }
//        for (String str:counter.keySet()){
//            Integer value = counter.get(str);
//            if(value>1){
//                System.out.println("去重："+str+",计数器："+value);
//            }
//        }
    }
 
}