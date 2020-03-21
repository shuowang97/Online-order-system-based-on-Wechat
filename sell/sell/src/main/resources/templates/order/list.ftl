<#--《br》是换行操作-->
<!--suppress ALL -->

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
                                        <th>订单id</th>
                                        <th>姓名</th>
                                        <th>手机号</th>
                                        <th>地址</th>
                                        <th>金额</th>
                                        <th>订单状态</th>
                                        <th>支付状态</th>
                                        <th>创建时间</th>
                                        <th colspan="2">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list orderDTOPage.content as orderDTO>
                                    <tr>
                                        <td>${orderDTO.orderId}</td>
                                        <td>${orderDTO.buyerName}</td>
                                        <td>${orderDTO.buyerPhone}</td>
                                        <td>${orderDTO.buyerAddress}</td>
                                        <td>${orderDTO.orderAmount}</td>
                                        <td>${orderDTO.getOrderStatusEnum().msg}</td>
                                        <td>${orderDTO.getPayStatusEnum().msg}</td>
                                        <td>${orderDTO.createTime}</td>
                                        <td>
                                            <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                                        </td>
                                        <td>
                                            <#if orderDTO.getOrderStatusEnum().msg == "新下单">
                                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                                        <a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a>
                                    </li>
                                </#if>

                                <#--开始 页数过多 超过10页-->
                                <#if orderDTOPage.getTotalPages() gte 10>
                                    <#if currentPage lte orderDTOPage.getTotalPages()/2>
                                        <#list 1..currentPage as index_show>
                                            <#if currentPage == index_show>
                                                <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                    <a href="#">${index_show}</a>
                                                </li>
                                                <li class="disabled">
                                                    <a href="#">...</a>
                                                </li>
                                                <li>
                                                    <a href="/sell/seller/order/list?page=${orderDTOPage.getTotalPages()}&size=${size}">${orderDTOPage.getTotalPages()}</a>
                                                </li>
                                            <#else> <#--如果不相等，把不相等的标签显示出来-->
                                                <li>
                                                    <a href="/sell/seller/order/list?page=${index_show}&size=${size}">${index_show}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>

                                    <#if currentPage gt orderDTOPage.getTotalPages()/2>
                                        <#list currentPage..orderDTOPage.getTotalPages() as index_show>
                                            <#if currentPage == index_show>
                                                <li>
                                                    <a href="/sell/seller/order/list?page=1&size=${size}">1</a>
                                                </li>
                                                <li class="disabled">
                                                    <a href="#">...</a>
                                                </li>
                                                <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                    <a href="#">${index_show}</a>
                                                </li>
                                            <#else>
                                                <li>
                                                    <a href="/sell/seller/order/list?page=${index_show}&size=${size}">${index_show}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                    </#if>

                                <#else> <#--此时页数不超过10页的情况-->
                                <#--页码-->
                                    <#list 1..orderDTOPage.getTotalPages() as index>

                                        <#if currentPage == index>
                                            <li class="disabled">   <#--如果当前页等于index 置为灰色-->
                                                <a href="#">${index}</a>
                                            </li>
                                        <#else>
                                            <li>
                                                <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                            </li>
                                        </#if>

                                    </#list>

                                </#if>

                                <#--下一页-->
                                <#if currentPage gte orderDTOPage.getTotalPages()>
                                    <li class="disabled">
                                        <a href="#">下一页</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a>
                                    </li>
                                </#if>

                                </ul>
                            </div>

                        </div>
                    </div>
            </div>
        </div>
    </div>

<#--触发弹窗-->

<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">
                    提醒
                </h4>
            </div>
            <div class="modal-body">
                您有新订单
            </div>
            <div class="modal-footer">
                <#--点关闭时 关闭音乐-->
                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <#--点击提交后 刷新当前页面 查看新订单-->
                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新订单</button>
            </div>
        </div>
    </div>
</div>


<#--弹窗播放音乐-->  <#--loop循环播放-->
<audio id="notice" loop="loop"
    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />
</audio>

<#--引入jquery-->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<#--引入bootstrap-->
<script src="https://cdn.bootcss.com/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <#--这里写的是JS前端代码-->
<script>
        var websocket = null;
        if('WebSocket' in window){
            //                          这里不再是http协议 是websock协议！！！
            websocket = new WebSocket('ws://127.0.0.1:8080/sell/webSocket');
        }else{
            alert('该浏览器不支持websocket');
        }

        websocket.onopen = function (event) {
            console.log("连接成功");
        }
        websocket.onclose = function (event) {
            console.log("连接关闭");
        }
        
        websocket.onmessage = function (event) {
            console.log("收到消息", event.data);
            //弹窗 or 播放音乐  ibootstramp
            $('#myModal').modal('show');
            /*播放音乐  根据id得到元素 调用play方法*/
            document.getElementById('notice').play();

        }

        websocket.onerror = function (event) {
            alert("websocket通信发生错误");
        }

        //窗口关闭时，关闭webso
        window.onbeforeunload = function () {
            websocket.close();
        }


</script>
</body>
</html>


