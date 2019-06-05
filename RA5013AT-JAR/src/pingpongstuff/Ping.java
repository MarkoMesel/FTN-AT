package pingpongstuff;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import agentcenter.AgentCenter;
import agentcenter.AgentCenterBean;
import agentstuff.AID;
import agentstuff.AbstractAgent;
import agentstuff.Agent;
import jmstest.SendMessage;
import messagestuff.ACLMessage;
import messagestuff.Performative;

@Stateful
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
		System.out.println("JA SAM PING!");
		System.out.println("MOJ AID JE: " + aid.getStr());
		System.out.println("DOBIO SAM PORUKU: ");
		System.out.println(aclMsg.content);
		
		if(aclMsg.performative == Performative.REQUEST) {
			ACLMessage msgForPong = new ACLMessage(aclMsg.receivers.get(0),
												   aclMsg.sender,
												   Performative.REQUEST,
												   "Cao. Ja sam " + aid.getStr() +
												   ". Saljem ti poruku.");
			agc.forwardToSM(msgForPong);
			System.out.println("POSLAO SAM PORUKU PONG-U!");
		} else if(aclMsg.performative == Performative.INFORM) {
			System.out.println("ZAVRSILI SMO SARADNJU!");
		}
	}
	
}
