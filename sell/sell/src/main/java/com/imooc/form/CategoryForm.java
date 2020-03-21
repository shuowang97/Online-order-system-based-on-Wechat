package com.imooc.form;

import lombok.Data;

@Data
public class CategoryForm {
    private Integer categoryId;
    //    类目名字
    private String categoryName;
    //    类目类型  此处编号不等于id
    private Integer categoryType;
}
