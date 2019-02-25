package worshop.chat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;

@Path("/chatservice")
public class ChatService {

	@GET
	@Produces("application/json")
	public String getResponse(@QueryParam("conversationMsg") String conversationMsg, @QueryParam("conversationCtx") String conversationCtx) {
		return "{\"response\":\"Hi!!!\", \"context\":{\"msg\": \"" + conversationMsg +"\", \"ctx\": " + conversationCtx +"}}";
	}	
	
}
