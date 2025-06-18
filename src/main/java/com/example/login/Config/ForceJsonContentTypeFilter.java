package com.example.login.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ForceJsonContentTypeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(req) {
            @Override
            public String getContentType() {
                // On force application/json
                return MediaType.APPLICATION_JSON_VALUE;
            }
            @Override
            public String getHeader(String name) {
                if ("Content-Type".equalsIgnoreCase(name)) {
                    return MediaType.APPLICATION_JSON_VALUE;
                }
                return super.getHeader(name);
            }
        };
        chain.doFilter(wrapper, response);
    }
}
