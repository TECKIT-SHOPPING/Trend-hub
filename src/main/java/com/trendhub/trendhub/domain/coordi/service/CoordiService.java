package com.trendhub.trendhub.domain.coordi.service;

import com.trendhub.trendhub.domain.coordi.dto.CoordiDetailDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiDto;
import com.trendhub.trendhub.domain.coordi.dto.CoordiLikeDto;
import com.trendhub.trendhub.domain.coordi.entity.Coordi;
import com.trendhub.trendhub.domain.coordi.repository.CoordiRepository;
import com.trendhub.trendhub.domain.likes.entity.Likes;
import com.trendhub.trendhub.domain.likes.service.LikesService;
import com.trendhub.trendhub.domain.user.entity.User;
import com.trendhub.trendhub.domain.user.repository.UserRepository;
import com.trendhub.trendhub.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CoordiService {

    private final CoordiRepository coordiRepository;
    private final LikesService likesService;
    private final UserRepository userRepository;
    private final S3Service s3Service;

    public List<Coordi> getList() {
        return this.coordiRepository.findAll();
    }
    public Long postCoordi(MultipartFile multipartFile) {
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

        return saveCoordi.getCoordiId();
    }

    public List<CoordiDto> findTop5ViewCountDesc() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            String loginId = authentication.getName();
            //세션을 통한 유저 조회
            user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));
            List<CoordiDto> result = coordiRepository.findTop5ByOrderByViewCountDesc(user);
            return result;
        } else {
            List<CoordiDto> result = coordiRepository.findTop5ByOrderByViewCountDescAnonymousUser();
            return result;
        }

    }

    public boolean toggleLikeCoordi(CoordiLikeDto coordiLikeDto) {
        String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
        Coordi coordi = coordiRepository.findById(coordiLikeDto.getCoordiId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 코디입니다."));
        User user = userRepository.findByLoginId(loginId).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저입니다."));

        Optional<Likes> _findLikes = likesService.findByCoordiAndUser(coordi, user);

        if (_findLikes.isEmpty()) {
            //좋아요를 누른적 없다면 likes 생성후, 좋아요 처리
            Likes likes = coordiLikeDto.toEntity(user, coordi);
            likesService.createLikes(likes);
            coordi.likeCoordi(likes);
            return true;
        } else {
            //좋아요 누른적 있다면 취소 처리 후 데이터 삭제
            Likes findLikes = _findLikes.get();
            coordi.unLikeCoordi(findLikes);
            likesService.deleteLikes(findLikes);
            return false;
        }
    }

    public Page<CoordiDto> getCoordiPage(Pageable pageable, int page) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            String loginId = authentication.getName();
            user = userRepository.findByLoginId(loginId).get();
        }

//         정렬 기준을 동적으로 변경할 수 있도록 Sort를 추가
        List<Sort.Order> sort = new ArrayList<>();
        sort.add(Sort.Order.desc("createDate"));
        pageable = PageRequest.of(page - 1, 20, Sort.by(sort));

        return coordiRepository.coordiPage(user, pageable);
    }

    public CoordiDetailDto findById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            String loginId = authentication.getName();
            user = userRepository.findByLoginId(loginId).get();
        }
        CoordiDetailDto coordiDetailDto = coordiRepository.findCoordiById(user, id);
        

        return coordiDetailDto;
    }

}
