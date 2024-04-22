package hello.login.web.session;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *  세션 관리
 */

@Component
public class SessionManager {

	private static final String SESSION_COOKIE_NAME = "mySessionId";
	private Map<String, Object> sessionStore = new ConcurrentHashMap<>();
	
	public void createSession(Object value, HttpServletResponse response) {
		
		// 세션 id를 생성하고, 값을 세션에 저장
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, value);
		
		// 쿠키 생성
		Cookie mySeesionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
		response.addCookie(mySeesionCookie);
	}
	
	// 세션 조회
	public Object getSession(HttpServletRequest request) {
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if (sessionCookie == null) {
			return null;
		}
		return sessionStore.get(sessionCookie.getValue());
	}
	
	public Cookie findCookie(HttpServletRequest request, String cookieName) {
		if(request.getCookies() == null) {
			return null;
		}
		return Arrays.stream(request.getCookies())
			.filter(cookie -> cookie.getName().equals(cookieName))
			.findAny()
			.orElse(null);
	}
	
	// 세션 만료
	public void expire(HttpServletRequest request) {
		Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			sessionStore.remove(sessionCookie.getValue());
		}
	}
		
		
}



















