package jmstest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import agentcenter.AgentCenter;
import agentcenter.AgentCenterBean;
import agentstuff.AID;
import agentstuff.Agent;
import messagestuff.ACLMessage;
import messagestuff.Performative;

import javax.ejb.MessageDriven;
import javax.ejb.Singleton;
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/testQueue")
})

public class MDBConsumer implements MessageListener {
	
	@EJB
	AgentCenter agc;
	
	@PostConstruct
	public void init() {
		System.out.println("JAVLJAM SE IZ MDBConsumer init().");
	}

	@Override
	public void onMessage(Message msg) {
		System.out.println("JAVLJAM SE IZ MDBConsumer-a !!!");
		try {
			ACLMessage aclMsg = (ACLMessage) ((ObjectMessage) msg).getObject();
			System.out.println("UZEO SAM PORUKU!");
			AID senderAid = aclMsg.sender;
			/*
			if(aclMsg.receivers.size() > 1) {
				for(int i = 0; i <= aclMsg.receivers.size(); i++) {
					Agent agent = agc.findAgent(aclMsg.receivers.get(i
							));
					if(agent != null) {
						agent.setConsumer(this);
						agent.handleMessage(aclMsg);
					}
				}
			} else {
			*/
				AID receiverAid = aclMsg.receivers.get(0);
				Performative performative = aclMsg.performative;
				String content = aclMsg.content;
				
				System.out.println("Sender (IZ CONSUMER-a): " + senderAid.getStr());
				System.out.println("Receiver (IZ CONSUMER-a): " + receiverAid.getStr());
				System.out.println("Performative (IZ CONSUMER-a): " + performative.toString());
				System.out.println("Content: " + content);
				
				System.out.println("AGENTSKI CENTAR TRENUTNO IMA U SEBI " + agc.numOfAgents() + " agenta.");
				Agent agent = agc.findAgent(receiverAid);
				if(agent != null) {
					System.out.println("NASAO SAM GA. SAD CU MU POSLATI!");
					//agent.setConsumer(this);
					agent.handleMessage(aclMsg);
				} else {
					System.out.println("NISAM NASAO AGENTA!");
				}
			//}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public AgentCenter getAgc() {
		return agc;
	}

}
