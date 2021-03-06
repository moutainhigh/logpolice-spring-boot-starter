package com.logpolice.infrastructure.config;

import com.logpolice.application.NoticeService;
import com.logpolice.application.NoticeServiceFactory;
import com.logpolice.domain.repository.ExceptionNoticeRepository;
import com.logpolice.domain.repository.ExceptionStatisticRepository;
import com.logpolice.infrastructure.utils.LockUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 本地缓存自动装配
 *
 * @author huang
 * @date 2019/8/28
 */
@Configuration
@AutoConfigureAfter({LogpoliceRedisAutoConfiguration.class, LogpoliceLoclCacheAutoConfiguration.class,
        LogpoliceMailAutoConfiguration.class, LogpoliceDingDingAutoConfiguration.class})
public class LogpoliceServiceAutoConfiguration {

    private final List<ExceptionNoticeRepository> exceptionNoticeRepositories;
    private final List<ExceptionStatisticRepository> exceptionStatisticRepositories;
    private final List<LockUtils> lockUtils;


    @Autowired
    public LogpoliceServiceAutoConfiguration(List<ExceptionNoticeRepository> exceptionNoticeRepositories,
                                             List<ExceptionStatisticRepository> exceptionStatisticRepositories,
                                             List<LockUtils> lockUtils) {
        this.exceptionNoticeRepositories = exceptionNoticeRepositories;
        this.exceptionStatisticRepositories = exceptionStatisticRepositories;
        this.lockUtils = lockUtils;
    }

    @Bean
    public NoticeServiceFactory noticeServiceFactory() {
        return new NoticeServiceFactory(exceptionNoticeRepositories, exceptionStatisticRepositories, lockUtils);
    }

    @Bean
    public NoticeService noticeService() {
        return new NoticeService(noticeServiceFactory());
    }

}
