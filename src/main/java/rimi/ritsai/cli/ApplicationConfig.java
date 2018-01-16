package rimi.ritsai.cli;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = { "rimi.ritsai.cli" })
@ImportResource({ "classpath*:META-INF/shell/**/rimi-*.xml" })
public class ApplicationConfig {

}
