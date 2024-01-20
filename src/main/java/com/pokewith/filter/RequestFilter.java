package com.pokewith.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Slf4j
public class RequestFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);
        long start = System.currentTimeMillis();
        chain.doFilter(requestWrapper, response);
        long end = System.currentTimeMillis();
        long elapseTime = end - start;
        ObjectMapper mapper = new ObjectMapper();
        String params = "";
        try {
            params = mapper.writeValueAsString(request.getParameterMap());
        } catch (JsonProcessingException ignored){
        }
        log.info("{" +
                    "\"method\":\"{}\", " +
                    "\"url\":\"{}\", " +
                    "\"statusCode\" : {}, " +
                    "\"latency\" : {}, " +
                    "\"id\" : {}, " +
                    "\"userAgent\" : \"{}\", " +
                    "\"param\" : {}, " +
                    "\"body\" : {}" +
                "}",
                ((HttpServletRequest) request).getMethod(),
                ((HttpServletRequest)request).getRequestURI(),
                responseWrapper.getStatus(),
                elapseTime,
                request.getAttribute("id"),
                ((HttpServletRequest) request).getHeader("user-agent"),
                params,
                getRequestBody(requestWrapper));
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            if (wrapper.getContentLength() > 0) {
                payload = new String(wrapper.getContentAsByteArray());
            }
        }
        return payload != null ? payload.replace("\n", "") : "\"\"";
    }
}
