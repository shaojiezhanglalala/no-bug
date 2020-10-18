 //:~添加设备开始
$(function(){
    $("#sumbitadd").click(function(){
//        var eqppid=$("#aeqpid").val();
//        var eqpid=0;
        var eqpname=$("#aeqpname").val();
        var eqppic=$("#aeqppic").val();
        var eqpcondval=$("#aeqpcond").val();
//        if(!eqppid){
//            alert("请输入设备ID");
//            return;
//        }
        
        if(!eqpname){
            alert("请输入设备名称");
            return;
        }
        if(eqpcondval==0){
            alert("请选择设备状态");
            return;
        }else if(eqpcondval==1){
            eqpcond="启用";
        }else if(eqpcondval==2){
            eqpcond = "停用";
        }else if(eqpcondval==3){
            eqpcond="故障";
        }
//        var eqpid=eqppid*1;
         $.post("/smartfactory/addnewequipment", "eqpname=" + eqpname + "&eqppic=" + eqppic + "&eqpcond=" + eqpcond, function (data) {
              if (data == "true") {
                  //添加成功
                  alert("添加成功");
              } else {
                  //添加失败
                  alert("添加设备失败");
              }
             $("#closeadd").click();
          }, "text");
    })
})
//:~添加设备结束

 //:~分页功能的实现通过产品名字搜索
