package pk.org.rayn.zindagi.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

enum RequestMethods
{
    GET,
    POST,
    DELETE,
    PUT,
    PATCH
}

@Configuration
public class CorsConfigs {
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer()
        {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/**")
                        .allowedMethods(RequestMethods.PATCH.name(),RequestMethods.PUT.name(),RequestMethods.POST.name(),RequestMethods.GET.name(),RequestMethods.DELETE.name(), RequestMethod.OPTIONS.name())
                        .allowedHeaders("*")
                        .allowedOrigins("*")
                        .allowCredentials(true);
            }
        };
    }
}
