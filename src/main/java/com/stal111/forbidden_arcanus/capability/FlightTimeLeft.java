package com.stal111.forbidden_arcanus.capability;

public class FlightTimeLeft implements IFlightTimeLeft {

    private int flightTimeLeft = 0;

    @Override
    public int getFlightTimeLeft() {
        return flightTimeLeft;
    }

    @Override
    public void setFlightTimeLeft(int flightTimeLeft) {
        this.flightTimeLeft = flightTimeLeft;
    }
}
