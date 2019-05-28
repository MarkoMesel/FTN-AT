package reststuff;


import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;

import messagestuff.Performative;

@Path("/")
public class RestTest {
	
	/*
	public RestTest() {
	      System.out.println("*** REST SERVICE ****");
	    }
	
	@GET
	@Path("resource")
	@Produces(MediaType.TEXT_PLAIN)
	public Response getResource() {
	      System.out.println("***Calling function****");
	      return Response.status(Status.OK).entity("working").build();
	    }
	
	    */

	@GET
	@Path("/messages")
	@Consumes("text/plain")
	@Produces("text/plain")
	public String getPerformativesList() {
		/*
		String[] performatives = Performative.getPerformativesStringArray();
		for(int i = 0; i < performatives.length; i++) {
				System.out.println("OVO JE NASAO: " + performatives[i]);
		}
		JSONArray performativesJSON = new JSONArray(Arrays.asList(performatives));
		System.out.println("OVO JE KONVERTOVAO: " + performativesJSON.toString());
		return performativesJSON;
		*/
		return Arrays.toString(Performative.getPerformativesStringArray());
	}
	

	
	/*
	@GET
	@Path("/messages")
	//@Produces(MediaType.APPLICATION_JSON)
	public String getPerformativesList() {
		return "<h1>Hello World<h1>";
	}
	*/
	 
}