//给显示的查询按钮绑定点击事件
 $(function () {
     $("#geqpsearchBtnShow").click(function () {
         $("#eqppageNum").val(1);
         $("#geqpsearchBth").click();
     })
     //：~给隐藏查询按钮绑定点击事件
     $("#geqpsearchBth").click(function () {
//         $("#addeqpdiv").hide();
         $("#eqppagectrl").hide();
         //发送Ajax请求
         $.post("/smartfactory/searchequipmentbygname", $("[name]").serialize(), function (data) {
             if (data && data.size > 0) {
                 //1.动态添加消息
                 //添加记录总条数
                 $("#eqptotal1").html(data.total);
                 //添加总页数
                 $("#eqppages1").html(data.pages);
                 //添加当前页数
                 $("#eqpcurpage1").html(data.pageNum);
                 //2.添加记录
                 //删除表格中原有记录
                 $("#eqpresulttable tr:gt(0)").remove();

                 //遍历结果集，添加数据行
                 for (var i = 0; i < data.list.length; i++) {
                     var equipmentinfo = data.list[i];
                     //创建一行，生成<tr>
                     var eqpoTr = $("<tr height: 70px></tr>");
                     //生成序号单元格,放入tr
                     $("<td align='center'></td>").html(i * 1 + 1).appendTo(eqpoTr);
                     //生成设备序列号单元格,放入tr
                     $("<td align='center'></td>").html("EQP" + equipmentinfo.eqpid).appendTo(eqpoTr);
                     //生成设备名单元格,放入tr
                     $("<td align='center'></td>").html(equipmentinfo.eqpname).appendTo(eqpoTr);
                     //生成设备图片单元格,放入tr
                     // var picpath="image/1.jpg";
                     $("<td align='center'></td>").html(equipmentinfo.eqppic).appendTo(eqpoTr);
                     //生成可生产产品单元格,放入tr
                     $("<td align='center'></td>").appendTo(eqpoTr);
                     //生成可生产产品产能单元格,放入tr
//                     $("<td align='center'></td>").appendTo(eqpoTr);
                     //生成设备状态单元格,放入tr
                     $("<td align='center'></td>").html(equipmentinfo.eqpcond).appendTo(eqpoTr);
                     var eqpoTd = $("<td align='center'></td>").appendTo(eqpoTr);
                     var id = 0;
                     var eqpname = equipmentinfo.eqpname;
//                     $.post("/smartfactory/searchgnamebyeqpname", "gid=" + id + "&eqpname=" + eqpname+"&pageNum="+3+"&pageSize="+3, function (data) {
//                         if (data && data.size > 0){
//                              var num =0;
//                             var goodinfo=data.list[num];
//                             eqpoTd.parent().parent().find("td:eq(4)").html(goodinfo.gname);
////                             eqpoTd.parent().parent().find("td:eq(4)").html(goodinfo.gname);
//                         }
//                     }, "json");

                      $.ajax({

                        type: "post",
                        
                        url: "/smartfactory/searchgnamebyeqpname",
                        
                        data: "gid=" + id + "&eqpname=" + eqpname+"&pageNum="+3+"&pageSize="+3,

                        async : false,
                        
                        success: function(data){
                            if (data && data.size > 0){
                              var num =0;
                             var goodinfo=data.list[num];
                             eqpoTd.parent().parent().find("td:eq(4)").html(goodinfo.gname);
                         }
                        },
                        dataType: "json"});   
                     
                     
                     
                     
                     //：~动态生成删除按钮
                     var eqpcond = $(this).parent().parent().find("td:eq(5)").html();
                     if (eqpcond != "启用") {
                         $("<button class='btn btn-default' id='geqpdelbtn'>删除</button>").click(function () {
                             var isOK = confirm("确定要删除该设备吗？");
                             if (isOK) {
                                 var seqpid = $(this).parent().parent().find("td:eq(1)").html();
                                 var eqpid = seqpid.slice(3) * 1;
                                 //发送ajax
                                 $.post("/smartfactory/deleteequipment", "eqpid=" + eqpid, function (data) {
                                     if (data == "true") {
                                         //删除成功
                                         alert("删除成功");
                                         $("#geqpsearchBtnShow").click();
                                     } else {
                                         //删除失败
                                         alert("状态为“启用”的设备不可删除");
                                     }
                                 }, "text");

                             }
                         }).appendTo(eqpoTd);

                     }
                     //：~动态生成删除按钮结束
                     //：~添加修改按钮
//                     $("<button class='btn btn-default' id='geqpmodbtn'>修改</button>").click(function () {
                         $("<input type='button' class='btn btn-default' value='修改'>").click(function () { 
                         if ($(this).val() == "修改") {
                             //将设备名变为可编辑状态
                             //获取设备名
                             var oTd3 = $(this).parent().parent().find("td:eq(2)");
                             //获取其中文本
                             var eqpname = oTd3.html();
                             //清空单元格
                             oTd3.empty();
                             //动态生成一个文本框，并将其设备名初始化到其中。并将其放入oTd3中
                             $("<input type='text'>").css("width", "50px").val(eqpname).appendTo(oTd3);

                             var oTd6 = $(this).parent().parent().find("td:eq(5)");
                             var eqpcond = oTd6.html();
                             oTd6.empty();
                             
                             $("<select name='condition' class='form-control' id='condition'></select>").appendTo(oTd6);
//                             <option value='0'>--'eqpcond'--</option>
                             $("<option></option>").html(eqpcond).val(0).appendTo("#condition");
                             $("<option></option>").html("启用").val(1).appendTo("#condition");
                             $("<option></option>").html("停用").val(2).appendTo("#condition");
                             $("<option></option>").html("故障").val(3).appendTo("#condition");
                             
                             $(this).val("确定");
                         } else if ($(this).val() == "确定") {
                             //1.页面验证
                             //1.1验证是否填写了设备名
                             var oText3 = $(this).parent().parent().find("td:eq(2) input");
                             var eqpname = oText3.val();
                             if (!eqpname) {
                                 alert("请输入设备名");
                                 oText3.focus();
                                 return;
                             }
                             //1.2验证是否填写了设备状态
//                             var oText6 = $(this).parent().parent().find("td:eq(5) input");
//                             var eqpcond = oText6.val();
                             
                             var oText6=$(this).parent().parent().find("td:eq(5)  option:selected");
                             var eqpcond=oText6.html();
                             if (!eqpcond) {
                                 alert("请输入设备状态");
                                 oText6.focus();
                                 return;
                             }
                             //发送ajax请求
                             var seqpid = $(this).parent().parent().find("td:eq(1)").html();
                             var eqpid = seqpid.slice(3);
                             var oBtn = $(this);
                             $.post("/smartfactory/modifyequipment", "eqpid=" + eqpid + "&eqpname=" + eqpname + "&eqpcond=" + eqpcond, function (data) {
                                 if (data == "true") {
                                     //修改成功
                                     //1.将设备名还原为不可编辑状态
                                     var oTd3 = oBtn.parent().parent().find("td:eq(2)");
                                     var oText3 = oTd3.find("input");
                                     var eqpname = oText3.val();
                                     oTd3.empty();
                                     oTd3.html(eqpname);
                                     
                                     //2.将设备状态还原为不可编辑
                                     var oTd6 = oBtn.parent().parent().find("td:eq(5)");
                                     var oText6 = oTd6.find("option:selected");
                                     var eqpcond = oText6.html();
                                     oTd6.empty();
                                     oTd6.html(eqpcond);
                                     //提示信息
                                     oBtn.val("修改");
                                     alert("修改成功");
                                     $("#geqpsearchBth").click();
                                     
                                 } else {
                                     //修改失败
                                     alert("修改失败");
                                 }
                             }, "text");
                         }
                     }).appendTo(eqpoTd);
                    // $("#geqpsearchBtn").click();
                     //~:添加修改按钮结束
                     eqpoTr.appendTo("#eqpresulttable");
                 }

                 //3.页面的控制
                 //结果表格和分页控制按钮的显示
                 $("#eqpresultdiv").show();
                 $("#eqppagectrl1").show();
                 //上一页和下一页按钮的控制
                 if (data.isFirstPage) {
                     $("#eqpprePage1").hide();
                     $("#eqpprePageSpan1").show();
                 } else {
                     $("#eqpprePage1").show();
                     $("#eqpprePageSpan1").hide();
                 }
                 if (data.isLastPage) {
                     $("#eqpnextPage1").hide();
                     $("#eqpnextPageSpan1").show();
                 } else {
                     $("#eqpnextPage1").show();
                     $("#eqpnextPageSpan1").hide();
                 }
             } else {
                 //没有查到数据
                 $("#eqpresultdiv").hide();
                 $("#eqppagectrl1").hide();
                 alert("没有查到数据");

             }
         }, "json");
     
     
        //Ajax请求
     })
     //：~给隐藏查询按钮绑定点击事件结束
 })
 //:~分页功能实现结束 

