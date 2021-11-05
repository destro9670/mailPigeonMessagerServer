package ua.destro967.mailPigeon.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth";
    private static final String REGISTER_ENDPOINT = "/api/v1/register";
    private static final String REFRESH_ENDPOINT = "/api/v1/refresh";

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

    /*    if (((HttpServletRequest) servletRequest).getRequestURI().contains(LOGIN_ENDPOINT)||
                ((HttpServletRequest) servletRequest).getRequestURI().contains(REGISTER_ENDPOINT)||
                ((HttpServletRequest) servletRequest).getRequestURI().contains(REFRESH_ENDPOINT)){

        }else
*/
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);

        /*
        if (!servletRequest.().contains("/api/v1/account/import")) {
            final JJWTService jjwtService = new JJWTService();

            if (token == null || !jjwtService.parseJWTToken(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                filterChain.doFilter(req, res);
            }
        }*/
    }
}
