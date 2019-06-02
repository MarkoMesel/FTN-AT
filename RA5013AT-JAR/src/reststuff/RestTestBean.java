package reststuff;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import agentcenter.AgentCenter;
import agentstuff.AID;
import agentstuff.AgentType;
import jmstest.SendMessage;
import messagestuff.ACLMessage;
import messagestuff.Performative;

@Stateless
@Remote(RestTest.class)
@LocalBean
@Path("/")
public class RestTestBean implements RestTest {

	@EJB
	AgentCenter agc;
	
	//@EJB
	//SendMessageInterface sm;
	
	@Override
	public String getPerformativesList() {
		System.out.println("JAVLJAM SE IZ JAR-A : RestTest !");
		agc.reciNesto();
		//sm.init();
		return Arrays.toString(Performative.getPerformativesStringArray());
	}

	@Override
	public void startServerAgent(String agentType, String name) {
		System.out.println("JAVLJAM SE IZ JAR-A : RestTest !");
		System.out.println("IZ startServerAgent !");
		String[] agentTypeSplitted = agentType.split(" - ");
		System.out.println("Agent Type - Name: " + agentTypeSplitted[0]);
		System.out.println("Agent Type - Module: " + agentTypeSplitted[1]);
		System.out.println("Agent Name: " + name);
		AgentType at = new AgentType();
		at.setName(agentTypeSplitted[0]);
		at.setModule(agentTypeSplitted[1]);
		AID aid = new AID();
		aid.setName(name);
		aid.setHost(agc.getAlias());
		aid.setType(at);
		agc.startServerAgent(aid);
	}

	@Override
	public void createNewAgent(String typeName, String typeModule, String agentName) {
		System.out.println("JAVLJAM SE IZ JAR-A : RestTest !");
		System.out.println("IZ createNewAgent !");
		System.out.println("Agent Type - Name: " + typeName);
		System.out.println("Agent Type - Module: " + typeModule);
		System.out.println("Agent Name: " + agentName);
		AgentType at = new AgentType();
		at.setName(typeName);
		at.setModule(typeModule);
		AID aid = new AID();
		aid.setName(agentName);
		aid.setHost(agc.getAlias());
		aid.setType(at);
		agc.createNewAgent(typeName, aid);
		//getAgentTypes();
	}

	@Override
	public String getAgentTypes() {
		return Arrays.toString(agc.getAgentTypes());
	}

	@Override
	public String getRunningAgents() {
		return Arrays.toString(agc.getRunningAgents());
	}

	@Override
	public void stopAgent(String aidParam) {
		String[] aidSplit = aidParam.split(" - ");
		AgentType at = new AgentType();
		at.setName(aidSplit[0]);
		at.setModule(aidSplit[1]);
		AID aid = new AID();
		aid.setName(aidSplit[2]);
		aid.setHost(agc.getAlias());
		aid.setType(at);
		agc.stopServerAgent(aid);
	}

	@Override
	public void sendACLMessage(String data) {
		/*
		try {
			JSONArray dataJSON = new JSONArray(data);
			System.out.println("USPELA JE KONVERZIJA!");
			List<String> list = new ArrayList<String>();
			for(int i = 0; i < dataJSON.length(); i++){
				list.add(dataJSON.getString(i));
				System.out.println("USPELO JE: " + dataJSON.getString(i));
				agc.handleMessage(new ACLMessage());
			}
		} catch (JSONException e) {
			System.out.println("NIJE USPELO");
		} 
		*/
		//agc.fireMessage("abc");
		//sm = new SendMessage();
		//sm.init();
		//sm.init();
		//SendMessage sm = new SendMessage();
		//sm.init();
		agc.fireMessage(data);
	}
	
}
