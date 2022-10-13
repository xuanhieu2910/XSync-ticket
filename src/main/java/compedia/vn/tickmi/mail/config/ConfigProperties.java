package compedia.vn.tickmi.mail.config;

import com.antkorwin.xsync.XSync;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Bean
    public XSync<Long> xSync(){
        return new XSync<>();
    }

}
