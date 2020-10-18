//查询所有订单，并将订单编号生成下拉框形式，供管理员选择
$(function () {
    $.post("/smartfactory/searchallorderformxxx", null, function (data) {
        if (data) {
            //遍历数组
            for (var i = 0; i < data.length; i++) {
                //获取到一个工单信息
                var form = data[i];
                //动态生成页面表单,追加到下拉框中
                $("<option></option>").html("O" + form.orderid).val(form.orderid).appendTo("#orderid");
            }
        }
    }, "json");
});
//点击查询按钮，动态分页查询订单表
$(function () {
    $("#selectorder").click(function () {
        $.post("/smartfactory/searchorderinfoxxx", $("[name]").serialize(), function (data) {
            if (data && data.size > 0) {
                $("#total").html(data.total);
                $("#pages").html(data.pages);
                $("#curpage").html(data.pageNum);
                $("#newodtable").hide();
                $("#selectordertable tr:gt(0)").remove();
                for (var i = 0; i < data.list.length; i++) {
                    var otr = $("<tr></tr>");
                    var orderinfo = data.list[i];
                    var orderotd = $("<td></td>").html("O" + orderinfo.orderid).val(orderinfo.orderid).appendTo(otr);
                    $("<td></td>").html(orderinfo.ofsou).appendTo(otr);
                    $("<td></td>").html(orderinfo.gname).appendTo(otr);
                    $("<td></td>").html(orderinfo.gnum).appendTo(otr);
                    $("<td></td>").html(orderinfo.comnum).appendTo(otr);
                    $("<td></td>").html(orderinfo.oendtime).appendTo(otr);
                    $("<td></td>").html(orderinfo.ofcond).appendTo(otr);
                    var capacityotd = $("<td></td>").appendTo(otr);
                    $("<td></td>").html(orderinfo.note).appendTo(otr);
                    var handleotd = $("<td></td>").appendTo(otr);
                    //插入产能信息,如果订单状态为未接单则显示产能，否则显示--  开始
                    if (orderinfo.ofcond == "待接单") {
                        var Otd = $("<td></td>").appendTo(otr);
                        $("<input type='button' class='a'>").click(function () {
                            var oBtn = $(this);
                            $.post("/smartfactory/searchcapacitybyorderid", "orderid=" + orderinfo.orderid, function (data) {
                                if (data) {
                                    oBtn.parent().parent().find("td:eq(7)").html(data);
                                }
                            }, "json");
                        }).appendTo(Otd);
                        Otd.hide();
                    } else {
                        capacityotd.html("---");
                    }
                    //插入产能信息结束

                    //根据订单状态插入操作按钮 开始
                    //所有状态都要添加查看详情的按钮
                    if (orderinfo.ofcond != "已拒单") {
                        $("<a href='javascript:void(0)' id='searchinfo' >查询详细</a> ").click(function () {
                            var xxx = $(this).parent().parent().find("td:eq(6)").html();
                            //如果订单已完成或者生产中，则跳转到工单管理模块 
                            if (xxx == "已完成" || xxx == "生产中") {
                                window.location.href = "/smartfactory/bfmain.html";
                            }
                            //如果订单待接单状态则跳转到商品界面
                            else if (xxx == "待接单") {
                                window.location.href = "/smartfactory/goodsearch.html";
                            }
                            //如果订单已接单状态则跳转到计划表界面
                            else if (xxx == "已接单") {
                                window.location.href = "/smartfactory/plan2.html";
                            }

                        }).appendTo(handleotd);
                    }
                    //如果是待接单，则添加接单和拒单按钮
                    if (orderinfo.ofcond == "待接单") {
                        $("<a href='javascript:void(0)' id='accept' >接单</a>").click(function () {
                            var oid = $(this).parent().parent().find("td:eq(0)").val();
                            var num = $(this).parent().parent().find("td:eq(3)").html();
                            var cap1 = $(this).parent().parent().find("td:eq(7)").html();
                            if (num * 1 > cap1 * 1) {
                                var isokxx = confirm("当前产能不足，确定要接受订单吗？");
                                if (isokxx) {
                                    $.post("/smartfactory/modorderconditionxxx", "orderid=" + oid + "&ofcond=已接单", function (data) {
                                        if (data == "true") {
                                            alert("已成功接受订单！");
                                        } else {
                                            alert("接受订单失败");
                                        }
                                    }, "text");
                                    $("#selectordershow").click();
                                }
                            } else {
                                $.post("/smartfactory/modorderconditionxxx", "orderid=" + oid + "&ofcond=已接单", function (data) {
                                    if (data == "true") {
                                        alert("已成功接受订单！");
                                    } else {
                                        alert("接受订单失败");
                                    }
                                    $("#selectordershow").click();
                                }, "text");
                            }


                        }).appendTo(handleotd);
                        $("<a href='javascript:void(0)' id='reject'>拒单</a>").click(function () {
                            var oid = $(this).parent().parent().find("td:eq(0)").val();

                            if ($(this).html() == "拒单") {
                                var otd2 = $(this).parent().parent().find("td:eq(8)");
                                otd2.empty();
                                $("<input type='text'placeholder='在此处填写备注'>").css("width", "150px").appendTo(otd2);
                                $(this).html("确定");
                            } else if ($(this).html() == "确定") {
                                var notexxx = $(this).parent().parent().find("td:eq(8) input").val();
                                if (!notexxx) {
                                    alert("请填写备注！")
                                } else {
                                    $.post("/smartfactory/modorderconditionaddnotexxx", "orderid=" + oid + "&ofcond=已拒单" + "&note=" + notexxx, function (data) {
                                        if (data == "true") {
                                            alert("已成功拒单订单！");
                                        } else {
                                            alert("拒绝订单失败");
                                        }
                                        $("#selectordershow").click();
                                    }, "text");
                                }
                            }

                        }).appendTo(handleotd);
                    }
                    //如果是生产中，添加完成订单按钮
                    if (orderinfo.ofcond == "生产中") {
                        $("<a href='javascript:void(0)' id='finish' >完成订单</a>").click(function () {
                            var oid = $(this).parent().parent().find("td:eq(0)").val();
                            var tnum = $(this).parent().parent().find("td:eq(3)").html();
                            var cnum1 = $(this).parent().parent().find("td:eq(4)").html();
                            if (tnum * 1 > cnum1 * 1 && $(this).html() == "完成订单") {
                                var isokxx = confirm("订单完成数量未达标，确定要完成订单吗？");
                                if (isokxx) {
                                    var otd2 = $(this).parent().parent().find("td:eq(8)");
                                    otd2.empty();
                                    $("<input type='text'placeholder='在此处填写备注'>").css("width", "150px").appendTo(otd2);
                                    $(this).html("确定");
                                }
                            } else if ($(this).html() == "确定") {
                                var notexxx = $(this).parent().parent().find("td:eq(8) input").val();
                                if (!notexxx) {
                                    alert("请填写备注！")
                                } else {
                                    $.post("/smartfactory/modorderfinishxxx", "orderid=" + oid + "&ofcond=已完成" + "&note=" + notexxx, function (data) {
                                        if (data == "true") {
                                            alert("已成功完成订单！");
                                        } else {
                                            alert("完成订单失败");
                                        }
                                        $("#selectordershow").click();
                                    }, "text");
                                }
                            } else {
                                var successnote = "订单完成数量已达标";
                                $.post("/smartfactory/modorderfinishxxx", "orderid=" + oid + "&ofcond=已完成" + "&note=" + successnote, function (data) {
                                    if (data == "true") {
                                        alert("已成功完成订单！");
                                    } else {
                                        alert("完成订单失败");
                                    }
                                    $("#selectordershow").click();
                                }, "text");
                            }
                        }).appendTo(handleotd);
                    }
                    //根据订单状态插入操作按钮 结束
                    otr.appendTo("#selectordertable");
                }
                $(".a").hide();
                $(".a").click();
                $("#selectodtable").show();
                $("#pagectrl").show();
                //判断上一页下一页的显示
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
            } else {
                alert("没有找到符合条件的订单，查询失败");
            }
        }, "json");
    });
});
//点击上一页下一页进行跳转
$(function () {
    $("#prePage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum - 1);
        $("#selectorder").click();
    });

    $("#nextPage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum * 1 + 1);
        $("#selectorder").click();
    });
    $("#goBtn").click(function () {
        var gopage = $("#gopage").val();
        if (gopage) {

            if (gopage * 1 < 1) {
                gopage = 1;
            }
            var pages = $("#pages").html();
            if (gopage * 1 > pages * 1) {
                gopage = pages;
            }
            $("#pageNum").val(gopage * 1);
            var pagenum = $("#pageNum").val();
            $("#selectorder").click();
        }
    });
});
//新建订单
//点击新建报工单按钮，创建新的报工单
$(function () {
    $("#neworder").click(function () {
        //modal开始
        $("#modalneworder").click();
        $("#searchallgoodxxx").click();
        $("#ofsource").val("");
        $("#gnamesssxx").val("");
        $("#gnumberxxx").val("");
        $("#endtimexxx").val("");
        $("#gnamesssxx option:gt(0)").remove();
        //modal结束



        //        $("#pagectrl").hide();
        //        $("#selectodtable").hide();
        //        $("#newordertable tr:gt(0)").remove();
        //        $("#newodtable").show();
        //        var otr11 = $("<tr></tr>");
        //        //订单来源
        //        var otdd1 = $("<td></td>").appendTo(otr11);
        //        $("<input type='text' placeholder='请输入订单来源'/ class='inputxxx'>").appendTo(otdd1);
        //        //产品名称
        //        var otdd2 = $("<td></td>").appendTo(otr11);
        //        //添加选择产品的下拉框
        //        $("<select name='gnamesss' id='gnamesss'><option value='0'>请输入产品名称</option></select>").appendTo(otdd2);
        //        $.post("/smartfactory/searchallgoodxxx", null, function (data) {
        //            if (data) {
        //                for (var i = 0; i < data.length; i++) {
        //                    var goodssssss = data[i];
        //                    $("<option></option>").html(goodssssss.gname).val(goodssssss.gname).appendTo("#gnamesss");
        //                }
        //            }
        //        }, "json");
        //        //产品数量
        //        var otdd3 = $("<td></td>").appendTo(otr11);
        //        $("<input type='number'min=1 placeholder='请输入产品数量' class='inputxxx'/>").appendTo(otdd3);
        //        //订单结束时间
        //        var otdd4 = $("<td></td>").appendTo(otr11);
        //        $("<input type='text' placeholder='例:2020-07-20' class='inputxxx'/>").appendTo(otdd4);
        //        //确认按钮
        //        var submitssss = $("<td></td>").appendTo(otr11);
        //        $("<a href='javascript:void(0)' id='submit'></a>").click(function () {
        //            //点击提交按钮，添加新的报工单进入数据库
        //            var sourcea = $(this).parent().parent().find("td:eq(0) input").val();
        //            var gnamesssa = $(this).parent().parent().find("#gnamesss").val();
        //            var gnumsssa = $(this).parent().parent().find("td:eq(2) input").val();
        //            var etimea = $(this).parent().parent().find("td:eq(3) input").val();
        //
        //            if (gnamesssa == 0) {
        //                alert("请选择产品名称！");
        //                return;
        //            }
        //            if (!gnumsssa) {
        //                alert("请填写产品数量！");
        //                return;
        //            }
        //            if (!etimea) {
        //                alert("请填写订单结束日期！");
        //                return;
        //            }
        //            var curid=$("#curid").val();
        //            $.post("/smartfactory/addneworderxxx", "ofsou=" + sourcea + "&gname=" + gnamesssa + "&gnum=" + gnumsssa + "&oendtime=" + etimea+"&userid="+curid,
        //                function (data) {
        //                   if(data=="true"){
        //                       alert("新建订单成功！");
        //                   }else{
        //                       alert("新建订单失败！");
        //                   }
        //                $("#selectordershow").click();
        //                }, "text");
        //
        //        }).html("确定").css("margin","center").appendTo(submitssss);
        //        otr11.appendTo("#newordertable");
    });
});
//隐藏按钮，使每次查询都回到第一页
$(function () {
    $("#selectordershow").click(function () {
        $("#pageNum").val(1);
        $("#selectorder").click();
    });
});
//获得当前用户的id
$(function(){
    $.post("/smartfactory/getcuruser",null,function(data){
        if(data){
            $("#curid").val(data.userid);
        }
    },"json");
});


