package com.lee.poiexcelutil;

import com.lee.poiexcelutil.pojo.Person;
import com.lee.poiexcelutil.pojo.Person2;
import com.lee.poiexcelutil.pojo.StatusEnum;
import com.lee.poiexcelutil.service.ExcelDemoService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

    @Test
    void easyPoiExportExcel() throws IOException {
        service.exportExcelByEasyPoi(new ArrayList<Person2>() {{
            add(Person2.builder().id(1L).name("测试名称_01").birth(new Date()).height(198).status(StatusEnum.s).build());
            add(Person2.builder().id(2L).name("测试名称_02").birth(new Date()).height(188).status(StatusEnum.s).build());
            add(Person2.builder().id(3L).name("测试名称_03").birth(new Date()).height(178).status(StatusEnum.s).build());
            add(Person2.builder().id(4L).name("测试名称_04").birth(new Date()).height(168).status(StatusEnum.s).build());
        }});
    }

    @Test
    void easyPoiImportExcel() throws Exception {
        List<Person2> person2List = service.importExcelByEasyPoi();
        person2List.stream().forEach(System.out::println);
    }

}
