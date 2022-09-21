package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.MailDetailHis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailDetailHisRepository extends JpaRepository<MailDetailHis, Integer>, MailDetailHisRepositoryCustom {

}
