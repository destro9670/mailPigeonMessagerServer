package ua.destro967.mailPigeon.filters;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;




public class CORSFilter implements Filter {

    private static final String ONE_HOUR = "3600";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", ONE_HOUR);
        response.setHeader("Access-Control-Allow-Headers", "x-device-user-agent, Content-Type, Origin, X-Requested-With, Content-Type, Accept, Authorization");

        if (req instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            if (httpServletRequest.getHeader(HttpHeaders.ORIGIN) != null
                    && httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.name())
                    && httpServletRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null) {
                System.out.println("Received an OPTIONS pre-flight request.");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }

}

