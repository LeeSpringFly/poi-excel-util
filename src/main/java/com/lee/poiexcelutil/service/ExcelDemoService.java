package com.lee.poiexcelutil.service;

import com.lee.poiexcelutil.common.excel.v_3_9.ExcelUtil;
import com.lee.poiexcelutil.pojo.Person;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

@Service
public class ExcelDemoService {

    public List<Person> importExcel(InputStream inputStream) throws InvalidFormatException, InstantiationException, IllegalAccessException, ParseException, IOException {
        ExcelUtil<Person> excelUtil = new ExcelUtil<>();
        return excelUtil.importByExcel(inputStream, Person.class);
    }
}
