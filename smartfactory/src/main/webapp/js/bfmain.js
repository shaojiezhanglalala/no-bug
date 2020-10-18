//查询所有工单，并将工单编号生成下拉框形式，供管理员选择
$(function () {

    $.post("/smartfactory/searchallform", null, function (data) {
        if (data) {
            //遍历数组
            for (var i = 0; i < data.length; i++) {
                //获取到一个工单信息
                var form = data[i];
                //动态生成页面表单,追加到下拉框中
                $("<option></option>").html(form.formid + "---" + "(EQP" + form.eqpid + ")" + "---" + "(" + form.gname + ")" + "---" + "(" + form.fcond + ")").val(form.formid).appendTo("#selfid");
            }
        }
    }, "json");

    //    $("#selfid").click();
});
//点击选择工单时，将工单的状态传入到页面的隐藏值fcond1中
$(function () {
    $("#selfid").click(function () {
        var fid1 = $("#selfid").val();
        $.post("/smartfactory/searchfconditionbyfid", "formid=" + fid1, function (data) {
            if (data) {
                $("#fcond1").val(data.fcond);
            }
        }, "json");
    });
});
//查询对应工单id下的所有报工单，分页查询
$(function () {
    $("#searchbtn").click(function () {
        $.post("/smartfactory/searchbforminfo", $("[name]").serialize(), function (data) {
            if (data && data.size > 0) {
                $("#total").html(data.total);
                $("#pages").html(data.pages);
                $("#curpage").html(data.pageNum);
                $("#bformtable").show();
                $("#bformtable tr:gt(0)").remove();
                $("#addbformtable").hide();
                for (var i = 0; i < data.list.length; i++) {
                    var otr = $("<tr></tr>");
                    var bform = data.list[i];
                    $("<td></td>").html(bform.bfid).appendTo(otr);
                    $("<td></td>").html(bform.fid).appendTo(otr);
                    $("<td></td>").html(bform.gname).appendTo(otr);
                    $("<td></td>").appendTo(otr);
                    $("<td></td>").appendTo(otr);
                    $("<td></td>").html(bform.pnum).appendTo(otr);
                    $("<td></td>").html(bform.qnum).appendTo(otr);
                    $("<td></td>").html(bform.stime).appendTo(otr);
                    $("<td></td>").html(bform.etime).appendTo(otr);
                    $("<td></td>").html(bform.note).appendTo(otr);
                    //插入工单状态开始
                    oTd = $("<td></td>").appendTo(otr);
                    $("<input type='button' value='查id' class='a'>").click(function () {
                        var fid = $(this).parent().parent().find("td:eq(1)").html();
                        var Obtn = $(this);
                        $.post("/smartfactory/searchfconditionbyfid", "formid=" + fid, function (data) {
                            if (data) {
                                Obtn.parent().parent().find("td:eq(4)").html(data.fcond);
                                Obtn.parent().parent().find("td:eq(3)").html("EQP" + data.eqpid);
                            }

                        }, "json");
                    }).appendTo(oTd);
                    oTd.hide();
                    //插入工单状态结束
                    //插入设备序列号开始
                    //                    oTd1 = $("<td></td>").appendTo(otr);
                    //                    $("<input type='button' class='b'>").click(function () {
                    //                        var fid1 = $(this).parent().parent().find("td:eq(1)").html();
                    //                        var Obtn1 = $(this);
                    //                        $.post("/smartfactory/searchequp", "formid=" + fid1, function (data) {
                    //                            if (data && data.length > 0) {
                    //                                var eq = "";
                    //                                for (var i = 0; i < data.length; i++) {
                    //                                    if (i < data.length - 1) {
                    //                                        eq = eq +"EQP"+ data[i].eqpid;
                    //                                        eq = eq + "、";
                    //                                    } else {
                    //                                        eq = eq +"EQP"+ data[i].eqpid;
                    //                                    }
                    //                                }
                    //                                Obtn1.parent().parent().find("td:eq(3)").html(eq);
                    //                            }
                    //
                    //                        }, "json");
                    //                    }).appendTo(oTd1);
                    //                    oTd1.hide();
                    //插入设备序列号结束
                    otr.appendTo("#bformtable");
                }
                $(".a").hide();
                $(".a").click();
                //                $(".b").hide();
                //                $(".b").click();
                $("#bftable").show();
                $("#pagectrl").show();
                //                $("#stimeddd").hide();
                //                $("#etimeddd").hide();
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
                alert("该工单暂无报工单记录，请先新建报工单。");
            }
        }, "json");
    });
});
//点击上一页下一页进行跳转
$(function () {
    $("#prePage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum - 1);
        $("#searchbtn").click();
    });

    $("#nextPage").click(function () {
        var pageNum = $("#pageNum").val();
        $("#pageNum").val(pageNum * 1 + 1);
        $("#searchbtn").click();
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
            $("#searchbtn").click();
        }
    });
});
//隐藏按钮，使每次查询都回到第一页
$(function () {
    $("#searchbtnshow").click(function () {
        $("#pageNum").val(1);
        $("#searchbtn").click();
    });
});
//点击新建报工单按钮，创建新的报工单
$(function () {
    $("#addbtn").click(function () {
        var formid11 = $("#selfid").val();
        var formcond = $("#fcond1").val();
        if (formid11 == 0) {
            alert("请选择工单号！");
            return;
        }
        if (formcond == "已完成") {
            alert("该工单已完成，无法进行报工！");
            $("#searchbtnshow").click();
            return;
        }
        $("#pagectrl").hide();
        $("#bformtable").hide();
        $("#addbformtable tr:gt(0)").hide();
        $("#addbformtable").show();
        $("#stimeddd").val("");
        $("#etimeddd").val("");
        var otr = $("<tr></tr>");
        //工单id
        var fid = $("<td></td>").val(formid11).html(formid11).appendTo(otr);
        //加工数量
        var pnum = $("<td></td>").appendTo(otr);
        $("<input type='number' min=0 placeholder='请输入加工数量'class='form-control'>").css("width", "150px").appendTo(pnum);
        //合格数量
        var qnum = $("<td></td>").appendTo(otr);
        $("<input type='number' min=0 placeholder='请输入合格数量'class='form-control'>").css("width", "150px").appendTo(qnum);
        //开始时间
        var stime = $("<td></td>").appendTo(otr);
        $("#stimeddd").show().appendTo(stime);
        //结束时间
        var etime = $("<td></td>").appendTo(otr);
        $("#etimeddd").show().appendTo(etime);
        //是否完成报工
        var isend = $("<td></td>").appendTo(otr);
        $("<span></span>").html("是:").appendTo(isend);
        $("<input type='radio'name='isend1'>").appendTo(isend);
        $("<span></span>").html("  否 :").appendTo(isend);
        $("<input type='radio'name='isend1' checked>").appendTo(isend);
        //备注
        var note = $("<td></td>").appendTo(otr);
        $("<input type='text'placeholder='选择完成报工，必须填写备注'class='form-control'>").css("width", "250px").appendTo(note);
        var submit = $("<td></td>").appendTo(otr);
        $("<a href='javascript:void(0)' id='submit'></a>").click(function () {
            //点击提交按钮，添加新的报工单进入数据库
            var pnum1 = $(this).parent().parent().find("td:eq(1) input").val();
            var qnum1 = $(this).parent().parent().find("td:eq(2) input").val();
            var stime1 = $(this).parent().parent().find("td:eq(3) input").val();
            var etime1 = $(this).parent().parent().find("td:eq(4) input").val();
            var note2 = $(this).parent().parent().find("td:eq(6) input").val();
            var rd = document.getElementsByName('isend1');
            var isend2 = 0;
            if (rd[0].checked) {
                isend2 = 1;
            }

            if (!pnum1) {
                alert("请填写加工数量！");
                return;
            }
            if (pnum1 * 1 < 0) {
                alert("请输入正确的加工数量！");
                return;
            }
            if (!qnum1) {
                alert("请填写合格数量！");
                return;
            }
            if (qnum1 * 1 < 0) {
                alert("请输入正确的合格数量！");
                return;
            }
            if (pnum1 * 1 < qnum1 * 1) {
                alert("合格数量不能大于加工数量！");
                return;
            }
            if (!stime1) {
                alert("请填写开始日期！");
                return;
            }
            if (!etime1) {
                alert("请填写结束日期！");
                return;
            }
            if (isend2 == 1) {
                if (!note2) {
                    alert("请填写备注");
                    return;
                }
            }
            if (stime1 > etime1) {
                alert("结束时间不能小于开始时间！");
                return;
            }
            $.post("/smartfactory/addnewbform", "fid=" + formid11 + "&pnum=" + pnum1 + "&qnum=" + qnum1 + "&stime=" + stime1 + "&etime=" + etime1 + "&note=" + note2 + "&isend=" + isend2,
                function (data) {
                    if (data == "true") {
                        if (isend2 == 1) {
                            $("#modfcon").click();
                        }else{
                            alert("报工成功！");
                        }
                        $("#searchbtnshow").click();
                    } else {
                        alert("报工失败！新建报工单出现错误！");
                    }
                }, "text");

        }).html("提交").appendTo(submit);
        otr.appendTo("#addbformtable");
    });
});
//完成报工后修改工单状态
$(function () {
    $("#modfcon").click(function () {
        var formid12 = $("#selfid").val();
        $.post("/smartfactory/modformcondition", "formid=" + formid12, function (data) {
            if (data == "true") {
                $("#selfid option:gt(0)").remove();
                $.post("/smartfactory/searchallform", null, function (data) {
                    if (data) {
                        //遍历数组
                        for (var i = 0; i < data.length; i++) {
                            //获取到一个工单信息
                            var form = data[i];
                            //动态生成页面表单,追加到下拉框中
                            $("<option></option>").html(form.formid + "---" + "(EQP" + form.eqpid + ")" + "---" + "(" + form.gname + ")" + "---" + "(" + form.fcond + ")").val(form.formid).appendTo("#selfid");
                        }
                    }
                }, "json");
                alert("报工成功!");
            }else{
                alert("报工失败，修改订单状态时出现错误。");
            }
        }, "text");
    });
});
