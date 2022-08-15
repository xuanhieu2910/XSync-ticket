package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.MailRoot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRootRepository extends JpaRepository<MailRoot, Long> , MailRootRepositoryCustom {


}
