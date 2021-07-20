package com.lee.poiexcelutil.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ExcelTarget("")
public class Person2 extends BaseEntity implements Serializable {

    @ExcelIgnore
    private Long id;

    @Excel(name = "姓名", orderNum = "1")
    private String name;

    @Excel(name = "生日", orderNum = "2", format = "yyyy年MM月dd日")
    private Date birth;

    @Excel(name = "身高", orderNum = "3")
    private Integer height;

//    @JsonSerialize(using = EnumJsonSerialize.class)
    @Excel(name = "状态", orderNum = "4", enumExportField = "cnName", enumImportMethod = "getByCnName")
    private StatusEnum status;
}
