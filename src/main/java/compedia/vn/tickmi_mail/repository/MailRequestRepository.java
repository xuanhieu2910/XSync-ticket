package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.MailRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRequestRepository extends JpaRepository<MailRequest, Long> , MailRequestRepositoryCustom {


}
