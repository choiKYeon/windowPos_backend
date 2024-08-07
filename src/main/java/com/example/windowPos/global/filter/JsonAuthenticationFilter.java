package com.example.windowPos.global.filter;

/*
스프링 시큐리티의 인증 필터를 구현
UsernamePasswordAuthenticationFilter는 AbstractAuthenticationProcessingFilter의 구현체
새로운 필터를 만들기위해 AbstractAuthenticationProcessingFilter를 상속받는 새로운 Filter 구현체를 만듦
해당 필터는 JSON 요청을 받아 미인증된 UsernamePasswordAuthenticationToken 를 반환
*/
//public class JsonAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
//    private ObjectMapper objectMapper;
//
//    public JsonAuthenticationFilter(ObjectMapper objectMapper) {
//        super(new AntPathRequestMatcher("/api/v1/member/login", "POST")); // 해당 경로 요청을 처리하기 위한 설정
//        this.objectMapper = objectMapper;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        if(request.getContentType() == null || !request.getContentType().equals("application/json")  ) {
//            throw new AuthenticationServiceException("잘못된 요청 형식입니다. = " + request.getContentType());
//        }
//        Map<String, String> loginForm = objectMapper.readValue(request.getInputStream(), Map.class);
//
//        String username = loginForm.get("username");
//        String password = loginForm.get("password");
//
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//
//
//        // 요청 받은 값으로 객체를 생성해 실제 인증 절차를 진행.
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
//}
