package com.example.windowPos.global.customUserDetailService;

//@Service
//@Transactional
//@RequiredArgsConstructor
////  시큐리티를 이용해 로그인한 사용자 정보를 얻고, 해당 사용자의 설정을 불러오거나 저장하는 구문
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Member member = memberRepository.findByUsername(username).orElse(null);
//
//        return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(), new ArrayList<>());
//    }
//}
