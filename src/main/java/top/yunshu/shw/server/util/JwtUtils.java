package top.yunshu.shw.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import top.yunshu.shw.server.entity.LoginUser;
import top.yunshu.shw.server.exception.CasException;

import java.util.Date;

import static top.yunshu.shw.server.cas.CasFilter.MAPPER;

/**
 * Jwt 工具类
 *
 * @author itning
 */
public class JwtUtils {
    private static final String PRIVATE_KEY = "hxcshw";
    private static final String LOGIN_USER = "loginUser";
    private static final String DEFAULT_STR = "null";

    private JwtUtils() {

    }

    public static String buildJwt(Object o) throws JsonProcessingException {
        return Jwts.builder()
                //SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)
                //expTime是过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .claim(LOGIN_USER, MAPPER.writeValueAsString(o))
                //令牌的发行者
                .setIssuer("itning")
                .compact();
    }

    public static LoginUser getLoginUser(String jwt) {
        if (DEFAULT_STR.equals(jwt)) {
            throw new CasException("请先登陆", HttpStatus.UNAUTHORIZED);
        }
        try {
            //解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    //SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                    .setSigningKey(PRIVATE_KEY)
                    //jwt是JWT字符串
                    .parseClaimsJws(jwt)
                    .getBody();
            //获取自定义字段key
            String loginUserJson = claims.get(LOGIN_USER, String.class);
            LoginUser loginUser = MAPPER.readValue(loginUserJson, LoginUser.class);
            System.out.println(loginUser);
            //判断自定义字段是否正确
            if (loginUser == null) {
                throw new CasException("登陆失败", HttpStatus.UNAUTHORIZED);
            } else {
                return loginUser;
            }
            //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        } catch (ExpiredJwtException e) {
            throw new CasException("登陆超时", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            throw new CasException("凭据错误", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new CasException("登陆失败", HttpStatus.UNAUTHORIZED);
        }
    }
}
