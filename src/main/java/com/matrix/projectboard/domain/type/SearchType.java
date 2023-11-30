package com.matrix.projectboard.domain.type;

import lombok.Getter;

/**
 * author         : Jason Lee
 * date           : 2023-08-10
 * description    :
 */
public enum SearchType {
    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    @Getter
    private final String description;

    SearchType(String description) {
        this.description = description;
    }
}
