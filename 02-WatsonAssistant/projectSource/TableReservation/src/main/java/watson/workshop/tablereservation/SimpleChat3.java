//https://console.bluemix.net/docs/services/conversation/develop-app.html#building-a-client-application

/*
 * Example 3: maintains state.
 */

package watson.workshop.tablereservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.LogManager;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v1.model.RuntimeIntent;

public class SimpleChat3 {
  public static void main(String[] args) {

    // Suppress log messages in stdout.
    LogManager.getLogManager().reset();

    // Set up Assistant service.
	Assistant service = new Assistant("2018-02-16");
    service.setUsernameAndPassword("b611a43b-942e-4cfe-b06e-ed296ce1a3dc", "LDp6qZVwPVKu"); 
    String workspaceId = "b8921661-8de2-4e34-a248-429bac56cb85";

    // Initialize with empty value to start the conversation.
    MessageOptions options = new MessageOptions.Builder(workspaceId).build();
    Context context = new Context();

    try {
		// Main input/output loop
		do {
		  // Send message to Assistant service.
		  MessageResponse response = service.message(options).execute();

		  // If an intent was detected, print it to the console.
		  List<RuntimeIntent> responseIntents = response.getIntents();
		  if(responseIntents.size() > 0) {
		    System.out.println("Detected intent: #" + responseIntents.get(0).getIntent());
		  }

		  // Print the output from dialog, if any.
		  List<String> responseText = response.getOutput().getText();
		  if(responseText.size() > 0) {
		    System.out.println(responseText.get(0));
		  }

		  // Update the stored context with the latest received from the dialog.
		  context = response.getContext();

		  // Prompt for next round of input.
		  System.out.print(">> ");
		  BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		  String inputText = in.readLine();
		  InputData input = new InputData.Builder(inputText).build();
		  options = new MessageOptions.Builder(workspaceId).input(input).context(context).build();
		} while(true);
	} catch (RuntimeException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}

