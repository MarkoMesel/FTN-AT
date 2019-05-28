package socketstuff;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/webendpoint")
public class WebSocketServerEndpoint {
	
	@OnOpen
	public void onOpen() {	
		System.out.println("WebSocketSeverEndpoint je otvoren! ");
	}
	
	@OnMessage
	public void onMessage(String message) {
		System.out.println("Ja sam ovo dobio: " + message);
	}
	
	@OnClose
	public void onClose() {	
		System.out.println("Zatvorio se WebSocketSeverEndpoint.");
	}
	
}
