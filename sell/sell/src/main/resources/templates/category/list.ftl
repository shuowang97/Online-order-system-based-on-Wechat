<#--《br》是换行操作-->
<html>
<#include "../common/header.ftl">
    <body>


    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-2 column">
                <#--边栏sidebar-->
                <#include "../common/nav.ftl">
            </div>
            <div class="col-md-6 column">
                <#--主要内容content-->
                    <div class="container">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                                <table class="table table-condensed">
                                    <thead>
                                    <tr>
                                        <th>类目id</th>
                                        <th>名字</th>
                                        <th>type</th>
                                        <th>创建时间</th>
                                        <th>修改时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list categoryList as category>
                                    <tr>
                                        <td>${category.categoryId}</td>
                                        <td>${category.categoryName}</td>
                                        <td>${category.categoryType}</td>
                                        <td>${category.createTime}</td>
                                        <td>${category.updateTime}</td>
                                        <td>
                                            <a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a>
                                        </td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </div>


    </body>
</html>


