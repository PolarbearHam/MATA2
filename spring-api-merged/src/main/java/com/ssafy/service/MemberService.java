package com.ssafy.service;

import com.ssafy.util.JwtTokenProvider;
import com.ssafy.dto.MemberLoginDto;
import com.ssafy.dto.MemberSignUpDto;
import com.ssafy.dto.MemberDto;
import com.ssafy.util.NoSuchMemberException;
import com.ssafy.util.DuplicateMemberException;
import com.ssafy.entity.Member;
import com.ssafy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberSignUpDto request){
        isExistEmail(request.getEmail());
        String password = passwordEncoder.encode(request.getPassword());
        Member member = request.toEntity(password);
        memberRepository.save(member);
    }

    public MemberDto login(MemberLoginDto request){

        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(NoSuchMemberException::new);
        log.info(member.getEmail());

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        log.info("============ access authentication Token ==============");

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("============ access authentication ==============");

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        MemberDto tokenInfo = jwtTokenProvider.generateToken(authentication, member, true, "");
        log.info("============ access token ==============");

        // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
        stringRedisTemplate.opsForValue()
                .set("RT:" + member.getEmail(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
        return tokenInfo;
    }

    public void isExistEmail(String email){
        if(memberRepository.existsByEmail(email)) throw new DuplicateMemberException();
    }

    public void logout(String accessToken)
    {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        log.info(authentication.getName());
        if(stringRedisTemplate.opsForValue().get("RT:"+authentication.getName()) !=null){
            stringRedisTemplate.delete("RT:"+authentication.getName());
        }
        else log.info("refresh 없음");
    }

    public Member getMemberInfoByUserName(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(NoSuchMemberException::new);
        return member;
    }
}
