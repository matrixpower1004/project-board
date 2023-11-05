package com.matrix.projectboard.service;

import com.matrix.projectboard.domain.Article;
import com.matrix.projectboard.domain.type.SearchType;
import com.matrix.projectboard.dto.ArticleDto;
import com.matrix.projectboard.dto.ArticleWithCommentsDto;
import com.matrix.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * author         : Jason Lee
 * date           : 2023-08-10
 * description    :
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) { // search keyword가 없을 때
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from);
            // ToDo: hashtag에 #을 넣어서 검색하면 #이 두번 들어가게 된다. 나중에 refactoring 해보자.
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException(("게시글이 없습니다 - articleId: " + articleId)));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        // findById() 메서드는 Optional로 반환되기 때문에 예외 처리에 유리하지만,
        // getReferenceById() 는 프록시 객체를 가져오기 때문에 예외 발생 위험이 있다. null 방어 로직이 필요하다.
        try {
            Article article = articleRepository.getReferenceById(dto.id());

            if (dto.title() != null) {
                article.setTitle(dto.title());
            }
            if (dto.content() != null) {
                article.setContent(dto.content());
            }
            article.setHashtag(dto.hashtag()); // null 허용 필드라 dto에서 null이 리턴되어도 관계 없다.

            // 클래스 레벨 트랜잭션에 의해서 메서드 단위로 트랜잭션이 묶어 있다.
            // 그래서 트랜잭션이 종료될 때 영속성 컨텍스트는 Article 변한 것을 감지해 내고 update 쿼리를 날린다.
            // 따라서 save() 메서드는 생략 가능하다.
//        articleRepository.save(dto.toEntity());
        } catch (EntityNotFoundException e) {
            log.warn("디버그 : 게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto); // string interpolation
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}
