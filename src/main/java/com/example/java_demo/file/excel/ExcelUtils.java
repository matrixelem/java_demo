package com.example.java_demo.file.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelUtils {

    public static void main(String[] args) throws Exception {
        File file = new File("D:\\tmp\\新建文件夹 (5)\\excel2.xls");
        readExcel(file, 2, 0, 15, 0);
    }


    //cellNum=15

    public static void readExcel(File file, int startRow, int startCol, int cellNum, int sheetNo) throws Exception {
        List<String[]> data = new ArrayList<>();
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = wb.getSheetAt(sheetNo);
        int rowNum = sheet.getLastRowNum() + 1; //行号从0开始，取得最后一行的行号
        for (int i = startRow; i < rowNum; i++) {
            HSSFRow row = sheet.getRow(i);
            if (null == row) {//跳过空行
                continue;
            }
            //创建和列数一致的数据组，存每列值
            String[] rowArray = new String[cellNum];
            for (int j = startCol; j < cellNum + startCol; j++) {
                String cellValue = "";
                try {
                    HSSFCell cell = row.getCell(Short.parseShort(j + "")); //从0开始获取单元格数据
                    if (null != cell) {
                        // 判断excel单元格内容的格式
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                //判断是否为日期
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                                        cellValue = LocalDateTime.
//                                        fromDateFields(cell.getDateCellValue()).toString("yyyy-MM-dd HH:mm:ss");
                                } else {//数字
                                    //解决科学计数法和小数点问题
                                    DataFormatter formatter = new DataFormatter();
                                    cellValue = formatter.formatCellValue(cell);
                                }
                                break;
                            case STRING://字符型
                                cellValue = cell.getStringCellValue();
                                break;
                            case FORMULA://公式型
                                cellValue = cell.getNumericCellValue() + "";
                                break;
                            case BLANK:
                                cellValue = "";
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case ERROR:
                                cellValue = String.valueOf(cell.getErrorCellValue());
                                log.error("表格数据错误，原因：{}", cellValue);
                                break;
                        }
                    }
                } catch (Exception e) {
                    log.error("解析[{}]行[{}]列异常，原因：{}", i, j, e);
                }
                //从数组下标0开始赋值
                rowArray[j - startCol] = null == cellValue ? "" : cellValue.trim();
            }
            data.add(rowArray);
        }

        System.out.println("sdsd");

    }
}
