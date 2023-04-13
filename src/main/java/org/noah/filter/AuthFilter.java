package org.noah.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.subject.WebSubject;
import org.noah.entity.AuthToken;
import org.noah.entity.ResponseEntity;
import org.noah.enums.CommonError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends AuthenticatingFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);

    /**
     * 获取请求的accessToken
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        // 从请求Header中获取token
        String token = httpRequest.getHeader("accessToken");
        // 如果header中不存在token，则从参数中获取token
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter("accessToken");
        }
        log.info("从请求中获取AccessToken:{}", token);
        return token;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问 例如我们提供一个地址 GET /article 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西 所以我们在这里返回true，Controller中可以通过
     * subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                log.debug("isOPTIONS");
                return true;
            }
        }
        log.debug("isNotOPTIONS");
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException exception,
                                     ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            // 处理登录失败的异常
            String message = "\"登录失效，请重新登录\"";
            Throwable throwable = exception.getCause() == null ? exception : exception.getCause();
            log.error("{}:{}", message, throwable.getMessage().toString());
            ResponseEntity responseEntity = ResponseEntity.fail(CommonError.LOGIN_LOST, message);
            httpResponse.getWriter().print(JSON.toJSONString(responseEntity));
        } catch (IOException ioException) {
            log.error("请求响应返回失败", ioException);
        }
        return false;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                httpServletRequest.getHeader("Access-Control-Request-Headers"));
        System.out.println(httpServletRequest.getRequestURI());
        log.info("Request: {}", httpServletRequest.getRequestURI());
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        log.debug("createToken");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String accessToken = getRequestToken(httpServletRequest);
        AuthToken token = new AuthToken(accessToken);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return new AuthToken(accessToken);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        log.debug("onAccessDenied");
        // 获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isEmpty(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json;charset=utf-8");
            ResponseEntity responseEntity = ResponseEntity.fail(CommonError.LOGIN_LOST, "请登录");
            httpResponse.getWriter().print(JSON.toJSONString(responseEntity));
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 登录验证处理，父类本来就有，只是有个bug，按官方给出的方法进行了重写
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        AuthenticationToken token = this.createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                //修复bug代码，也算个不小的坑吧
                Subject subject =  new WebSubject.Builder(request, response).buildSubject();
                subject.login(token);
                ThreadContext.bind(subject);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

}
