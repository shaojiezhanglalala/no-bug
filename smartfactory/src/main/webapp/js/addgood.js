$(function(){
	$("#addgoodBtn").click(function(){
      
		var gname = $("#1").val();
        var gpic = $("#2").val();
        var gcap = $("#3").val();
        var eqpname = $("#4").val();

	
        if(!gname){
     	   alert("请填写商品名称！");
     	   return;
        }
        if(!gpic){
     	   alert("请填写商品图片！");
     	   return;
        }
        if(!gcap){
     	   alert("请填写产能！");
     	   return;
        }
        if(eqpname==0){
     	   alert("请选择设备！");
     	   return;
        }
        
        
        
		 $.post("/smartfactory/addgname","gname="+gname+"&gpic="+gpic+"&gcap="+gcap+"&eqpname="+eqpname,function(data){
		  //对服务器端响应的处理
		  
		  if(data=="true"){
			alert("添加成功");
			window.location.href="/smartfactory/goodsearch.html";
		  }		  
		  else{
			
			alert("产品名不能重复,添加失败");  
		  }
	  },"text");
		
	});
});
//添加设备选择下拉框
$(function(){
    $.post("/smartfactory/searchequipmentnoused",null,function(data){
        if(data){
            //遍历数组
            for (var i = 0; i < data.length; i++) {
                //获取到一个设备信息
                var equip = data[i];
                //动态生成页面表单,追加到下拉框中
                $("<option></option>").html(equip.eqpname).val(equip.eqpname).appendTo("#4");
            }
        }
    },"json");
});