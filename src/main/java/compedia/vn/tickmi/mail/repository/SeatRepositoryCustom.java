package compedia.vn.tickmi.mail.repository;

public interface SeatRepositoryCustom {
    boolean updateUsedStatusBySeatCode(String seatCode, Long eventId);
}
