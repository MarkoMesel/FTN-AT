package pingpongstuff;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import agentstuff.AID;
import agentstuff.AbstractAgent;
import agentstuff.Agent;
import messagestuff.ACLMessage;


@Stateless
@Remote(Agent.class)
public class Ping extends AbstractAgent {

	@Override
	public void initialise(AID aid) {
		this.aid = aid;
		
	}
	
	@Override
	public AID getAid() {
		return aid;
	}

	@Override
	public void handleMessage(ACLMessage aclMsg) {
		System.out.println("JAVLJAM SE IZ AGENTA!");
		System.out.println("moj AID je: " + aid.getStr());
	}
	
}
