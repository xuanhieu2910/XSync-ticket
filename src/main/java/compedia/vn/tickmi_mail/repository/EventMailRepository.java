package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.EventMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface EventMailRepository extends JpaRepository<EventMail,Long> {
}
