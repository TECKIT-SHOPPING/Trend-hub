package com.trendhub.trendhub.domain.coordi.service;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.coordi.repository.CoordiRepository;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoordiService {

    private final CoordiRepository coordiRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public List<Coordi> getList() {
        return this.coordiRepository.findAll();
    }
    public void postCoordi(MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") {
            throw new IllegalStateException("로그인해주세요.");
        }
        String loginId = authentication.getName();
        //세션을 통한 유저 조회
        User findUser = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        //s3업로드
        String imageUrl = s3Service.createVideo(multipartFile);

        Coordi saveCoordi = Coordi.builder()
                .image(imageUrl)
                .user(findUser)
                .viewCount(0)
                .totalLike(0)
                .build();

        coordiRepository.save(saveCoordi);
    }

    public List<CoordiDto> findTop5ViewCountDesc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            String loginId = authentication.getName();
            //세션을 통한 유저 조회
            user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
        }

        List<CoordiDto> result = coordiRepository.findTop5ByOrderByViewCountDesc(user);
        return result;
    }

}
