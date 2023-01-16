package kr.or.connect.visitorbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"kr.or.connect.visitorbook.dao", "kr.or.connect.visitorbook.service"})
@Import({DBConfig.class})
public class ApplicationConfig {

}
