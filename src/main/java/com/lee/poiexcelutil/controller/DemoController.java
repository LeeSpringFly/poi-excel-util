package com.lee.poiexcelutil.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.lee.poiexcelutil.pojo.Person2;
import com.lee.poiexcelutil.pojo.StatusEnum;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<Person2> personList = new ArrayList<Person2>() {{
            add(Person2.builder().id(1L).name("测试名称_01").birth(new Date()).height(198).status(StatusEnum.s).build());
            add(Person2.builder().id(2L).name("测试名称_02").birth(new Date()).height(188).status(StatusEnum.s).build());
            add(Person2.builder().id(3L).name("测试名称_03").birth(new Date()).height(178).status(StatusEnum.s).build());
            add(Person2.builder().id(4L).name("测试名称_04").birth(new Date()).height(168).status(StatusEnum.s).build());
        }};

        for (int i=0; i<personList.size(); i++) {
            personList.get(i).setTitle("标题_" + (i+1));
        }

        String fileName = "危险品化学品统计_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSSS").format(new Date()) +".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + fileName);
        ServletOutputStream os = response.getOutputStream();
        ExportParams exportParams = new ExportParams("计算机一班学生","学生");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Person2.class, personList);
        workbook.write(os);
    }
}
