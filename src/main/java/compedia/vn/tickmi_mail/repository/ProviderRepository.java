package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<Provider,Long>, ProviderRepositoryCustom {
}
