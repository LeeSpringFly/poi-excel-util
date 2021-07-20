package com.lee.poiexcelutil.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusEnum implements Serializable {
    d("标记删除"),
    s("保存");


    private String cnName;

    StatusEnum(String cnName) {
        this.cnName = cnName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getName() {
        return super.name();
    }

    public static StatusEnum getByCnName(String message){
        StatusEnum[] arr =  StatusEnum.values();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].cnName.equals(message)){
                return arr[i];
            }
        }
        return null;
    }
}
