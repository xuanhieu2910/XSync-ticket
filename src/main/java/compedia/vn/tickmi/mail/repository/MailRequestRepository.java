package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.MailRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRequestRepository extends JpaRepository<MailRequest, Integer>, MailRequestRepositoryCustom {


}
