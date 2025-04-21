# 🛡️ OAuth2 + OIDC 인증 서버 (Spring Authorization Server)

Spring Security + Spring Authorization Server 기반의 OAuth2 인증 서버입니다.  
OpenID Connect (OIDC)도 함께 지원하며, `/userinfo` 엔드포인트를 통해 사용자 정보를 제공합니다.

---

## 📦 프로젝트 구조


---

## 🚀 실행 방법

1. **포트**
   - 인증 서버: `http://localhost:9000`
   - 클라이언트 앱: `http://localhost:9001`

2. **기본 유저**
   - 아이디: `test`
   - 비밀번호: `123123`

3. **등록된 클라이언트**
   - `clientId`: `client`
   - `clientSecret`: `secret`
   - `redirectUri`: `http://localhost:9001/login/oauth2/code/client-oidc`
   - 인증 방식: `authorization_code + refresh_token`

---

## 🔐 인증 플로우

```text
[클라이언트] → 인증 요청 (9000 포트)
       ↓
[서버] → 로그인 (Form Login)
       ↓
[서버] → Authorization Code 발급 → redirectUri로 리디렉션
       ↓
[클라이언트] → 토큰 요청
       ↓
[서버] → Access Token / ID Token 발급
       ↓
[클라이언트] → /userinfo 요청 (Access Token 포함)
       ↓
[서버] → 사용자 정보 반환