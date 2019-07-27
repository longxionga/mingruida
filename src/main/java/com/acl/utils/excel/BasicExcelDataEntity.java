package com.acl.utils.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class BasicExcelDataEntity implements Serializable {
    private Map<String, String> excelTitleIndex;
    private Map<String, String> excelTitleMap;
    private ArrayList<Map<String, String>> excelData;

    public Map<String, String> getExcelTitleIndex() {
        return excelTitleIndex;
    }

    public void setExcelTitleIndex(Map<String, String> excelTitleIndex) {
        this.excelTitleIndex = excelTitleIndex;
    }

    public ArrayList<Map<String, String>> getExcelData() {
        return excelData;
    }

    public void setExcelData(ArrayList<Map<String, String>> excelData) {
        this.excelData = excelData;
    }

    public Map<String, String> getExcelTitleMap() {
        return excelTitleMap;
    }

    public void setExcelTitleMap(Map<String, String> excelTitleMap) {
        this.excelTitleMap = excelTitleMap;
    }
}
