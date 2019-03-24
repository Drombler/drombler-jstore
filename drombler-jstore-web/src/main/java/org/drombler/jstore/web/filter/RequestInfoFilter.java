package org.drombler.jstore.web.filter;

import org.drombler.jstore.model.RequestInfo;
import org.softsmithy.lib.util.context.ContextProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.drombler.jstore.web.filter.RequestInfoFilter.ORDER;

@Component
@Order(ORDER)
public class RequestInfoFilter extends OncePerRequestFilter {
    public static final ContextProperty<RequestInfo> REQUEST_INFO = new ContextProperty<>("requestInfo", RequestInfo.class);

    public static final int ORDER = Ordered.HIGHEST_PRECEDENCE + 10;

    @Autowired
    private RequestInfoSupportService requestInfoSupportService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        RequestInfo requestInfo = requestInfoSupportService.createRequestInfo();
        requestInfo.setUserPrincipal(request.getUserPrincipal());
        REQUEST_INFO.setValue(request::setAttribute, requestInfo);

        filterChain.doFilter(request, response);
    }
}