//页面控制2
 $(function () {
     //完成“上一页”功能
     $("#eqpprePage1").click(function () {
         //将页面上隐藏的pageNum值减一
         var eqppageNum = $("#eqppageNum").val();
         $("#eqppageNum").val(eqppageNum - 1);
         //在进行一次查询的操作，点击一次查询按钮
         $("#geqpsearchBth").click();

     });
     //完成“下一页”功能
     $("#eqpnextPage1").click(function () {
         //将页面上隐藏的pageNum值jia一
         var eqppageNum = $("#eqppageNum").val();
         $("#eqppageNum").val(eqppageNum * 1 + 1);
         //在进行一次查询的操作，点击一次查询按钮
         $("#geqpsearchBth").click();
     });
     //完成go按钮的功能
     $("#eqpgoBtn1").click(function () {
         //获取跳转的页数
         var eqpgopage = $("#eqpgopage1").val() * 1;
         //判断用户的输入
         //页码小于第一页
         if (eqpgopage < 1) {
             eqpgopage = 1;
         }
         //页码大于最后一页
         //获取总页数
         var eqppages = $("#eqppages1").html();
         if (eqpgopage > eqppages * 1) {
             eqpgopage = eqppages;
         }

         //将页面上用户隐藏的pageNum设定为gopage
         $("#eqppageNum").val(eqpgopage);
         //在进行一次查询的操作，点击一次查询按钮
         $("#geqpsearchBth").click();


     });

 });

  //:~分页功能的实现1通过设备信息查找设备
 $(function () {
     $("#eqpsearchBtnShow").click(function () {
         $("#eqppageNum").val(1);
         $("#eqpsearchBth").click();
     })
     //：~给隐藏查询按钮绑定点击事件
     $("#eqpsearchBth").click(function () {
//         $("#addeqpdiv").hide();
         $("#eqppagectrl1").hide();
         //发送Ajax请求
         $.post("/smartfactory/searchequipment", $("[name]").serialize(), function (data) {
             if (data && data.size > 0) {
                 //1.动态添加消息
                 //添加记录总条数
                 $("#eqptotal").html(data.total);
                 //添加总页数
                 $("#eqppages").html(data.pages);
                 //添加当前页数
                 $("#eqpcurpage").html(data.pageNum);
                 //2.添加记录
                 //删除表格中原有记录
                 $("#eqpresulttable tr:gt(0)").remove();

                 //遍历结果集，添加数据行
                 for (var i = 0; i < data.list.length; i++) {
                     var equipmentinfo = data.list[i];
                     //创建一行，生成<tr>
                     var eqpoTr = $("<tr height: 70px></tr>");
                     //生成序号单元格,放入tr
                     $("<td align='center'></td>").html(i * 1 + 1).appendTo(eqpoTr);
                     //生成设备序列号单元格,放入tr
                     $("<td align='center'></td>").html("EQP" + equipmentinfo.eqpid).appendTo(eqpoTr);
                     //生成设备名单元格,放入tr
                     $("<td align='center'></td>").html(equipmentinfo.eqpname).appendTo(eqpoTr);
                     //生成设备图片单元格,放入tr
                     // var picpath="image/1.jpg";
                     $("<td align='center'></td>").html(equipmentinfo.eqppic).appendTo(eqpoTr);
                     //生成可生产产品单元格,放入tr
                     $("<td align='center'></td>").appendTo(eqpoTr);

                     //生成设备状态单元格,放入tr
                     $("<td align='center'></td>").html(equipmentinfo.eqpcond).appendTo(eqpoTr);
                     var eqpoTd = $("<td align='center'></td>").appendTo(eqpoTr);                     
                     var id = 0;
                     //获取设备名
//                     var eqpname = "设备3";
                     var eqpname = equipmentinfo.eqpname;
//                     $.post("/smartfactory/searchgnamebyeqpname", "gid=" + id + "&eqpname=" + eqpname+"&pageNum="+3+"&pageSize="+3, function (data) {
//                         if (data && data.size > 0){
//                              var num =0;
//                             var goodinfo=data.list[num];
//                             eqpoTd.parent().parent().find("td:eq(4)").html(goodinfo.gname);
//                         }
//                     }, "json");
                     $.ajax({

                        type: "post",
                        
                        url: "/smartfactory/searchgnamebyeqpname",
                        
                        data: "gid=" + id + "&eqpname=" + eqpname+"&pageNum="+3+"&pageSize="+3,

                        async : false,
                        
                        success: function(data){
                            if (data && data.size > 0){
                              var num =0;
                             var goodinfo=data.list[num];
                             eqpoTd.parent().parent().find("td:eq(4)").html(goodinfo.gname);
                         }
                        },
                        dataType: "json"});
                     

                        
                     //：~动态生成删除按钮
                  //   var eqpcond = $(this).parent().parent().find("td:eq(5)").html();
                     var oTd6 = $(this).parent().parent().find("td:eq(5)");
                             var eqpcond = oTd6.html();
                     
                     if (eqpcond != "启用") {
                         $("<button class='btn btn-default' id='eqpdelbtn'>删除</button>").click(function () {
                             var isOK = confirm("确定要删除该设备吗？");
                             if (isOK) {
                                 var seqpid = $(this).parent().parent().find("td:eq(1)").html();
                                 var eqpid = seqpid.slice(3) * 1;
                                 //发送ajax
                                 $.post("/smartfactory/deleteequipment", "eqpid=" + eqpid, function (data) {
                                     if (data == "true") {
                                         //删除成功
                                         alert("删除成功");
                                         $("#eqpsearchBtnShow").click();
                                     } else {
                                         //删除失败
                                         alert("删除失败");
                                     }
                                 }, "text");

                             }
                         }).appendTo(eqpoTd);

                     }
                     //：~动态生成删除按钮结束
                     //：~添加修改按钮
                       $("<input type='button' class='btn btn-default' value='修改'>").click(function () {  
                         
                     if ($(this).val() == "修改") {
                             //将设备名变为可编辑状态
                             //获取设备名
                             var oTd3 = $(this).parent().parent().find("td:eq(2)");
                             //获取其中文本
                             var eqpname = oTd3.html();
                             //清空单元格
                             oTd3.empty();
                             //动态生成一个文本框，并将其设备名初始化到其中。并将其放入oTd3中
                             $("<input type='text'>").css("width", "50px").val(eqpname).appendTo(oTd3);
                             
                             
                             
                             var oTd6 = $(this).parent().parent().find("td:eq(5)");
                             var eqpcond = oTd6.html();
                             oTd6.empty();
                             
                             $("<select name='condition' class='form-control' id='condition'></select>").appendTo(oTd6);
//                             <option value='0'>--'eqpcond'--</option>
                             $("<option></option>").html(eqpcond).val(0).appendTo("#condition");
                             $("<option></option>").html("启用").val(1).appendTo("#condition");
                             $("<option></option>").html("停用").val(2).appendTo("#condition");
                             $("<option></option>").html("故障").val(3).appendTo("#condition");
                             
                             $(this).val("确定");
                         } else if ($(this).val() == "确定") {
                             //1.页面验证
                             //1.1验证是否填写了设备名
                             var oText3 = $(this).parent().parent().find("td:eq(2) input");
                             var eqpname = oText3.val();
                             if (!eqpname) {
                                 alert("请输入设备名");
                                 oText3.focus();
                                 return;
                             }
                             
                             var oText6=$(this).parent().parent().find("td:eq(5)  option:selected");
                             var eqpcond=oText6.html();
                             if (!eqpcond) {
                                 alert("请输入设备状态");
                                 oText6.focus();
                                 return;
                             }
                             
                             //发送ajax请求
                             var seqpid = $(this).parent().parent().find("td:eq(1)").html();
                             var eqpid = seqpid.slice(3);
                             var oBtn = $(this);
                             $.post("/smartfactory/modifyequipment", "eqpid=" + eqpid + "&eqpname=" + eqpname + "&eqpcond=" + eqpcond, function (data) {
                                 if (data == "true") {
                                     //修改成功
                                     //1.将设备名还原为不可编辑状态
                                     var oTd3 = oBtn.parent().parent().find("td:eq(2)");
                                     var oText3 = oTd3.find("input");
                                     var eqpname = oText3.val();
                                     oTd3.empty();
                                     oTd3.html(eqpname);
                                     var oTd5 = oBtn.parent().parent().find("td:eq(4)");
                                     var oText5 = oTd5.find("input");
                                     var gname = oText5.val();
                                     oTd5.empty();
                                     oTd5.html(gname);
                                     var ggid=0;
                                     var gid=0;
                                     
                                    
                                     
                                     //2.将设备状态还原为不可编辑
                                     var oTd6 = oBtn.parent().parent().find("td:eq(5)");
                                     var oText6 = oTd6.find("option:selected");
                                     var eqpcond = oText6.html();
                                     oTd6.empty();
                                     oTd6.html(eqpcond);
                                     //提示信息
                                     oBtn.val("修改");
                                     alert("修改成功");
                                     $("#eqpsearchBth").click();
                                     
                                 } else {
                                     //修改失败
                                     alert("修改失败");
                                 }
                             }, "text");
                             //再次发送Ajax请求，将goodinfo表对应的设备名字做更改
                             
                         }
                     }).appendTo(eqpoTd);
                    // $("#geqpsearchBtn").click();
                     //~:添加修改按钮结束
                     eqpoTr.appendTo("#eqpresulttable");
                 }

                 //3.页面的控制
                 //结果表格和分页控制按钮的显示
                 $("#eqpresultdiv").show();
                 $("#eqppagectrl").show();
                 //上一页和下一页按钮的控制
                 if (data.isFirstPage) {
                     $("#eqpprePage").hide();
                     $("#eqpprePageSpan").show();
                 } else {
                     $("#eqpprePage").show();
                     $("#eqpprePageSpan").hide();
                 }
                 if (data.isLastPage) {
                     $("#eqpnextPage").hide();
                     $("#eqpnextPageSpan").show();
                 } else {
                     $("#eqpnextPage").show();
                     $("#eqpnextPageSpan").hide();
                 }
             } else {
                 //没有查到数据
                 $("#eqpresultdiv").hide();
                 $("#eqppagectrl").hide();
                 alert("没有查到数据");

             }
         }, "json");
     
            
        //Ajax请求
     })
     //：~给隐藏查询按钮绑定点击事件结束
 })
 //:~分页功能实现结束 
  

 //页面控制1
 $(function () {
     //完成“上一页”功能
     $("#eqpprePage").click(function () {
         //将页面上隐藏的pageNum值减一
         var eqppageNum = $("#eqppageNum").val();
         $("#eqppageNum").val(eqppageNum - 1);
         //在进行一次查询的操作，点击一次查询按钮
         $("#eqpsearchBth").click();

     });
     //完成“下一页”功能
     $("#eqpnextPage").click(function () {
         //将页面上隐藏的pageNum值jia一
         var eqppageNum = $("#eqppageNum").val();
         $("#eqppageNum").val(eqppageNum * 1 + 1);
         //在进行一次查询的操作，点击一次查询按钮
         $("#eqpsearchBth").click();
     });
     //完成go按钮的功能
     $("#eqpgoBtn").click(function () {
         //获取跳转的页数
         var eqpgopage = $("#eqpgopage").val() * 1;
         //判断用户的输入
         //页码小于第一页
         if (eqpgopage < 1) {
             eqpgopage = 1;
         }
         //页码大于最后一页
         //获取总页数
         var eqppages = $("#eqppages").html();
         if (eqpgopage > eqppages * 1) {
             eqpgopage = eqppages;
         }

         //将页面上用户隐藏的pageNum设定为gopage
         $("#eqppageNum").val(eqpgopage);
         //在进行一次查询的操作，点击一次查询按钮
         $("#eqpsearchBth").click();


     });

 });
 