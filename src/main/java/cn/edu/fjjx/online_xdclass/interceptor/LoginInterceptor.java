package cn.edu.fjjx.online_xdclass.interceptor;

import cn.edu.fjjx.online_xdclass.utils.JWTUtil;
import cn.edu.fjjx.online_xdclass.utils.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String accesToken = request.getHeader("token");
            if (accesToken==null){
                request.getParameter("token");
            }
            if (StringUtils.isNoneBlank(accesToken)) {
                Claims claims = JWTUtil.checkJWT(accesToken);
                if (claims==null){
                    //告知登录过期，重新登录
                    sendJsonMessage(response, JsonData.buildError("登录过期，重新登录"));
                    return false;
                }
                Integer id = (Integer) claims.get("id");
                String name = (String) claims.get("name");
                request.setAttribute("user_id",id);
                request.setAttribute("name",name);
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        sendJsonMessage(response, JsonData.buildError("登录失败，重新登录"));
        return false;
    }

    //响应json数据给前端
    public static void sendJsonMessage(HttpServletResponse response,Object obj){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(objectMapper.writeValueAsString(obj));
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
