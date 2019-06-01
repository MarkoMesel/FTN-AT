package reststuff;

import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import agentstuff.AgentType;
import messagestuff.Performative;

public interface RestTest {
	
	@GET
	@Path("/messages")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String getPerformativesList();
	
	//TODO: implementiraj PUT umesto POST
	@POST
	@Path("/agents/running/{type}/{name}")
	@Consumes("text/plain")
	@Produces("text/plain")
	public void startServerAgent(@PathParam("type") String agentType,
								   @PathParam("name") String name);
	
	
	@POST
	@Path("/agents/{typeName}/{typeModule}/{agentName}")
	@Consumes("text/plain")
	@Produces("text/plain")
	public void createNewAgent(@PathParam("typeName") String typeName,
								@PathParam("typeModule") String typeModule,
								@PathParam("agentName") String agentName);
	
	@GET
	@Path("/agents/classes")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String getAgentTypes();
}
