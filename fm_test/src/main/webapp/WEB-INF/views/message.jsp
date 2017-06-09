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
<script src="<%=basePath %>js/jquery-1.10.2.js"></script>

</head>
<body>
	输入名称:${basePath }
	<input id="message" type="text" >
	<input type="button" onclick="sendMessage()" value="发送到服务器">
	<div id="recFromServer"></div>
	测试方式: 用两个浏览器打开这个页面，然后一个页面提交信息，它能接收到服务器的数据，同时另一个页面也能接收到服务器发送的数据。
</body>
<script src="<%=basePath%>/js/websocket.js"></script>
<script type="text/javascript">
function sendMessage(){
	ws1.send($("#message").val());
}
</script>
</html>
