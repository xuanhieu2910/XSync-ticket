package compedia.vn.tickmi_mail.repository;

import compedia.vn.tickmi_mail.entity.Provider;

import java.util.Optional;

public interface ProviderRepositoryCustom {

    Optional<Provider> findProviderByUserId(Long userId);
}
