package com.cfets.bcs.config;

import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class WebMappingExceptionResolver extends SimpleMappingExceptionResolver {
  private static Logger logger = LoggerFactory.getLogger(WebMappingExceptionResolver.class);

  public WebMappingExceptionResolver() {
    setWarnLogCategory(WebMappingExceptionResolver.class.getName());

    Properties mappings = new Properties();
    mappings.setProperty("InfoAbsentException", "exception/info_absent");

    setExceptionMappings(mappings); // None by default
    setDefaultErrorView("error/error"); // No default
  }

  @Override
  public String buildLogMessage(Exception e, HttpServletRequest req) {
    logger.error(e.getLocalizedMessage());
    return "MVC exception: " + e.getLocalizedMessage();
  }

  @Override
  protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
      Exception exception) {
    // Call super method to get the ModelAndView
    ModelAndView mav = super.doResolveException(request, response, handler, exception);

    // Make the full URL available to the view - note ModelAndView uses
    // addObject()
    // but Model uses addAttribute(). They work the same.
    mav.addObject("url", request.getRequestURL());
    mav.addObject("timestamp", new Date());
    // mav.addObject("status", 500);

    return mav;
  }
}
