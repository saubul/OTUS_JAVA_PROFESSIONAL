package ru.otus.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/add", "/div", "/multiply", "/subtract"})
public class LogFilter extends HttpFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        StringBuilder reqSB = new StringBuilder();
        LOGGER.info(
                (reqSB
                        .append("REQUEST: ")
                        .append(req.getMethod())
                        .append(" ")
                        .append(req.getRequestURL())
                )
                        .toString());
        chain.doFilter(req, res);
    }
}
