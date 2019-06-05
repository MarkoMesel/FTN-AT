package jmstest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import agentcenter.AgentCenter;
import agentstuff.AID;
import messagestuff.ACLMessage;

@Singleton
@LocalBean
public class SendMessage {
	
	private Connection connection;
	@Resource(lookup = "java:jboss/exported/jms/RemoteConnectionFactory")
	private ConnectionFactory cf;
	@Resource(lookup = "java:jboss/exported/jms/testQueue")	
	private Queue queue;
	
	Session session;
	MessageProducer messageProducer;
	
	@PostConstruct
	public void init() {
		Properties jndiProperties = new Properties();
		System.out.println("k2");
		
		//jndiProperties.put("jboss.naming.client.ejb.context", "true");
		System.out.println("k3");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		System.out.println("k4");
		jndiProperties.put(Context.PROVIDER_URL,"http-remoting://127.0.0.1:8080");
		System.out.println("k5");
		
		jndiProperties.put(Context.SECURITY_PRINCIPAL, "testuser");
		jndiProperties.put(Context.SECURITY_CREDENTIALS, "testuser");
		
		connection = null;
		
		try {
			
			System.out.println("k6");
			Context ctx = new InitialContext(jndiProperties);
			System.out.println("k7");
			//Queue queue = (Queue) ctx.lookup("jms/testQueue");
			System.out.println("k8");
			//ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
			
			System.out.println("k9");
			connection = cf.createConnection("testuser","testuser");
			
			System.out.println("k10");
			//session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session = connection.createSession(false, Session.DUPS_OK_ACKNOWLEDGE);
			System.out.println("k11");
			messageProducer = session.createProducer(queue);

			/*
			TextMessage message = session.createTextMessage("Hello World. The time is now " + new Date());
			System.out.println("k13");
			messageProducer.send(message);
			*/
			
			System.out.println("RADI!!!");
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(ACLMessage aclMsg) {
		try {
			ArrayList<ObjectMessage> messagesToSend = new ArrayList<>();
			if(aclMsg.receivers.size() > 1) {
				System.out.println("IMA VISE RECEIVER-A!");
				for(int i = 0; i < aclMsg.receivers.size(); i++) {
				System.out.println("OVO JE PORUKA ZA: " + aclMsg.receivers.get(i).getStr());
				ACLMessage sendToOne = new ACLMessage(aclMsg.sender,
												   aclMsg.receivers.get(i),
												   aclMsg.performative,
												   aclMsg.content);
				ObjectMessage objMsg = session.createObjectMessage(sendToOne);
				messagesToSend.add(objMsg);
				//messageProducer.send(objMsg);
				}
				for(int i = 0; i < messagesToSend.size(); i++) {
					messageProducer.send(messagesToSend.get(i));
				}
				
			} else {
				ObjectMessage objMsg = session.createObjectMessage(aclMsg);
				messageProducer.send(objMsg);
			}
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reciNesto() {
		System.out.println("Nesto.");
	}
	
	@PreDestroy
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("KONEKCIJA JE ZATVORENA!");
		} catch (JMSException e) {
				
		}
	}

}
