package agentcenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Stateless;

import com.sun.beans.util.Cache;

import agentstuff.AgentType;
import agentstuff.AID;
import agentstuff.Agent;
import messagestuff.Performative;
import pingpongstuff.Ping;
import pingpongstuff.Pong;

@Stateless
@Local(AgentCenter.class)
@LocalBean
public class AgentCenterBean implements AgentCenter {
	
	private String alias = "acb";
	private String address = "moj.program.stagod";
	
	public	HashMap<Agent, Boolean> agents = new HashMap<Agent, Boolean>();
	//private HashMap<AID, Agent> runningAgents = new HashMap<AID, Agent>();
	
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
			default:
				break;
			}
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
}
