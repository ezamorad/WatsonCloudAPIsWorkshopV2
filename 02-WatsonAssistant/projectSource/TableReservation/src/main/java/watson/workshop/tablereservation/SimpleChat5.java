//https://console.bluemix.net/docs/services/conversation/develop-app.html#building-a-client-application


/*
 * Example 4: Stored reservation into Database
 */

package watson.workshop.tablereservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.LogManager;

import org.json.JSONObject;

import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;

import watson.workshop.dao.ReservationDao;
import watson.workshop.dao.ReservationFactory;
import watson.workshop.model.Reservation;

public class SimpleChat5 {
  public static void main(String[] args) {

    try {
		// Suppress log messages in stdout.
		LogManager.getLogManager().reset();

		// Set up Assistant service.
		Assistant service = new Assistant("2018-09-20");
	    service.setUsernameAndPassword("b611a43b-942e-4cfe-b06e-ed296ce1a3dc", "LDp6qZVwPVKu"); 
	    String workspaceId = "b8921661-8de2-4e34-a248-429bac56cb85";

		// Initialize with empty value to start the conversation.
		MessageOptions options = new MessageOptions.Builder(workspaceId).build();
		Context context = new Context();
		String currentAction = "";

		// Main input/output loop
		do {
		  // Send message to Assistant service.
		  MessageResponse response = service.message(options).execute();
		  System.out.println(response);
		  
		  // Print the output from dialog, if any.
		  List<String> responseText = response.getOutput().getText();
		  if(responseText.size() > 0) {
		    System.out.println(responseText.get(0));
		  }

		  // Update the stored context with the latest received from the dialog.
		  context = response.getContext();

		  // Check for action flags sent by the dialog.
		  JSONObject outputObj = new JSONObject(response.getOutput().toString());
		  if(outputObj.has("action")) {
		    currentAction = outputObj.getString("action");
		  }
		  else {
		    currentAction = "";
		  }
		  
		  //System.out.println("Actions are" + outputObj.toString() + ".");

		  // User confirms saving the reservation
		  if(currentAction.equals("save")) {
			  
			  System.out.println("Actions is SAVE");
			  
			  ReservationFactory reservatioFactory =  new ReservationFactory();
			  ReservationDao reservationDao = reservatioFactory.getReservationDao();
			  
			  Reservation reservation = new Reservation();
			  reservation.setDate((String)context.get("date"));
			  reservation.setTime((String)context.get("time"));
			  reservation.setGuests(((Double)context.get("guests")).intValue());
			  
			  reservationDao.save(reservation);
			  
			  //no need to clean the action since it does not return back in the context.
		  }

		  // If we're not done, prompt for next round of input.
		  if(!currentAction.equals("quit")) {
		    System.out.print(">> ");
		    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String inputText = in.readLine();
		    InputData input = new InputData.Builder(inputText).build();
		    options = new MessageOptions.Builder(workspaceId).input(input).context(context).build();
		  }

		} while(!currentAction.equals("quit"));
	} catch (SecurityException e) {
		e.printStackTrace();
	} catch (RuntimeException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}

