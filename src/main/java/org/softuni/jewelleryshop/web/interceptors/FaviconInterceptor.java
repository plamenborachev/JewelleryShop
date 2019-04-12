package org.softuni.jewelleryshop.web.interceptors;

import org.softuni.jewelleryshop.GlobalConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView)
            throws Exception {
        String link = GlobalConstants.FAVICON_LINK;

        if (modelAndView != null) {
            modelAndView.addObject("favicon", link);
        }
    }
}
