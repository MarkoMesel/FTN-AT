package agentstuff;

import jmstest.MDBConsumer;
import messagestuff.ACLMessage;

public interface Agent {
	
	void initialise(AID aid);
	
	void handleMessage(ACLMessage msg);
	
	AID getAid();

	void setConsumer(MDBConsumer consumer);

}
