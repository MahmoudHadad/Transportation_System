package com.guides;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;


@Path("/")
@Produces("text/html")
public class Services {
	
	private static final Logger logger = Logger.getLogger(Services.class
			.getCanonicalName());
	
	static{
		logger.warning("In Services class");
	}
	@Path("/test")
	@GET
	public Response test(){
		logger.warning("In Test");
		return Response.ok(new Viewable("/Test")).build();
		
	}
	
}
