package com.bot.agentd.repository;

import com.bot.agentd.core.TugasIndividu;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TugasIndividuRepository extends JpaRepository<TugasIndividu, Long> {
    @Query(value = "SELECT s FROM TugasIndividu s WHERE s.ownerId = :ownerId")
    List<TugasIndividu> fetchTugasIndividu(@Param("ownerId") String ownerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TugasIndividu s WHERE s.id = :id")
    void deleteTugasIndividu(@Param("id") Long id);

}
