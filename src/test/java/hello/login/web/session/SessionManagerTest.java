package hello.login.web.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import hello.login.domain.member.Member;
import jakarta.servlet.http.HttpServletResponse;

class SessionManagerTest {

	SessionManager sessionManager = new SessionManager();
	
	@Test
	void sessionTest() {
		
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//세션 생성 (서버에서 클라이언트로)
		Member member = new Member();
		sessionManager.createSession(member, response);
		
		//요청에 응답 쿠키 저장 (클라이언트에서 서버로)
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(response.getCookies()); // mySessionId
		
		// 세션 조회
		Object result = sessionManager.getSession(request);
		Assertions.assertThat(result).isEqualTo(member);
		
		// 세션 만료
		sessionManager.expire(request);
		Object expired = sessionManager.getSession(request);
		Assertions.assertThat(expired).isNull();
	
	}
}
























