package com.example.java_demo.multi_thread.threadLocal;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

@Slf4j
public class WebFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest, servletResponse);

        List<Long> ids = TestThreadLocal.get();
        log.info("current thread:{}, ids:{}", Thread.currentThread().getId(), ids);
    }

    @Override
    public void destroy() {
    }
}
