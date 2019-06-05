package contractnetstuff;

import java.util.Arrays;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import agentstuff.AID;
import agentstuff.AbstractAgent;
import agentstuff.Agent;
import messagestuff.ACLMessage;
import messagestuff.Performative;

@Stateful
@Remote(Agent.class)
public class Participant extends AbstractAgent {
	
	@Override
	public void initialise(AID aid) {
		this.aid = aid;
		
	}

	@Override
	public void handleMessage(ACLMessage aclMsg) {
		System.out.println("JAVLJAM SE IZ PARTICIPANT-A!");
		System.out.println("MOJ AID JE: " + aid.getStr());
		ACLMessage reply = aclMsg.makeReply(Performative.ACCEPT_PROPOSAL);
		reply.sender = aid;
		agc.forwardToSM(reply);
	}
	
}
