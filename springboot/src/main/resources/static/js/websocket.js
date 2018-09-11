var host = location.host;
var websocket = null;
if('WebSocket' in window){
	websocket = new WebSocket("ws://" + host + "/userList");
}else{
	alert('Not support websocket')
}