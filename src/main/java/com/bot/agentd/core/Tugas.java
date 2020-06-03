package com.bot.agentd.core;

import java.time.LocalDateTime;

public interface Tugas {
    String getName();
    void setName(String name);
    String getDesc();
    void setDesc(String desc);
    LocalDateTime getDeadline();
    void setDeadline(LocalDateTime date);

}
