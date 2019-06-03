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
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;

import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/testQueue")
})
public class MDBConsumer implements MessageListener {

	@EJB
	AgentCenter agc;

	@Override
	public void onMessage(Message msg) {
		System.out.println("JAVLJAM SE IZ MDBConsumer-a !!!");
		try {
			ACLMessage aclMsg = (ACLMessage) ((ObjectMessage) msg).getObject();
			AID senderAid = aclMsg.sender;
			AID receiverAid = aclMsg.receivers.get(0);
			Performative performative = aclMsg.performative;
			String content = aclMsg.content;
			
			System.out.println("Sender (IZ CONSUMER-a): " + senderAid.getStr());
			System.out.println("Receiver (IZ CONSUMER-a): " + receiverAid.getStr());
			System.out.println("Performative (IZ CONSUMER-a): " + performative.toString());
			System.out.println("Content: " + content);
			
			Agent agent = agc.findAgent(receiverAid);
			if(agent != null) {
				agent.handleMessage(aclMsg);
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
