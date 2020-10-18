                                                                                 
$(function(){
	
	$("#searchBtnShow").click(function(){
		$("#pageNum").val(1);
		$("#searchBtn").click();
	});
	
	
	$("#searchBtn").click(function(){
		$.post("/smartfactory/searchgood", $("[name]").serialize(), function(data){
			if(data && data.size > 0){
				$("#total").html(data.total);
				$("#pages").html(data.pages);
				$("#curpage").html(data.pageNum);
				//添加记录
				$("#resulttable tr:gt(0)").remove();
				//遍历结果集，添加数据行
				for(var i=0; i < data.list.length; i++){
					
					var goodinfo = data.list[i];
					
					var oTr = $("<tr height: 70px></tr>");
					//创建单元格，生成td元素，放到tr中
                   
					$("<td></td>").html(goodinfo.gid).appendTo(oTr);
					$("<td></td>").html(goodinfo.gname).appendTo(oTr);
					$("<td></td>").html(goodinfo.gpic).appendTo(oTr);
					$("<td></td>").html(goodinfo.gcap).appendTo(oTr);
					$("<td></td>").html(goodinfo.eqpname).appendTo(oTr);
				
					//动态生成删除按钮,当前用户不能删除
					var oTd = $("<td></td>").appendTo(oTr);
                   
                    $("<input type='button' class='btn btn-default' value='删除' > ").click(function(){
				            //进行二次确认                          	
							var isOK = confirm("是否删除该商品？");
							//真正删除
							if(isOK){
								//通过DOM模型对象之间的父子关系，来获取userid
								var gid = $(this).parent().parent().find("td:eq(0)").html();
								
								$.post("/smartfactory/delgood", "gid="+gid, function(data){
									if(data == "true"){
										//删除成功
										alert("删除成功");
										$("#searchBtn").click();
										
									}
									else{
										//删除失败
										alert("该产品还有订单未完成，无法删除。");
										
									}
								}, "text")
							}
							
						}).appendTo(oTd);	
                            
                              
                       
								
					
					//
					$("<input type='button' class='btn btn-default' value='修改' >").click(function(){
						if($(this).val() == "修改"){
							//按钮文字是修改
							//1.将用户名变为可编辑状态
							//1.1获取当前用户名
							//获取当前行第二个单元对象
							var oTd2 = $(this).parent().parent().find("td:eq(1)");
							var gname = oTd2.html();
							//1.2清空单元格
							oTd2.empty();
							//1.3动态生成文本框
							$("<input type='text'>").css("width", "50px").val(gname).appendTo(oTd2);
							//2.将密码变为可编辑
							var oTd3 = $(this).parent().parent().find("td:eq(2)");
							var gpic = oTd3.html();
							oTd3.empty();
							$("<input type='text'>").css("width", "50px").val(gpic).appendTo(oTd3);
							//将产能变为可编辑
							var oTd4 = $(this).parent().parent().find("td:eq(3)");
							var gcap = oTd4.html();
							oTd4.empty();
							$("<input type='text'>").css("width", "50px").val(gcap).appendTo(oTd4);
							//将设备变为可编辑
							var oTd5 = $(this).parent().parent().find("td:eq(4)");
							var eqpname = oTd5.html();
							oTd5.empty();
							$("<input type='text'>").css("width", "50px").val(eqpname).appendTo(oTd5);
							//3.按钮变为确定
							$(this).val("确定");
						}
						else if($(this).val() == "确定"){
							//按钮文字是确定
							//1.页面验证
							//1.1用户名是否填写
							var oText2 = $(this).parent().parent().find("td:eq(1) input");
							var gname = oText2.val();
							if(!gname){
								alert("请输入商品名");
								oText2.focus();
								return;
							}
							//1.2图片是否输入
							var oText3 = $(this).parent().parent().find("td:eq(2) input");
							var gpic = oText3.val();
							if(!gpic){
								alert("请输入图片");
								oText3.focus();
								return;
							}
							//产能是否输入
							var oText4 = $(this).parent().parent().find("td:eq(3) input");
							var gcap = oText4.val();
							if(!gcap){
								alert("请输入产能值");
								oText4.focus();
								return;
							}
							//设备是否输入
							var oText5 = $(this).parent().parent().find("td:eq(4) input");
							var eqpname = oText5.val();
							if(!eqpname){
								alert("请输入设备类型");
								oText5.focus();
								return;
							}
							
							
							//2.发送AJAX请求
							//获取当前用户id
							var gid = $(this).parent().parent().find("td:eq(0)").html();
							var oBtn = $(this);
							$.post("/smartfactory/modgood", "gid="+ gid +"&gname="+ gname + "&gpic="+ gpic +"&gcap="+ gcap + "&eqpname="+eqpname , function(data){
								if(data == "true"){
									//修改成功
									//2.还原不可编辑状态
									var oTd2 = oBtn.parent().parent().find("td:eq(1)");
									var oText2 = oTd2.find("input");
									var gname = oText2.val();
									oTd2.empty();
									oTd2.html(gname);
									//
									var oTd3 = oBtn.parent().parent().find("td:eq(2)");
									var oText3 = oTd3.find("input");
									var gpic = oText3.val();
									oTd3.empty();
									oTd3.html(gpic);
									//
									var oTd4 = oBtn.parent().parent().find("td:eq(3)");
									var oText4 = oTd4.find("input");
									var gcap = oText4.val();
									oTd4.empty();
									oTd4.html(gcap);
									//
									var oTd5 = oBtn.parent().parent().find("td:eq(4)");
									var oText5 = oTd5.find("input");
									var eqpname = oText5.val();
									oTd5.empty();
									oTd5.html(eqpname);
									//3.变为修改按钮
									oBtn.val("修改");
									//4.提示信息
									alert("修改成功");
								} else{
									alert("已有该商品，请重试");
								}
							}, "text");
						}
					}).appendTo(oTd);
					
					//将tr放入表格中
					oTr.appendTo("#resulttable");
				}
				//3.页面的控制
				//结果表格 和分页控制 按钮的显示
				$("#resultdiv").show();
				$("#pagectrl").show();
				
				if(data.isFirstPage){
					$("#prePage").hide(); 
				    $("#prePageSpan").show();
				}
				else{
					$("#prePage").show();
					$("#prePageSpan").hide();
				}
				if(data.isLastPage){
					$("#nextPage").hide();
					$("#nextPageSpan").show();
				}
				else{
					$("#nextPage").show();
					$("#nextPageSpan").hide();
				}
			}
			else{
				//没有结果需要隐藏
				$("#resultdiv").hide();
				$("#pagectrl").hide();
				alert("没有查到数据");
			}
		}, "json");
	});
});

$(function(){
	//完成上一页功能
	$("#prePage").click(function(){
		//将页面上隐藏的pageNum的值减1
		var pageNum = $("#pageNum").val();
		//var既能取值也能赋值，括号里给参数就是赋值，不给就是取值 
	    $("#pageNum").val(pageNum-1);
		//再进行查询
		$("#searchBtn").click();
		
	});
	$("#nextPage").click(function(){
		//将页面上隐藏的pageNum的值加1
		var pageNum = $("#pageNum").val();
		$("#pageNum").val(pageNum*1+1);
		$("#searchBtn").click();
	});
	
	$("#goBtn").click(function(){
	//获取跳转的页数
	   var gopage = $("#gopage").val();
      //判断用户输入
	   if(gopage){
		   if(gopage < 1){
			  gopage = 1;
		   }
          //获取总页数
            var pages = $("#pages").html();
            if(gopage > pages*1){
	          gopage = pages;
            }
          //将隐藏的pageNum设定为gopage
             $("#pageNum").val(gopage);
             $("#searchBtn").click();
	  }
  });
	
    
   
    
});




