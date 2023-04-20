package com.ssafy.mata.service;

import com.ssafy.mata.exception.DuplicateMemberException;
import com.ssafy.mata.exception.NoSuchMemberException;
import com.ssafy.mata.dto.MemberLoginRequest;
import com.ssafy.mata.dto.MemberSignUpRequest;
import com.ssafy.mata.dto.MemberResponse;
import com.ssafy.mata.entity.Member;
import com.ssafy.mata.repository.MemberRepository;
import com.ssafy.mata.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate stringRedisTemplate;
    private final PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(MemberService.class);

    @Transactional
    public void signUp(MemberSignUpRequest request){
        isExistEmail(request.getEmail());
        String password = passwordEncoder.encode(request.getPassword());
        Member member = request.toEntity(password);
        memberRepository.save(member);
    }

    public MemberResponse login(MemberLoginRequest request){

        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(NoSuchMemberException::new);
        logger.info(member.getEmail());

        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        logger.info("============ access authentication Token ==============");

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        logger.info("============ access authentication ==============");

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        MemberResponse tokenInfo = jwtTokenProvider.generateToken(authentication, member, true, "");
        logger.info("============ access token ==============");

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
        logger.info(authentication.getName());
        if(stringRedisTemplate.opsForValue().get("RT:"+authentication.getName()) !=null){
            stringRedisTemplate.delete("RT:"+authentication.getName());
        }
        else logger.info("refresh 없음");
    }
}