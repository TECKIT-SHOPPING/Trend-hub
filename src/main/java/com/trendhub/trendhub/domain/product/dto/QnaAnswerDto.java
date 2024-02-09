package com.trendhub.trendhub.domain.product.dto;

import com.trendhub.trendhub.domain.product.entity.QnA;
import com.trendhub.trendhub.domain.product.entity.QnaAnswer;
import com.trendhub.trendhub.domain.user.entity.User;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnaAnswerDto {

    @Size(
            min = 20, max = 1000, message = "최소 20글자, 최대 1000글자 입력해주세요."
    )
    private String content;

    public QnaAnswer toEntity(QnA qnA, User user) {
        return QnaAnswer.builder()
                .content(this.getContent())
                .qnA(qnA)
                .createDate(LocalDateTime.now())
                /*.role(ROLE.ADMIN)*/ // 추후 실제 계정에 있는 관리자 권한 검사
                .name(user.getUsername())
                .loginId(user.getLoginId())
                .build();
    }
}
