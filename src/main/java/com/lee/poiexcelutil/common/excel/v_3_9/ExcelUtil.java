package com.lee.poiexcelutil.common.excel.v_3_9;

import com.lee.poiexcelutil.common.annotations.ExcelColumn;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel 导入导出公共方法
 * Excel 数据格式
 * [Field1],[Field2],[Field3] ...
 * [Data1],[Data2],[Data3] ...
 *
 * @param <T> 模型类
 * @author Chun
 */
public class ExcelUtil<T> {

    /**
     * 导入 Excel 数据，解析成 List<T> 类型，只获取第一个 Sheet
     *
     * @param inputStream 文件的输入流
     * @param clazz
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ParseException
     */
    public List<T> importByExcel(InputStream inputStream, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, ParseException, InvalidFormatException {
        Workbook wb = WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(0);

        List<T> result = new ArrayList<>();
        List<Field> fieldArray = new ArrayList<>();
        fieldArray.addAll(Arrays.asList(clazz.getDeclaredFields()));
        fieldArray.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

        List<Field> fieldOrderList = new ArrayList<>();
        T t;

        for (Row row : sheet) {
            if (fieldOrderList.isEmpty()) {  // 映射模型属性顺序对应 Excel 标题顺序
                String title;
                for (Cell cell : row) {
                    title = cell.getStringCellValue();
                    for (Field field : fieldArray) {
                        if (field.getAnnotation(ExcelColumn.class) == null)  // 如果没有标记 ExcelColumn, 跳过
                            continue;
                        if (title.toUpperCase().equals(field.getAnnotation(ExcelColumn.class).name().toUpperCase())) {
                            fieldOrderList.add(field);
                            break;
                        }
                    }
                }
            } else {  // 如果已经获取标题列表，开始获取内容
                if (isRowEmpty(row))  // 无效数据行跳过
                    continue;

                t = clazz.newInstance();
                Field field;
                for (Cell cell : row) {  // 设置模型属性值
                    Cell cellTmp = row.getCell(19);

                    field = fieldOrderList.get(cell.getColumnIndex());
                    field.setAccessible(true);

                    if (field.getType() == String.class)
                        cell.setCellType(Cell.CELL_TYPE_STRING);

                    Object cellVal = getCellValue(cell);
                    mapperExcelToModel(field, cellVal, t);

                    
                }
                
                // model 属性非空验证
                for (Field fieldTmp : fieldOrderList) {
                    fieldTmp.setAccessible(true);
                    if (fieldTmp.getAnnotation(ExcelColumn.class).notNull()) {
                        if (fieldTmp.getType() == String.class && (fieldTmp.get(t) == null || StringUtils.isEmpty(fieldTmp.get(t).toString()))) {
                            throw new NullPointerException(fieldTmp.getAnnotation(ExcelColumn.class).description() + "不能为空值");
                        }

                        if (fieldTmp.getType() != String.class && fieldTmp.get(t) == null){
                            throw new NullPointerException(fieldTmp.getAnnotation(ExcelColumn.class).description() + "不能为空值");
                        }
                    }
                }
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Excel 单元格数据映射到 Model 属性
     * 如果单元格为 null，对应 Model 属性为 null
     *
     * @param field
     * @param cellVal
     * @param t
     * @throws IllegalAccessException
     * @throws ParseException
     */
    private void mapperExcelToModel(Field field, Object cellVal, T t) throws IllegalAccessException, ParseException {
        if (cellVal == null)
            return;

        if (field.getType() == cellVal.getClass()) {
            field.set(t, cellVal);
        } else {
            if (field.getType() == String.class)
                field.set(t, String.valueOf(cellVal));

            if (field.getType() == Integer.class && cellVal.getClass() == Double.class)
                field.set(t, Double.valueOf((Double) cellVal).intValue());

            if (field.getType() == Date.class)
                field.set(t, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(cellVal)));

            if (field.getType() == BigDecimal.class && cellVal.getClass() == Double.class) {
                field.set(t, BigDecimal.valueOf((Double) cellVal));
            }
        }
    }

    /**
     * 判断是否空行
     * @return
     */
    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }

        return true;
    }

    /**
     * 获取 Excel 值
     *
     * @param cell
     * @return
     */
    private Object getCellValue(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING)
            return cell.getStringCellValue().trim().replace(" ", "");

        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell))
                return cell.getDateCellValue();
            else
                return cell.getNumericCellValue();
        }

        return null;
    }

}
