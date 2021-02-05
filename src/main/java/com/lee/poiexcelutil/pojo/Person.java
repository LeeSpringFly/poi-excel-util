package com.lee.poiexcelutil.pojo;

import com.lee.poiexcelutil.common.annotations.ExcelColumn;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    @ExcelColumn(name = "NAME", notNull = true, description = "姓名")
    private String name;

    @ExcelColumn(name = "BIRTH", notNull = true, description = "生日")
    private Date birth;

    @ExcelColumn(name = "HEIGHT", description = "身高")
    private Integer height;
}
