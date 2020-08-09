package com.msr.flowable.demo.config;

import com.msr.flowable.demo.common.SessionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MaiShuRen
 * @version v1.0
 * @date 2020/8/8 17:04
 */

//@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final String OUTER_API = "/login/**";

    private static final String OPEN_API = "/open/**";

    private static final String LOGIN_API = "/login/**";

    private static final String ERROR_API = "/error/**";
    static AntPathMatcher springAntMatcher = new AntPathMatcher();
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //获取请求路径
        String requestUrl = request.getServletPath();

        log.debug("请求的api---------------" + requestUrl);

        if (checkHoldCurrentURL(requestUrl)) {
            Object user = request.getSession().getAttribute(SessionConst.SESSION_USERNAME_KEY);
            if (user == null) {
                // 未登录
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


    /**
     * 是否验证当前url
     *
     * @param requestUrl
     * @return
     */
    private boolean checkHoldCurrentURL(String requestUrl) {
        if (springAntMatcher.match(OUTER_API, requestUrl) || springAntMatcher.match(OPEN_API, requestUrl)
                || springAntMatcher.match(LOGIN_API, requestUrl) || springAntMatcher.match(ERROR_API, requestUrl)) {
            return false;
        }
        return true;
    }


}
