package com.sereon.po.security.service;

import com.sereon.po.entity.User;
import com.sereon.po.entity.UserRole;
import com.sereon.po.repository.UserRepository;
import com.sereon.po.security.dto.PoAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PoOAuth2UserDetailsService extends DefaultOAuth2UserService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthorizationException{

        log.info("----------------------------------");
        log.info("userRequest : "+userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser((userRequest));

        oAuth2User.getAttributes().forEach((k,v) -> {
            log.info(k +":"+ v);
        });

        String email = null;

        if(clientName.equals("Google")){
            email = oAuth2User.getAttribute("email");
        }
        log.info("Email : "+email);

        //User user = saveSocialMember(email);

        //return oAuth2User;
        User user = saveSocialMember(email);

        PoAuthMemberDTO poAuthMember = new PoAuthMemberDTO(user.getEmail(), user.getPassword(),true
                                        ,user.getRoleSet().stream().map(role->new SimpleGrantedAuthority("ROLE_"+role.name())).collect(Collectors.toList()),oAuth2User.getAttributes());

        poAuthMember.setName((user.getName()));

        return poAuthMember;
    }


    private User saveSocialMember(String email){
        Optional<User> result = userRepository.findByEmail(email, true);

        if(result.isPresent()){
            return result.get();
        }

        User user = User.builder().email(email)
                .name(email)
                .password(passwordEncoder.encode("password"))
                .fromSocial(true)
                .build();


        user.addMemberRole(UserRole.USER);

        userRepository.save(user);

        return user;

    }

}
