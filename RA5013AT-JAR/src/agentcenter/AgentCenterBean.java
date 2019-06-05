package agentcenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;

import org.json.JSONArray;
import org.json.JSONException;

import agentstuff.AID;
import agentstuff.Agent;
import contractnetstuff.Initiator;
import contractnetstuff.Participant;
import jmstest.MDBConsumer;
import jmstest.SendMessage;
import messagestuff.ACLMessage;
import messagestuff.Performative;
import pingpongstuff.Ping;
import pingpongstuff.Pong;

@Stateless
@Local(AgentCenter.class)
@LocalBean
public class AgentCenterBean implements AgentCenter {
	
	private String alias = "acb";
	private String address = "moj.program.neznam";
	
	public	HashMap<Agent, Boolean> agents;
	//private HashMap<AID, Agent> runningAgents = new HashMap<AID, Agent>();
	
	@EJB
	SendMessage sm;
	
	@Override
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@PostConstruct
	public void init() {
		System.out.println("JAVLJAM SE IZ AgentCenter init().");
		agents = new HashMap<Agent, Boolean>();
	}
	
	@Override
	public void reciNesto() {
		System.out.println("Alias: " + alias);
		System.out.println("Address: " + address);
	}
	
	@Override
	public void startServerAgent(AID aid) {
		Agent agent = findAgent(aid);
		if(agent != null) {
			if(!agents.get(agent)) {
				agents.replace(agent,true);
				System.out.println("Uspesno je pokrenut agent: " + aid.getName());
			}
		}
	}
	
	@Override
	public void createNewAgent(String typeName, AID aid) {
		Agent agent = findAgent(aid);
		if(agent == null) {
			switch(typeName) {
			case "ping":
				agent = new Ping();
				agent.initialise(aid);
				break;
			case "pong":
				agent = new Pong();
				agent.initialise(aid);
				break;
			case "initiator":
				agent = new Initiator();
				agent.initialise(aid);
				break;
			case "participant":
				agent = new Participant();
				agent.initialise(aid);
				break;
			default:
				break;
			}
			agent.setAgc(this);
			agents.put(agent, false);
			System.out.println("USPESNO JE STAVLJEN AGENT U MAPU!");
		}
		System.out.println("Broj agenata u mapi: " +  agents.size());
	}
	
	@Override
	public Agent findAgent(AID aidFromSite) {
		Agent a = null;
		Iterator it = agents.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        //a = (Agent) pair.getKey();
	        Boolean notRight = false;
	        switch(aidFromSite.getType().getName()) {
	        case "ping":
	        	try {
	        		a = (Ping) pair.getKey();
	        	}catch(Exception e1) {
	        		notRight = true;
	        	}
	        	break;
	        case "pong":
	        	try {
	        		a = (Pong) pair.getKey();
	        	}catch(Exception e2) {
	        		notRight = true;
	        	}
	        	break;
	        case "initiator":
	        	try {
	        		a = (Initiator) pair.getKey();
	        	}catch(Exception e2) {
	        		notRight = true;
	        	}
	        	break;
	        case "participant":
	        	try {
	        		a = (Participant) pair.getKey();
	        	}catch(Exception e2) {
	        		notRight = true;
	        	}
	        	break;
	        default:
	        	break;
	        }
	        if(notRight) {
	        	continue;
	        }
	        AID aidFromColl = a.getAid();
	        if(theyAreEqual(aidFromSite, aidFromColl))
	        	return a;
	        //it.remove();
	    }
		return null;
	}
	
	@Override
	public Boolean theyAreEqual(AID a1, AID a2) {
		/*
		if(!(a1.getHost()).equals(a2.getHost()))
			return false;
		 */
		if(!(a1.getName()).equals(a2.getName()))
			return false;
		if(!(a1.getType().getName()).equals(a2.getType().getName()))
			return false;
		if(!(a1.getType().getModule()).equals(a2.getType().getModule()))
			return false;
		return true;
	}
	@Override
	public String[] getAgentTypes() {
		ArrayList<String> agentTypes = new ArrayList<String>();
		Iterator it = agents.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Agent a = (Agent) pair.getKey();
	        AID aid = a.getAid();
	        String aType = aid.getType().getName() + " - " + aid.getType().getModule();
	        if(!agentTypes.contains(aType)) {
	        	agentTypes.add(aType);
	        }   
		}
		
		return agentTypes.toArray(new String[0]);
	}
	@Override
	public String[] getRunningAgents() {
		ArrayList<String> runningAgents = new ArrayList<String>();
		Iterator it = agents.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Agent a = (Agent) pair.getKey();
	        AID aid = a.getAid();
	        Boolean running = (Boolean) pair.getValue();
	        if(running) {
	        	String result = aid.getName() + " - " 
	        						   + aid.getType().getName() + " - "
	        						   + aid.getType().getModule();
	        	runningAgents.add(result);
	        }
		}
		
		return runningAgents.toArray(new String[0]);
	}
	@Override
	public void stopServerAgent(AID aid) {
		Agent agent = findAgent(aid);
		if(agent != null) {
			if(agents.get(agent)) {
				agents.replace(agent,false);
				System.out.println("Uspesno je prekinut agent: " + aid.getName());
			}
		}
	}
	@Override
	public void formMessage(String data) {
		List<String> list = null;
		try {
			JSONArray dataJSON = new JSONArray(data);
			System.out.println("USPELA JE KONVERZIJA!");
			list = new ArrayList<String>();
			for(int i = 0; i < dataJSON.length(); i++){
				list.add(dataJSON.getString(i));
				//System.out.println(dataJSON.getString(i));
			}
		} catch (JSONException e) {
			System.out.println("NIJE USPELO");
		}
		
		if(list.size() == 4) {
			System.out.println("Sender: " + list.get(0));
			System.out.println("Receiver: " + list.get(1));
			System.out.println("Performative: " + list.get(2));
			System.out.println("Content: " + list.get(3));
			
			AID senderAid = new AID();
			senderAid.formFromString(list.get(0));
			AID receiverAid = new AID();
			receiverAid.formFromString(list.get(1));
			Performative performative = Performative.valueOf(list.get(2));
			String content = list.get(3);
			ACLMessage aclMsg = new ACLMessage(senderAid,
											   receiverAid,
											   performative,
											   content);
			if(receiverAid.getType().getName().equals("initiator")) {
				System.out.println("USAO JE U INITIATOR IF!");
				AID[] participants = getAIDOfRunningParticipants();
				aclMsg.contentObj = participants;
				System.out.println("SPREMNO JE ZA SLANJE!");
			}
			System.out.println("Saljem poruku...");
			sm.sendMessage(aclMsg);
		}
		//sm.reciNesto();
	}
	@Override
	public SendMessage getSM() {
		return sm;
	}
	
	@Override
	public void forwardToSM(ACLMessage aclMsg) {
		sm.sendMessage(aclMsg);
	}
	
	private AID[] getAIDOfRunningParticipants() {
		ArrayList<AID> participants = new ArrayList<>();
		Agent a = null;
		Iterator it = agents.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Agent agent = (Agent) pair.getKey();
	        if((Boolean) pair.getValue() == true &&
	           agent.getAid().getType().getName().equals("participant")) {
	        	participants.add(agent.getAid());
	        }
	    }
		return participants.toArray(new AID[participants.size()]);
	}
	@Override
	public int numOfAgents() {
		return agents.size();
	}
}
