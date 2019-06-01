package agentstuff;

import messagestuff.ACLMessage;

public interface Agent {
	
	void initialise(AID aid);
	
	void handleMessage(ACLMessage msg);
	
	AID getAid();

}
