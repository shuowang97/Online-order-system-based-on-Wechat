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
                        <form role="form" method="post" action="/sell/seller/product/save">
                            <div class="form-group">
                                <label>名称</label>
                                <input name="productName" type="text" class="form-control" value="${(productInfo.productName)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>价格</label>
                                <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>库存</label>
                                <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>描述</label>
                                <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>图片</label>
                                <img height="100" width="100" src="${(productInfo.productIcon)!""}" alt="">
                                <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)!""}"/>
                            </div>
                            <div class="form-group">
                                <label>类目</label>
                                <select name="categoryType" class="form-control">
                                    <#list productCategoryList as category>
                                        <option value="${category.categoryType}"
                                            <#if (productInfo.categoryType)?? && (category.categoryType == productInfo.categoryType)>
                                                    selected
                                            <#--这里??代表存在的意思 如果productinfo被传入并且商品类目类型相同才选中显示 不设置 默认所有商品显示第一个类别-->
                                            </#if>
                                        >${category.categoryName}
                                        </option>
                                    </#list>
                                </select>
                            </div>
                            <#--productId在此处为隐藏字段，在修改页面中不显示 但是提交时需要使用productId来调用Save方法-->
                            <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
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


