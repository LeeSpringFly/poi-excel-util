# poi-excel-util
POI Excel 小工具

使用 ExcelColumn 注解 POJO 属性
使用 ExcelUtil 操作 Excel 

POJO:
@ExcelColumn(name = "NAME", notNull = true, description = "姓名")
private String name;

UTIL:
ExcelUtil<Person> excelUtil = new ExcelUtil<>();
excelUtil.importByExcel(inputStream, Person.class);

EXAMPLE:
PoiExcelUtilApplicationTests.java

