package me.firstSpring.util;

import jakarta.servlet.http.Cookie; // 웹 애플리케이션에서 쿠키를 생성하고 조작할 수 있게 해줌
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

//쿠키 관리 클래스
public class CookieUtil {
    //요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    //쿠키의 이름을 입력받아 쿠키 삭제
    //쿠키를 실제로 삭제하는 방법은 없으므로 빈값으로 바꾼 뒤 만료시간을 0으로 설정해 재생성과 동시에 만료처리함
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,String name){
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return;
        }
        for (Cookie cookie : cookies){
            if(name.equals(cookie.getName())){
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    //객체를 직렬화해 쿠키의 값으로 변환(인코딩)
    public static String serialize(Object obj){
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    //쿠키를 역직렬화해 객체로 변환(디코딩)
    public static <T> T deserialize(Cookie cookie, Class<T> cls){
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }
}
