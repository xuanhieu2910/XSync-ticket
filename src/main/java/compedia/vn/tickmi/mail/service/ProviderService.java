package compedia.vn.tickmi.mail.service;

import compedia.vn.tickmi.mail.entity.Provider;
import compedia.vn.tickmi.mail.repository.ProviderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ProviderService {

    @Autowired
    ProviderRepository providerRepository;


}
