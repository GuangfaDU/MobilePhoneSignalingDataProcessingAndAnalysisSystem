package cn.edu.szu.gateway.filter;

import cn.edu.szu.gateway.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author GuangfaDU
 * @version 1.0
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final String EXPECTED_LOGIN_PATH = "/security/login";
    private static final String AUTHORIZE_TOKEN = "Authorization";
    private static final String SP_ROLE_QUERY = "/user-service/role/list";
    private static final String SP_REGISTRY = "/mail-service/registry";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //登录操作直接放行
        String path = request.getURI().getPath();
        if (path.startsWith(EXPECTED_LOGIN_PATH) || path.startsWith(SP_ROLE_QUERY) || path.startsWith(SP_REGISTRY)) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        if (StringUtils.isEmpty(token)) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return response.setComplete();
        }

        try {
            JwtUtil.parseJwt(token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
