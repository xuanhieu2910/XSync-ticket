package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRequestRepository extends JpaRepository<EventRequest, Long>, EventRequestRepositoryCustom {
}
