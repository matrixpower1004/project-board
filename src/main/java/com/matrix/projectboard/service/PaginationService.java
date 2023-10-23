package com.matrix.projectboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * author         : Jisang Lee
 * date           : 2023-10-23
 * description    :
 */
@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;

    // pagination bar를 list 형태의 숫자로 내려 보내 준다.
    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages) {
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0); // 시작 페이지가 음수가 되지 않게
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages); // 종료 페이지가 총 페이지 수 보다 크지 않게

        return IntStream.range(startNumber, endNumber).boxed().toList(); // primitive type array를 Integer로 박싱 후 List로 변환
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}
