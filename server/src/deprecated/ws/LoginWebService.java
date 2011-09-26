package deprecated.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import services.UserService;

@Path("/isRegistered")
public class LoginWebService {
	
	private final UserService userService = UserService.getInstance();
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String isRegistered(@QueryParam("username") String username){
		System.out.println(".Login.");
		return (userService.isRegistered(username)) ? "true" : "false";
	}

}
 