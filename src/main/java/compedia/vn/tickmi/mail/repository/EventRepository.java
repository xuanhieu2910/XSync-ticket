package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long>, EventRepositoryCustom {
}
