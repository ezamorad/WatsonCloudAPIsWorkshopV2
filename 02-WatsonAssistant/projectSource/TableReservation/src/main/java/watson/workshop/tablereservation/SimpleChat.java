//https://console.bluemix.net/docs/services/conversation/develop-app.html#building-a-client-application

/*
 * Example 1: sets up service wrapper, sends initial message, and
 * receives response.
 */

package watson.workshop.tablereservation;

import java.util.logging.LogManager;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

public class SimpleChat {

	public static void main(String[] args) {

		// Suppress log messages in stdout.
		LogManager.getLogManager().reset();

		// Set up Assistant service.
		Assistant service = new Assistant("2018-02-16");
	    service.setUsernameAndPassword("b611a43b-942e-4cfe-b06e-ed296ce1a3dc", "LDp6qZVwPVKu"); 
	    String workspaceId = "b8921661-8de2-4e34-a248-429bac56cb85";

		// Start assistant with empty message.
		MessageOptions options = new MessageOptions.Builder(workspaceId).build();
		MessageResponse response = service.message(options).execute();

		System.out.println(response.getOutput().getText().get(0));
	}

}
