package com.bot.agentd.repository;

import com.bot.agentd.core.UserAgentD;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UserAgentDRepository extends JpaRepository<UserAgentD, String> {
    @Query(value = "SELECT * FROM user_agentd WHERE user_id = ?1", nativeQuery = true)
    UserAgentD findLineUserByUserId(String userID);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true "
            + "ELSE false END FROM user_agentd c WHERE c.user_id = ?1", nativeQuery = true)
    boolean isUserRegistered(String userID);

}

