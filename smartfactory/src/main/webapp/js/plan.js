$(function () {
    $("#submitxxx").click(function () {
        $("#yes").click();
        //        $("#close").click();
    });
});
$(function () {
    $("#add").click(function () {
        //新建按钮功能
        $("#resultdiv").hide();
        $("#pagectrl").hide();
        $("#addtable tr:gt(0)").hide();
        $("#xiala1").remove();
        $("#yes").remove();
        $("#starttime").val("");
        $("#endtime").val("");
        //        $("#starttime").hide();
        //        $("#endtime").hide();
        $("#adddiv").show();

        var oTr = $("<tr></tr>");
        var oText1 = $("<td></td>").appendTo(oTr);
        $("<select name='xiala1' id='xiala1' class='form-control'  ><option value='0'>--请选择--</option></select>").appendTo(oText1);
        var orderid = oText1.html();
        var orderid2 = oText1.val();
        var gname = $("<td></td>").appendTo(oTr);
        var gnum = $("<td></td>").appendTo(oTr);
        var oendtime = $("<td></td>").appendTo(oTr);
        var starttime = $("<td></td>").appendTo(oTr);
        var starttime1 = starttime.val();
        $("#starttime").show().appendTo(starttime);
        var endtime = $("<td></td>").appendTo(oTr)
        $("#endtime").show().appendTo(endtime);



        var pcond = $("<td></td>").html("未启动").appendTo(oTr);
        var op = $("<td></td>").appendTo(oTr).hide();
        $("<input type='button' value='确认' id='yes' class='btn btn-default'>").click(function () {
            var endtime1 = $(this).parent().parent().find("td:eq(5) input").val();
            var starttime1 = $(this).parent().parent().find("td:eq(4) input").val();
            var orderid2 = $(this).parent().parent().find("td:eq(0) select").val();

            if (orderid2 == "0") {
                alert("请选择订单");
                return;
            }
            if (!starttime1) {
                alert("请填写开始日期");
                return;
            }
            if (!endtime1) {
                alert("请填写结束日期");
                return;
            }
            if (starttime1 >= endtime1) {
                alert("结束日期不能小于开始日期");
                return;
            }
            $.post("/smartfactory/addplan", "orderid=" + orderid2 + "&starttime=" + starttime1 + "&endtime=" + endtime1, function (data) {
                if (data == "true") {
                    alert("成功新建");
                    $("#searchBtn").click();
                } else {
                    alert("新建失败");

                }
                $("#close").click();
            }, "text")

            //将新建之后的order表中的状态改为生产中

            $.post("/smartfactory/modofcondbyorderid", "orderid=" + orderid2, function (data) {
                if (data == "true") {
                    alert("订单修改完成");
                } else {
                    alert("订单修改失败");
                }


            }, "text")

        }).appendTo(op).hide();
        oTr.appendTo("#addtable");
        var oBtn2 = $(this);
        //选择已接单的订单
        $.post("/smartfactory/searchorderofcond", null, function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    var order = data[i];
                    var oText1 = oBtn2.parent().parent().find("td:eq(0)");
                    $("<option></option>").val(order.orderid).html(order.orderid).appendTo("#xiala1");



                    $("#xiala1").change(function () {
                        var orderid2 = $("#xiala1").val();

                        //将选中订单的产品。。显示出来
                        $.post("/smartfactory/searchorderbyorderid", "orderid=" + orderid2, function (data) {
                            var oText2 = $("#xiala1").parent().parent().find("td:eq(1)");
                            oText2.html(order.gname);
                            var oText3 = $("#xiala1").parent().parent().find("td:eq(2)");
                            oText3.html(order.gnum);
                            var oText4 = $("#xiala1").parent().parent().find("td:eq(3)");
                            oText4.html(order.oendtime);
                        }, "json")

                    });

                }
            }
        }, "json")
    });




});



