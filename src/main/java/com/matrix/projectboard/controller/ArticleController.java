package com.matrix.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * author         : Jason Lee
 * date           : 2023-06-09
 * description    :
 */

@RequestMapping("/articles")
@Controller
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articleId}")

    public String article(@PathVariable Long articleId, ModelMap map) {
        map.addAttribute("article", "article"); // ToDo: 실제 데이터가 들어갈 수 있도록 구현 필요.
        map.addAttribute("articleComments", List.of());

        return "articles/detail";
    }

}
