package compedia.vn.tickmi.mail.repository;

import compedia.vn.tickmi.mail.entity.Provider;

import java.util.Optional;

public interface ProviderRepositoryCustom {

    Optional<Provider> findProviderByUserId(Integer userId);
}
