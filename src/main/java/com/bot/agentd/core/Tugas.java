package com.bot.agentd.core;

import java.time.LocalDate;

public interface Tugas {
    String getName();
    void setName(String name);
    String getDesc();
    void setDesc(String desc);
    LocalDate getDeadline();
    void setDeadline(LocalDate date);

}
