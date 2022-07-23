package cn.edu.fjjx.online_xdclass.utils;

import cn.edu.fjjx.online_xdclass.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTUtil {

    private static final long EXPIRE = 6000*60*24*7; //过期时间：一周

    private static final String SECRET = "fjjx.edu.cn"; //密钥

    private static final String TOKEN_PREFIX = "fjjx"; //令牌前缀

    private static final String SUBJECT = "fjjx"; //subject


    //根据用户信息生成令牌
    public static String geneJsonWebToken(User user){
        String token =  TOKEN_PREFIX + Jwts.builder().setSubject(SUBJECT)
                .claim("head_img",user.getHeadImg())
                .claim("id",user.getId())
                .claim("name",user.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
        return token;
    }

    //校验token的方法
    public static Claims checkJWT(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }
}
