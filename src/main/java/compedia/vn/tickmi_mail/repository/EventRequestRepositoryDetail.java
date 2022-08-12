package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRequestRepositoryDetail extends JpaRepository<EventRequestDetail,Long>, EventRequestRepositoryDetailCustom{
}
