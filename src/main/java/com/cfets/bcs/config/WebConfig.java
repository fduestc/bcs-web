package com.cfets.bcs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
  // private Logger logger = Logger.getLogger(WebMvcConfigurerAdapter.class);
  @Autowired
  private AppConfig appConfig;

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    // Accept headers are messed up and you can’t normally change them
    // either (unless you use JavaScript and AJAX).
    // http://www.newmediacampaigns.com/blog/browser-rest-http-accept-headers
    // 因此应该禁用AcceptHeader来决定返回什么数据
    // 默认为json返回
    // 尽量采用后缀的方式访问
    // 其他的需要对应的后缀，如test.xml/test.json/test.html
    // 也可以采用参数的方式，如...?mediaType=xml
    configurer.favorPathExtension(true).favorParameter(true).parameterName("mediaType").ignoreAcceptHeader(true)
        .useJaf(false).defaultContentType(MediaType.APPLICATION_JSON).mediaType("html", MediaType.TEXT_HTML)
        .mediaType("xml", MediaType.APPLICATION_XML).mediaType("json", MediaType.APPLICATION_JSON);
  }

  /**
   * Create the CNVR. Get Spring to inject the ContentNegotiationManager created
   * by the configurer (see previous method).
   */
  @Bean
  public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setContentNegotiationManager(manager);
    return resolver;
  }

  // Will map to the JSP page: "WEB-INF/views/accounts/list.jsp"
  @Bean(name = "jspViewResolver")
  public ViewResolver getJspViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    resolver.setOrder(2);
    return resolver;
  }

  // http://stackoverflow.com/questions/14861720/annotation-configuration-replacement-for-mvcresources-spring
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    // Handles HTTP GET requests for /resources/** by efficiently serving up
    // static resources in the ${webappRoot}/resources directory
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

  }

}
