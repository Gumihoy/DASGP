package com.gumihoy.dasgp.admin.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kongtong.ouyang on 2017/10/11.
 */
@Component
public class AdminHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private static final Logger LOG = LoggerFactory.getLogger(AdminHandlerExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("/404");
        HandlerMethod method = (HandlerMethod) handler;
//
//        String accept = request.getHeader("accept");
//        ResponseBody body = method.getMethodAnnotation(ResponseBody.class);
//        if (accept != null && accept.contains("serviceregistry/json")) {
//
//        }

        LOG.error("", ex);
        return modelAndView;
    }
}