//新建订单动画开始
$(function () {
    $("#sumbitorder").click(function () {
        //判断是否在前面加0
        function getNow(s) {
            return s < 10 ? '0' + s : s;
        }

        var myDate = new Date();

        var year = myDate.getFullYear(); //获取当前年
        var month = myDate.getMonth() + 1; //获取当前月
        var date = myDate.getDate(); //获取当前日
        var now = year + '-' + getNow(month) + "-" + getNow(date);
        var source = $("#ofsource").val();
        var gname = $("#gnamesssxx").val();
        var gnum = $("#gnumberxxx").val();
        var endtime = $("#endtimexxx").val();
        $("#endtimexxx").show();
        var curid = $("#curid").val();
        if (gname == 0) {
            alert("请选择产品名称！");
            return;
        }
        if (!gnum) {
            alert("请填写产品数量！");
            return;
        }
        if (gnum * 1 < 0) {
            alert("请输入正确的产品数量！");
            return;
        }
        if (!endtime) {
            alert("请填写订单结束日期！");
            return;
        }
        if(now>=endtime){
            alert("结束日期不能小于等于当前时间！");
            return;
        }
        $.post("/smartfactory/addneworderxxx", "ofsou=" + source + "&gname=" + gname + "&gnum=" + gnum + "&oendtime=" + endtime + "&userid=" + curid,
            function (data) {
                if (data == "true") {
                    alert("新建订单成功！");
                } else {
                    alert("新建订单失败！");
                }
                $("#selectordershow").click();
                $("#closemodal").click();
            }, "text");

    });
});
//新建动画结束
//获取产品名称下拉框开始
$(function () {
    $("#searchallgoodxxx").click(function () {
        $.post("/smartfactory/searchallgoodxxx", null, function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    var goodssssss = data[i];
                    $("<option></option>").html((i + 1) + "." + goodssssss.gname).val(goodssssss.gname).appendTo("#gnamesssxx");
                }
            }
        }, "json");
    });
});
//结束
