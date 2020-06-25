package com.hausarbeit;


import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class RestServiceApplication {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder()
                .sources(RestServiceApplication.class)
                .run(args);
    }
    @Bean
    public TomcatServletWebServerFactory tomcatFactory() {
        return new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(org.apache.catalina.startup.Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }
            // register the DataSource in JNDI by overriding the postProcessContext
            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/postgres");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.postgresql.Driver");
                resource.setProperty("url", "jdbc:postgresql://localhost:5432/test1");
                resource.setProperty("username", "postgres");
                resource.setProperty("password", System.getenv("DB_PW"));
                context.getNamingResources()
                        .addResource(resource);
            }
        };
    }
}

