package com.lee.poiexcelutil.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import com.lee.poiexcelutil.common.excel.v_3_9.ExcelUtil;
import com.lee.poiexcelutil.pojo.Person;
import com.lee.poiexcelutil.pojo.Person2;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.util.List;

@Service
public class ExcelDemoService {

    public List<Person> importExcel(InputStream inputStream) throws InvalidFormatException, InstantiationException, IllegalAccessException, ParseException, IOException {
        ExcelUtil<Person> excelUtil = new ExcelUtil<>();
        return excelUtil.importExcel(inputStream, Person.class);
    }

    public List<Person2> importExcelByEasyPoi() throws Exception {
        File file = new File("D:/testExcel.xls");
        FileInputStream in = null;
        in = new FileInputStream(file);
        return new ExcelImportService().importExcelByIs(in, Person2.class, new ImportParams(), false).getList();
    }

    public void exportExcelByEasyPoi(List<Person2> personList) throws IOException {
        ExportParams exportParams = new ExportParams("计算机一班学生","学生");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Person2.class, personList);
        new ExcelExportService().createSheet(workbook, exportParams, Person2.class, personList);
        File file = new File("D:/testExcel.xls");
        workbook.write(new FileOutputStream(file));
    }

}
