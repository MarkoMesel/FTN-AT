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
import jmstest.MDBConsumer;
import jmstest.SendMessage;
import messagestuff.ACLMessage;
import messagestuff.Performative;

@Stateful
@Remote(Agent.class)
public class Pong extends AbstractAgent {
	
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
		System.out.println("JA SAM PONG!");
		System.out.println("MOJ AID JE: " + aid.getStr());
		System.out.println("DOBIO SAM PORUKU: ");
		System.out.println(aclMsg.content);
		if(aclMsg.performative == Performative.REQUEST) {
			ACLMessage replyToPing = aclMsg.makeReply(Performative.INFORM);
			replyToPing.sender = aid;
			replyToPing.content = "Cao. Ja sam " + aid.getStr() +
				   					". Evo ti reply.";
			agc.forwardToSM(replyToPing);
			System.out.println("POSLAO SAM REPLY PING-U!");
		}
	}
	
}
