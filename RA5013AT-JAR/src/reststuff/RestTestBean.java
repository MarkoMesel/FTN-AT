package reststuff;


import java.util.Arrays;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import agentcenter.AgentCenter;
import agentstuff.AID;
import agentstuff.AgentType;
import messagestuff.Performative;

@Stateless
@Remote(RestTest.class)
@LocalBean
@Path("/")
public class RestTestBean implements RestTest {

	@EJB
	AgentCenter agc;
	
	@Override
	public String getPerformativesList() {
		System.out.println("JAVLJAM SE IZ JAR-A : RestTest !");
		agc.reciNesto();
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
	
}
