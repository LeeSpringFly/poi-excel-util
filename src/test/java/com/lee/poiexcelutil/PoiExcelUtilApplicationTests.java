package com.lee.poiexcelutil;

import com.lee.poiexcelutil.pojo.Person;
import com.lee.poiexcelutil.service.ExcelDemoService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@SpringBootTest
class PoiExcelUtilApplicationTests {

    @Autowired
    private ExcelDemoService service;

    @Test
    void contextLoads() throws IOException, InvalidFormatException, InstantiationException, ParseException, IllegalAccessException {
        List<Person> personList = service.importExcel(new FileInputStream(ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/excel/template.xlsx")));
        personList.stream().forEach(System.out::println);
    }

}
