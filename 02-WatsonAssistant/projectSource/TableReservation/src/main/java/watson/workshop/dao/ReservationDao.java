package watson.workshop.dao;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.cloudant.client.api.Database;

import watson.workshop.cloudant.CloudantClientMgr;
import watson.workshop.model.Reservation;

public class ReservationDao {
	
//	public static List<Reservation> getSurveyList() {
//		
//		List<Reservation> reservationList = new ArrayList<Reservation>();
//		
//		Database db = null;
//		try {
//			db = CloudantClientMgr.getDB();
//			reservationList = db.find("\"selector\": {\"id\": " + id  + "}",  Reservation.class);
//
//			List<HashMap> alldocuments = 
//				db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(HashMap.class);
//			
//			Reservation reservation;
//			for(Map<String, Object> data : alldocuments) {
//				reservation = new Reservation();
//				reservation.setDate((String)data.get("date"));
//				reservation.setSubmissionDate(new Date(((Double)data.get("submissionDate")).longValue()));
//				reservation.setTime((String)data.get("time"));
//				reservation.setGuests((String)data.get("guests"));
//				reservationList.add(reservation);
//			}
//		} catch (Exception re) {
//			re.printStackTrace();
//		}
//		
//		return reservationList;
//	}

//	public boolean check(String email) {
//		
//		Database db = null;
//		try {
//			db = CloudantClientMgr.getDB();
//			
//			HashMap<String, Object> obj = (email == null) ? null : db.find(HashMap.class, email);
//
//			if (obj == null) {
//				return true;
//			} else {
//				return false;
//			}
//			
//		} catch (Exception re) {
//			if(!(re instanceof NoDocumentException)) {
//				re.printStackTrace();
//			}
//		}
//		
//		return true;
//	}

	public void save(Reservation reservation) {
		
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		
		reservation.setSubmissionDate(simpleDateFormat.format(GregorianCalendar.getInstance().getTime()));
		
		Database db = null;
		try {
			db = CloudantClientMgr.getDB();
		
			Map<String, Object> data = new HashMap<String, Object>();
			
			data.put("date", reservation.getDate());
			data.put("time",reservation.getTime());
			data.put("guests", reservation.getGuests());
			data.put("reservation", reservation.getSubmissionDate().toString());
			db.save(data);
		} catch (Exception re) {
			re.printStackTrace();
		}
	}
}
