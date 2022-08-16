package compedia.vn.tickmi_mail.service;

import compedia.vn.tickmi_mail.entity.Provider;
import compedia.vn.tickmi_mail.repository.ProviderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProviderService {

    private final static Logger logger = LoggerFactory.getLogger(ProviderService.class);

    @Autowired
    ProviderRepository providerRepository;

    public Optional<Provider> findProviderByUserId (Long userId){
        return providerRepository.findProviderByUserId(userId);
    }
}
