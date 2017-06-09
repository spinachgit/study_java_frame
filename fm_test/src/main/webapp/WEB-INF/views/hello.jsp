<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
	String path = request.getContextPath(); //  path = "/travel"
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Spring + WebSocket Hello world例子</title>
<script src="<%=basePath%>js/sockjs-1.1.1.min.js"></script>
<script src="<%=basePath%>js/stomp-2.3.4.min.js"></script>
<script src="<%=basePath%>/js/jquery-1.10.2.js"></script>
<script src="<%=basePath%>/js/jquery-ui-1.10.4.custom.js"></script>
<script src="<%=basePath%>/js/jquery.json.js"></script>
<script>
	//创建sockJS协议
	var socket = new SockJS("<c:url value='/ws'/>");
	var stompClient = Stomp.over(socket);
	//连接服务器
	/* stompClient.connect("guest", "guest", function() {
		$("#recFromServer").append("<br>" + "成功连接服务器.!");
		//成功连接后，设定接受服务器的地址和处理方法
		stompClient.subscribe('/topic/greetings', function(greeting) {
			var content = JSON.parse(greeting.body).content;
			$("#recFromServer").append("<br>" + content);
		});
	}, function(error) {
		//连接出现错误回调函数
		alert(error.headers.message);
	}); */
	stompClient.connect({}, function(frame) {
		//setConnected(true);
		$("#recFromServer").append("<br>" + "成功连接服务器.!");
		console.log('Connected: ' + frame);
		window.alert('Connected: ' + frame);
		stompClient.subscribe('/topic/greetings', function(greeting) {
			console.log(greeting.body);
			console.log(greeting);
			var content = JSON.parse(greeting.body).content;
			$("#recFromServer").append("<br>" + content);
		});
	});
	
	function sendMessage() {
		//发送信息给服务器
		stompClient.send("/app/greeting", {}, JSON.stringify({
			'name' : $("#message").val()
		}));
	}
</script>
</head>
<body>
	输入名称:${basePath }
	<input id="message" type="text">
	<input type="button" onclick="sendMessage()" value="发送到服务器">
	<div id="recFromServer"></div>
	测试方式: 用两个浏览器打开这个页面，然后一个页面提交信息，它能接收到服务器的数据，同时另一个页面也能接收到服务器发送的数据。
</body>
</html>
