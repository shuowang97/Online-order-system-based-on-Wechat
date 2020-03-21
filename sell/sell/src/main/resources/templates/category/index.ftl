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
                        <form role="form" method="post" action="/sell/seller/category/save">
                            <div class="form-group">
                                <label>名字</label>
                                <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>type</label>
                                <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)!""}"/>
                            </div>
                            <input hidden name="categoryId" type="number" value="${(category.categoryId)!''}">
                            <button type="submit" class="btn btn-default">提交</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>


