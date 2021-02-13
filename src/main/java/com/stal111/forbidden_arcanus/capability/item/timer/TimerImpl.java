package com.stal111.forbidden_arcanus.capability.item.timer;

/**
 * Timer Impl
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.capability.item.timer.TimerImpl
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-03
 */
public class TimerImpl implements ITimer {

    private int timer;

    @Override
    public int getTimer() {
        return timer;
    }

    @Override
    public void setTimer(int timer) {
        this.timer = timer;
    }

    @Override
    public void increaseTimer() {
        this.timer++;
    }

    @Override
    public void resetTimer() {
        this.timer = 0;
    }
}
