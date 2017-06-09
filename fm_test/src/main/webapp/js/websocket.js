var ws = null;
var url = null ;
var stompClient = null;
var subscription = null;
var isDebug = false;
var appName = "/fm_test";

var ws1 = null;
var ws2 = null;
	
$(function(){
	messageAirInit();
	
	messageSystemInit();
});

/**
 * 拷贝些函数到自己JS中即可。
 */
function messageAirInit() {
	ws1 = getWebsockByUrl("message/air");
	//
	ws1.onmessage = function (event) {  
		console.log('Received: ' + event.data);
		//displayMessage(event.data); //展示拿到服务端消息的方法。
		//加载气象消息数量
		$(".wListSize").html(event.data);
		$(".wListSize").show();
	}; 
}

function messageSystemInit() {
	ws2 = getWebsockByUrl("message/system");
	ws2.onmessage = function (event) {  
		console.log('Received: ' + event.data);
		//displayMessage(event.data);
		//加载系统消息数量
		$(".sListSize").html(event.data);
		$(".sListSize").show();
	};  
}


/**
 * stomp协议初始化
 */
function stompClientInit(){
	stompClient = Stomp.over(ws);
	var connectCallback = function() {
		console.log("Info: connection opened.");
		//订阅
		stompClient.subscribe("/topic/messDemo", function(data) {
			if (data) {
				var body = JSON.parse(data.body);
				console.log("Public: " + body.value);
			}
		});
	};
	var errorCallback = function(error) {
		if (error.headers) {
			console.log("Error: " + error.headers.message);
		}
	};
	if (isDebug) {
		stompClient.debug = function(str) {
			console.log("Debug: " + str);
		};
	}
	stompClient.connect({}, connectCallback, errorCallback);
}

/**
 * stomp协议 发送服务端消息
 */
function sendServeEemo() {
	if (ws != null) {
		var message = document.getElementById('message').value;
		console.log('Pinconsole.logg with: ' + message);
		if (stompClient != null) {
			stompClient.send("/app/ping", {}, JSON.stringify({
				'key' : 'MYKEY',
				'value' : message
			}));
		}
	} else {
		alert('connection not established, please connect.');
	}
}

/**
 * 获得对应websocket协议URL
 * @param url
 * @returns
 */
function getWebsockByUrl(url){
	if(null == url || url.trim() == ""){
		console.log("声明websocket的url不能为空！");
		return false;
	}else{
		url = url.trim();
	}
	
	if(url.indexOf != 0){
		url = "/"+url;
	}
	var _host = window.location.host
	var _ws ;
	if ('WebSocket' in window) {
		_ws = new WebSocket("ws://"+_host+appName+"/ws"+url);
	} else if ('MozWebSocket' in window) {
		_ws = new MozWebSocket("ws://"+_host+appName+"/ws"+url);
	} else {
		_ws = new SockJS("http://"+_host+appName+"/wss/"+url);
	}
	return _ws;
}

