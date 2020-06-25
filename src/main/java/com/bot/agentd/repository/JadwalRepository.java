package com.bot.agentd.repository;

import com.bot.agentd.core.Jadwal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JadwalRepository extends JpaRepository<Jadwal, Long> {
    @Query(value = "SELECT s FROM Jadwal s WHERE s.ownerId = :ownerId")
    List<Jadwal> fetchJadwal(@Param("ownerId") String ownerId);

    @Query(value = "SELECT s FROM Jadwal s WHERE s.ownerId = :ownerId AND s.day = :day")
    List<Jadwal> fetchJadwalBasedOnDay(@Param("ownerId") String ownerId, @Param("day") String day);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Jadwal s WHERE s.id = :id")
    void deleteJadwal(@Param("id") Long id);
}
