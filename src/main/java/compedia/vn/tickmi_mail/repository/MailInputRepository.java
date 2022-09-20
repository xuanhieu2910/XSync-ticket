package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.MailInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailInputRepository extends JpaRepository<MailInput,Integer> ,MailInputRepositoryCustom {

}
