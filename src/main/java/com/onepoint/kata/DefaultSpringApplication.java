package com.onepoint.kata;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

/**
 * The Class SpringBootWebApplication.
 *
 * @author srh
 */
@SpringBootApplication
public class DefaultSpringApplication extends SpringBootServletInitializer{

	
    /** Logger */
    private static final Logger loggerStatic = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /* (non-Javadoc)
     * @see org.springframework.boot.web.support.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DefaultSpringApplication.class);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
    
        
        SpringApplication app = new SpringApplication(DefaultSpringApplication.class);
        Environment env = app.run(args).getEnvironment();
        loggerStatic.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application api is running!" +
                        "Local: \t\t\thttp://127.0.0.1:{}\n\t" );
    }


}
