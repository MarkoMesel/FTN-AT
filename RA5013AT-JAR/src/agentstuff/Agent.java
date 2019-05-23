package agentstuff;

import messagestuff.ACLMessage;

public interface Agent {
	
	void handleMessage(ACLMessage msg);

}
