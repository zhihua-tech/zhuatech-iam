/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.iam.config;

import cn.zhuatech.iam.domain.DomainCatalog;
import cn.zhuatech.iam.model.WorkItem;
import cn.zhuatech.iam.repository.WorkItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedData(WorkItemRepository repository, DomainCatalog catalog) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(catalog.seedItems().stream()
                    .map(item -> new WorkItem(item.recordNo(), item.title(), item.status(), item.owner(), item.priority()))
                    .toList());
            }
        };
    }
}
