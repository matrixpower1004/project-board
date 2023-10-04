package com.matrix.projectboard.dto.response;

import com.matrix.projectboard.dto.ArticleCommentDto;

import java.time.LocalDateTime;

/**
 * author         : Jason Lee
 * date           : 2023-08-14
 * description    :
 */
public record ArticleCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname
) {

    public static ArticleCommentResponse of(
            Long id,
            String content,
            LocalDateTime createdAt,
            String email,
            String nickname) {
        return new ArticleCommentResponse(id, content, createdAt, email, nickname);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {
        String nickname = dto.userAccountDto().nickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = dto.userAccountDto().userId();
        }

        return new ArticleCommentResponse(
                dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userAccountDto().email(),
                nickname
        );
    }

}