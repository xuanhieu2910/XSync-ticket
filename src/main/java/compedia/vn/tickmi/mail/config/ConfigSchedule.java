package compedia.vn.tickmi.mail.config;

import compedia.vn.tickmi.mail.repository.EventRequestDetailRepository;
import compedia.vn.tickmi.mail.repository.EventRequestRepository;
import compedia.vn.tickmi.mail.utils.Constant;
import compedia.vn.tickmi.mail.utils.DbConstant;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableScheduling
public class ConfigSchedule {

    @Autowired
    EventRequestRepository eventRequestRepository;
    @Autowired
    EventRequestDetailRepository eventRequestDetailRepository;



    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(Constant.SIZE_POOL_THREAD);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }

    @Bean
    public void updateWholeTable() {
        updateWholeEventRequest();
        updateWholeEventRequestDetail();
        DbConstant.IS_FLAT_RUN_JOB = true;
    }

    public void updateWholeEventRequest() { eventRequestRepository.updateWholeEventRequestToNew(); }

    public void updateWholeEventRequestDetail() {
        eventRequestDetailRepository.updateWholeEventRequestDetail();
    }


}
