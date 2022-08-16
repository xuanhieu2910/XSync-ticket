package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.MailDetailHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailDetailHisRepository extends JpaRepository<MailDetailHis,Long>, MailDetailHisRepositoryCustom {

}
