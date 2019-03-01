package worshop.chat;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.json.JSONObject;

import javax.ws.rs.Produces;

@Path("/chatservice")
public class ChatService {

	public String urlDB;
	public String userDB;
	public String passwordDB;
	
	public ChatService(){
		try {
			loadProperties();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadProperties() throws Exception{
		
		Map<String,String> env = System.getenv();
		
		JSONObject vcap = null;
		
		//VCAP_SERVICES is loaded as an environment variable if running in from IBM Cloud.
		//Otherwise,  it is read locally from configuration.properties.
		if (env.containsKey("VCAP_SERVICES")) {
			vcap = new JSONObject(env.get("VCAP_SERVICES"));
		} else {
			InputStream in = null;

			try {
				Properties localEnv = new Properties();
				in = getClass().getResourceAsStream("/configuration.properties");
				localEnv.load(in);
				in.close();
				vcap = new JSONObject(localEnv.getProperty("VCAP_SERVICES"));
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}

		// DB2 entry
		JSONObject service = (JSONObject) vcap.getJSONArray("dashDB For Transactions").getJSONObject(0);

		if (service != null) {
			JSONObject creds = service.getJSONObject("credentials");
			urlDB = creds.getString("jdbcurl");
			userDB = creds.getString("username");
			passwordDB = creds.getString("password");
		}
	}
	
	@GET
	@Produces("application/json")
	public String getResponse(@QueryParam("conversationMsg") String conversationMsg, @QueryParam("conversationCtx") String conversationCtx) {
		return "{\"response\":\"Hi!!!\", \"context\":{\"msg\": \"" + conversationMsg +"\", \"ctx\": " + conversationCtx +"}}";
	}	
	
}
