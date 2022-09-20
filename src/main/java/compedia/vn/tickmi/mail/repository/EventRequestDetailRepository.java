package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRequestDetailRepository extends JpaRepository<EventRequestDetail, Long>, EventRequestRepositoryDetailCustom {
}
