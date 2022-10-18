package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.EventRequestHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRequestHisRepository extends JpaRepository<EventRequestHis,Long>, EventRequestHisRepositoryCustom{
}
