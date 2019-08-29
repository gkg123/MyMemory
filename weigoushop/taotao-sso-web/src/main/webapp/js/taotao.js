var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("COOKIE_NAME");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://localhost:8099/user/token/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var username = data.data.username;
					var html = username + "，欢迎来到淘淘！<a href=\"http://localhost:8099/page/login\" onclick=\"delcookie()\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

function delcookie(){


	var date=new Date();
	                 date.setTime(date.getTime()-10000);
	                 var keys=document.cookie.match(/[^ =;]+(?=\=)/g);
	                 console.log("COOKIE_NAME"+keys);
	 document.cookie=keys+"=0; expire="+date.toGMTString()+"; path=/";
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});