package com.zjlab.qa.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ReadExcelUtil {

    private static String root=ReadExcelUtil.class.getResource("/data/").getPath();
    /**
     * read excel
     * @param
     * @return
     */
    public static List<Map<String, String>> getExcelList(String fileName,String apiTag) {
        String filePath = root+fileName;
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        List<Map<String, String>> list = null;
        String cellData = null;

        wb = readExcel(filePath);
        if (wb != null) {
            //用来存放表中数据
            list = new ArrayList<Map<String, String>>();
            sheet = wb.getSheetAt(0);
            int rownum = sheet.getPhysicalNumberOfRows();
            row = sheet.getRow(0);
//            int colStart = row.getFirstCellNum();
            int colEnd = row.getPhysicalNumberOfCells();
            String [] colnumName=new String[colEnd];

            for (int i = 0; i < rownum; i++) {
                row = sheet.getRow(i);
                Map<String, String> map=new HashMap<String, String>();
                if (!isRowEmpty(row)) {
                    for (int j = 0; j < colEnd; j++) {
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if(cellData!=null ){
                            if(i == 0 && cellData.length()>0){
                                colnumName[j]=cellData;
                            } else{
                                if(colnumName[j]!=null) {
                                    map.put(colnumName[j], cellData);
                                }
                            }
                        }

                    }

                } else {
                    break;
                }
                if(!map.isEmpty()) {
                    if(!StringUtils.isBlank(apiTag)&& !map.containsValue(apiTag)){
                        continue;
                    }
                    list.add(map);
                }
            }
        }
        return list;
    }

    /**
     * 判断excel文件的类型
     *
     * @param filePath
     * @return
     */
    public static Workbook readExcel(String filePath) {
        Workbook wb = null;
        if (filePath == null) {
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                return wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                return wb = new XSSFWorkbook(is);
            } else {
                return wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    public static Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    cellValue =new DecimalFormat("########").format(cell.getNumericCellValue());
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    private static boolean  isRowEmpty(Row row){
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() !=BLANK )
                return false;
        }
        return true;
    }
}