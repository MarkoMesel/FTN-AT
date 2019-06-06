var webSocket = new WebSocket("ws://localhost:8080/RA5013AT-WAR/webendpoint");

webSocket.onopen = function(message) { onOpen(message); };
webSocket.onmessage = function(message) { onMessage(message); };
webSocket.onclose = function(message) { onClose(message); };

function onOpen(message) {
    
}
function onMessage(message) {
    
}
function onClose(message) {
    
}
