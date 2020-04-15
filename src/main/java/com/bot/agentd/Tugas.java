package com.bot.agentd;

import java.util.Date;

public interface Tugas {
    String getName();
    void setName(String name);
    String getDesc();
    void setDesc(String desc);
    Date getDeadline();
    void setDeadline(Date date);
}
