package contractnetstuff;

import java.util.Arrays;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import agentstuff.AID;
import agentstuff.AbstractAgent;
import agentstuff.Agent;
import jmstest.MDBConsumer;
import messagestuff.ACLMessage;
import messagestuff.Performative;

@Stateful
@Remote(Agent.class)
public class Initiator extends AbstractAgent {

	private int pendingProposals;
	
	@Override
	public void initialise(AID aid) {
		this.aid = aid;
		
	}

	@Override
	public void handleMessage(ACLMessage aclMsg) {
		System.out.println("JAVLJAM SE IZ INITIATOR-A!");
		switch(aclMsg.performative) {
			case REQUEST:
				AID[] participants = (AID[]) aclMsg.contentObj;
				ACLMessage msg = new ACLMessage(Performative.CALL_FOR_PROPOSAL);
				msg.sender = aid;
				msg.receivers.addAll(Arrays.asList(participants));
				pendingProposals = participants.length;
				System.out.println("BROJ POTREBNIH PROPOSAL-A: " + pendingProposals);
				agc.forwardToSM(msg);
				break;
			case ACCEPT_PROPOSAL:
				--pendingProposals;
				System.out.println("BROJ POTREBNIH PROPOSAL-A: " + pendingProposals);
				if(pendingProposals == 0) {
					System.out.println("KRAJ! NE TREBA MI VISE PROPOSAL-A!");
				}
				break;
			default:
		}
	}
	
	
	
	

}
