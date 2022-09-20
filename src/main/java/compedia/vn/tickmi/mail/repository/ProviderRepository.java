package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long>, ProviderRepositoryCustom {
}
