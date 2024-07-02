package com.gt.genti.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gt.genti.filter.MDCFilter;
import com.gt.genti.filter.ServletWrappingFilter;

// @Profile("!local") // 추후 운영환경에서만
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<ServletWrappingFilter> wrappingFilter() {
		FilterRegistrationBean<ServletWrappingFilter> filterRegistrationBean = new FilterRegistrationBean<>(
			new ServletWrappingFilter());
		filterRegistrationBean.setOrder(1);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean<MDCFilter> MDCFilter() {
		FilterRegistrationBean<MDCFilter> filterRegistrationBean = new FilterRegistrationBean<>(
			new MDCFilter());
		filterRegistrationBean.setOrder(2);
		return filterRegistrationBean;
	}
}
