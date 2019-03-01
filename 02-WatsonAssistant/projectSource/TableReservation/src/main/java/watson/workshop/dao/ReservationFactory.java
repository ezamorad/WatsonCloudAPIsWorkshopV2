package watson.workshop.dao;

public class ReservationFactory {

	public ReservationDao getReservationDao() {
		return new ReservationDao();
	}
}
