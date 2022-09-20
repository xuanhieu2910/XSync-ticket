package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.MailInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailInputRepository extends JpaRepository<MailInput, Integer>, MailInputRepositoryCustom {

}
