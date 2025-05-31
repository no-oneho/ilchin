package org.groupware.ilchin.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.groupware.ilchin.exception.CustomException;
import org.groupware.ilchin.exception.UserException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        try{
            if (handler instanceof HandlerMethod handlerMethod) {
				Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);

                if (authAnnotation != null) {
                    boolean includeUserIdx = authAnnotation.includeUserIdx();
                    if (includeUserIdx) {

                        if (!request.getHeader("Authorization").startsWith("Bearer ")) {
                            throw new CustomException(UserException.HANDLE_ACCESS_DENIED);
                        }
                        String token = request.getHeader("Authorization").split(" ")[1];

                        Long userId = TokenProvider.getUserIdFromToken(token);
                        request.setAttribute("userId", userId);
                        AuthHolder.setUserId(userId);
                    }
                    return true;
                }
            }

            return HandlerInterceptor.super.preHandle(request, response, handler);
        } catch (NullPointerException e) {
            throw new CustomException(UserException.HANDLE_ACCESS_DENIED);
        }

    }

}
