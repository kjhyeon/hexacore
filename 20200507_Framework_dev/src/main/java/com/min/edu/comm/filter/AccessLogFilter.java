package com.min.edu.comm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessLogFilter implements Filter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		System.out.println("---------- AccessLog 종료 ----------");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		
		String remoteAddr = StringUtils.defaultString(request.getRemoteAddr(), "-");
		String uri = StringUtils.defaultIfEmpty(request.getRequestURI(), "");
		String url = (request.getRequestURL()==null)?"":request.getRequestURL().toString();
		String queryString = StringUtils.defaultString(request.getQueryString(), "");
		String referer = StringUtils.defaultString(request.getHeader("Referer"), "-");
		String userAgent = StringUtils.defaultString(request.getHeader("User-Agent"), "-");
		String fullUrl = url;
		fullUrl += StringUtils.isNotEmpty(queryString)?"?"+queryString:queryString;
		StringBuffer result = new StringBuffer();
		result.append(remoteAddr)
		.append(":")
		.append(fullUrl)
		.append(":")
		.append(referer)
		.append(":")
		.append(userAgent);
		
		logger.info("[LOG FILTER] {}", result.toString());
		
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("---------- AccessLog 시작 ----------");
	}

}