$(function () {
    //添加点击查询按钮的方法
    $("#searchBtn").click(function () {
        //将新建的表隐藏
        $("#adddiv").hide();
        //查询的ajax方法
        $.post("/smartfactory/searchplan", $("[name]").serialize(), function (data) {
            if (data) {
                //共x页...赋值
                $("#total").html(data.total);
                $("#pages").html(data.pages);
                $("#curpage").html(data.pageNum);
                $("#resulttable tr:gt(0)").hide();
                //获取数据
                for (var i = 0; i < data.list.length; i++) {
                    var planinfo = data.list[i];
                    //新建一行
                    oTr = $("<tr></tr> ");
                    //将所有属性添加到对应列里
                    $("<td></td>").html(planinfo.pid).appendTo(oTr);
                    $("<td></td>").html(planinfo.orderid).appendTo(oTr);
                    $("<td></td>").appendTo(oTr);
                    $("<td></td>").appendTo(oTr);
                    $("<td></td>").appendTo(oTr);
                    $("<td></td>").html(planinfo.starttime).appendTo(oTr);
                    $("<td></td>").html(planinfo.endtime).appendTo(oTr);
                    $("<td></td>").html(planinfo.pcond).appendTo(oTr);
                    oTd = $("<td></td>").appendTo(oTr);
                    //添加一个查询orderid的按钮，方便找他的对应列，添加点击他的功能
                    $("<input type='button' value='查id' class='a'>").click(function () {
                        //获得orderid
                        var orderid = $(this).parent().parent().find("td:eq(1)").html();
                        var Obtn = $(this);
                        //将order表中属性添加进表格的ajax方法
                        $.post("/smartfactory/searchorderpid", "orderid=" + orderid, function (data) {
                            for (var p = 0; p < data.length; p++) {
                                var orderinfo = data[p];


                                Obtn.parent().parent().find("td:eq(2)").html(orderinfo.gname);
                                Obtn.parent().parent().find("td:eq(3)").html(orderinfo.gnum);
                                Obtn.parent().parent().find("td:eq(4)").html(orderinfo.oendtime);


                            }



                        }, "json")


                    }).appendTo(oTd);
                    if (planinfo.pcond == "未启动") {
                        //启动按钮
                        $("<input type='button' value='删除'  class='btn btn-default'>").click(function () {
                            var isOK = confirm("是否确定删除");
                            if (isOK) {

                                var pid = $(this).parent().parent().find("td:eq(0)").html();
                                //通过pid来删除
                                $.post("/smartfactory/delplan", "pid=" + pid, function (data) {
                                    if (data) {
                                        alert("删除成功");
                                        $("#searchBtn").click();

                                    } else {

                                        alert("删除失败")
                                    }




                                }, "text")
                            }

                        }).appendTo(oTd);

                    }



                    if (planinfo.pcond == "未启动") {
                        //添加编辑按钮
                        $("<input type='button' value='编辑'  class='btn btn-default'>").click(function () {

                            //有两个状态一个是编辑一个是确定
                            if ($(this).val() == "编辑") {
                                //获取第六列的数据格
                                var oTd1 = $(this).parent().parent().find("td:eq(5)")
                                //用一个变量接收这格子里的数据
                                var starttime = oTd1.html();
                                //将格子里的数据变空
                                oTd1.empty();
                                //用文本框的格式将原来格里的数据再写回去
                                $("#starttime").show().val(starttime).appendTo(oTd1);

                                var oTd2 = $(this).parent().parent().find("td:eq(6)")
                                var endtime = oTd2.html();
                                oTd2.empty();
                                $("#endtime").show().val(endtime).appendTo(oTd2);

                                //将编辑按钮改为确定按钮
                                $(this).val("确定");
                            } else if ($(this).val() == "确定") {
                                //获得在第六行中开始时间的文本框对象
                                var oText1 = $(this).parent().parent().find("td:eq(5) input");
                                //获得其中的数据
                                var starttime = oText1.val();
                                //判断有没有写数据
                                if (!starttime) {
                                    alert("请输入计划开始日期");
                                    oText1.focus();
                                    return;

                                }
                                var oText2 = $(this).parent().parent().find("td:eq(6) input");
                                //获得其中的数据
                                var endtime = oText2.val();
                                //判断有没有写数据
                                if (!endtime) {
                                    alert("请输入计划结束日期");
                                    oText2.focus();
                                    return;

                                }
                                if(starttime>=endtime){
                                    alert("结束日期不能小于等于开始日期");
                                    return;
                                }
                                //获取所有属性的数据，starttime和endtime上面有了
                                var pid = $(this).parent().parent().find("td:eq(0)").html();
                                var orderid = $(this).parent().parent().find("td:eq(1)").html();

                                var pcond = $(this).parent().parent().find("td:eq(7)").html();
                                var oBtn = $(this);
                                //修改表中数据的方法
                                $.post("/smartfactory/modplan", "pid=" + pid + "&orderid=" + orderid + "&starttime=" + starttime + "&endtime=" + endtime + "&pcond=" + pcond, function (data) {

                                    if (data == "true") {

                                        //找到开始时间对应的表格
                                        //                var oTd4=oBtn.parent().parent().find("td:eq(5)");
                                        //                //找到其中的文本框
                                        //                var oText4 = oTd4.find("input");
                                        //                //获得其中的数据
                                        //                var starttime = oText4.val();
                                        //                //清空完再放回去
                                        //                oTd4.empty();
                                        //                oTd4.html(starttime);
                                        //                
                                        //                
                                        //                 var oTd5=oBtn.parent().parent().find("td:eq(6)");
                                        //                var oText5 = oTd5.find("input");
                                        //                var endtime = oText5.val();
                                        //                oTd5.empty();
                                        //                oTd5.html(endtime);
                                        //                oBtn.val("编辑");
                                        alert("修改成功");
                                        $("#searchBtn").click();
                                        $("#starttime").val("");
                                        $("#endtime").val("");

                                    } else {
                                        alert("修改失败");
                                    }
                                }, "text")


                            }



                        }).appendTo(oTd);

                    }


                    if (planinfo.pcond == "未启动") {
                        //启动按钮
                        $("<input type='button' value='启动' id='start' class='btn btn-default'>").click(function () {
                            var starttime = $(this).parent().parent().find("td:eq(5)").html();
                            var endtime = $(this).parent().parent().find("td:eq(6)").html();
                            var pid = $(this).parent().parent().find("td:eq(0)").html();
                            var orderid = $(this).parent().parent().find("td:eq(1)").html();

                            var pcond = $(this).parent().parent().find("td:eq(7)").html();
                            alert("确定要启动吗")
                            //通过pid查询对应行并改变状态（在后台已经改变了pcond）
                            $.post("/smartfactory/searchpcond", "pid=" + pid, function (data) {
                                if (data == "true") {
                                    alert("启动成功");
                                } else {
                                    alert("启动失败");
                                }
                                $("#searchBtn").click();
                            }, "text")

                        }).appendTo(oTd);








                    }


                    oTr.appendTo("#resulttable");
                }
                $(".a").hide();
                $(".a").click();
                $("#resultdiv").show();
                $("#pagectrl").show();

            } else {
                $("#resultdiv").hide();
                $("#pagectrl").hide();
                alert("没有查到数据");
            }
            //上一页下一页显示开始
            if (data.isFirstPage) {
                $("#prePage").hide();
                $("#prepage").show();
            } else {
                $("#prePage").show();
                $("#prepage").hide();
            }
            if (data.isLastPage) {
                $("#nextPage").hide();
                $("#nextpage").show();
            } else {
                $("#nextPage").show();
                $("#nextpage").hide();
            }
            //结束    


        }, "json");
    });
});


$(function () {
    //底下那个部分的功能
    $("#prePage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum - 1);
        $("#searchBtn").click();

    });

    $("#nextPage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum * 1 + 1);
        $("#searchBtn").click();

    });


    $("#goBtn").click(function () {
        var gopage = $("#gopage").val();
        if (gopage) {
            if (gopage < 1) {
                gopage = 1;

            }
            var pages = $("#pages").html();
            if (gopage > pages + 1) {
                gopage = pages;
            }

            $("#pageNum").val(gopage);
            $("#searchBtn").click();
        }
    });






});

//查询按钮，每次查询回到第一页
$(function () {
    $("#searchBtnshow").click(function () {

        $("#pageNum").val(1);
        $("#searchBtn").click();
    });
});
