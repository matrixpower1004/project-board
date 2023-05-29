package com.matrix.projectboard.domain;

import java.time.LocalDateTime;

/**
 * author         : Jason Lee
 * date           : 2023-05-29
 * description    :
 */
public class ArticleComment {
    private Long id;
    private Article article; // 게시글 (ID)
    private String content; // 본문

    private LocalDateTime createdAt; // 생성일시
    private String createBy; // 생성자
    private LocalDateTime modifiedAt; // 수정일시
    private String modifiedBy; // 수정자
