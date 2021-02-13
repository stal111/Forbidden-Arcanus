package com.stal111.forbidden_arcanus.capability.item.timer;

/**
 * Timer Interface
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.capability.item.timer.ITimer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public interface ITimer {
    int getTimer();
    void setTimer(int timer);
    void increaseTimer();
    void resetTimer();
}
