package com.bot.agentd.repository;

import com.bot.agentd.core.TugasIndividu;
import com.bot.agentd.core.TugasKelompok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface TugasKelompokRepository extends JpaRepository<TugasKelompok, Long> {
    @Query(value = "SELECT s FROM TugasKelompok s WHERE s.ownerId = :ownerId")
    List<TugasKelompok> fetchTugasKelompok(@Param("ownerId") String ownerId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM TugasKelompok s WHERE s.id = :id")
    void deleteTugasKelompok(@Param("id") Long id);
}
