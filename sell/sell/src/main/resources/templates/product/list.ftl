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
                                        <th>商品id</th>
                                        <th>名称</th>
                                        <th>图片</th>
                                        <th>单价</th>
                                        <th>库存</th>
                                        <th>描述</th>
                                        <th>类目</th>
                                        <th>创建时间</th>
                                        <th>修改时间</th>
                                        <th colspan="2">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list productInfoPage.content as productInfo>
                                    <tr>
                                        <td>${productInfo.productId}</td>
                                        <td>${productInfo.productName}</td>
                                        <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                                        <td>${productInfo.productPrice}</td>
                                        <td>${productInfo.productStock}</td>
                                        <td>${productInfo.productDescription}</td>
                                        <td>
                                            <#list categoryList as category>
                                            <#if category.categoryType == productInfo.categoryType>
                                                ${category.categoryName}
                                            </#if>
                                            </#list>
                                        </td>

                                        <td>${productInfo.updateTime}</td>
                                        <td>${productInfo.createTime}</td>
                                        <td>
                                            <a href="/sell/seller/product/index?productId=${productInfo.productId}">修改</a>
                                        </td>
                                        <td>
                                                <#if productInfo.getProductStatusEnum().message == "上架">
                                                    <a href="/sell/seller/product/off_sale?productId=${productInfo.productId}">下架</a>
                                                </#if>
                                                <#if productInfo.getProductStatusEnum().message == "下架">
                                                    <a href="/sell/seller/product/on_sale?productId=${productInfo.productId}">上架</a>
                                                </#if>
                                        </td>

                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>

                        <#--分页模块-->
                            <div class="col-md-12 column">
                                <ul class="pagination pull-right">


                                <#--上一页-->
                                <#if currentPage lte 1>
                                    <li class="disabled">
                                        <a href="#">上一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a>
                                    </li>
                                </#if>

                                <#--开始 页数过多 超过10页-->
                                <#if productInfoPage.getTotalPages() gte 10>
                                    <#if currentPage lte productInfoPage.getTotalPages()/2>
                                        <#list 1..currentPage as index_show>
                                            <#if currentPage == index_show>
                                                <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                    <a href="#">${index_show}</a>
                                                </li>
                                                <li class="disabled">
                                                    <a href="#">...</a>
                                                </li>
                                                <li>
                                                    <a href="/sell/seller/product/list?page=${productInfoPage.getTotalPages()}&size=${size}">${productInfoPage.getTotalPages()}</a>
                                                </li>
                                            <#else> <#--如果不相等，把不相等的标签显示出来-->
                                                <li>
                                                    <a href="/sell/seller/product/list?page=${index_show}&size=${size}">${index_show}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>

                                    <#if currentPage gt productInfoPage.getTotalPages()/2>
                                        <#list currentPage..productInfoPage.getTotalPages() as index_show>
                                            <#if currentPage == index_show>
                                                <li>
                                                    <a href="/sell/seller/product/list?page=1&size=${size}">1</a>
                                                </li>
                                                <li class="disabled">
                                                    <a href="#">...</a>
                                                </li>
                                                <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                    <a href="#">${index_show}</a>
                                                </li>
                                            <#else>
                                                <li>
                                                    <a href="/sell/seller/product/list?page=${index_show}&size=${size}">${index_show}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>

                                <#else> <#--此时页数不超过10页的情况-->
                                <#--页码-->
                                    <#list 1..productInfoPage.getTotalPages() as index>

                                        <#if currentPage == index>
                                            <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                <a href="#">${index}</a>
                                            </li>
                                        <#else>
                                            <li>
                                                <a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a>
                                            </li>
                                        </#if>

                                    </#list>

                                </#if>

                                <#--下一页-->
                                <#if currentPage gte productInfoPage.getTotalPages()>
                                    <li class="disabled">
                                        <a href="#">下一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a>
                                    </li>
                                </#if>

                                </ul>
                            </div>

                        </div>
                    </div>
            </div>
        </div>
    </div>


    </body>
</html>


