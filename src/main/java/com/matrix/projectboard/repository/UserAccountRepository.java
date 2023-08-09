package com.matrix.projectboard.repository;

import com.matrix.projectboard.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author         : Jason Lee
 * date           : 2023-08-09
 * description    :
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
}
