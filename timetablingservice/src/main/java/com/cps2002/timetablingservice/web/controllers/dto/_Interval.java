package com.cps2002.timetablingservice.web.controllers.dto;

public class _Interval<T> {
    T start;
    T end;

    public _Interval(T start, T end) {
        this.start = start;
        this.end = end;
    }

    public T getStart() {
        return start;
    }

    public void setStart(T start) {
        this.start = start;
    }

    public T getEnd() {
        return end;
    }

    public void setEnd(T end) {
        this.end = end;
    }
}
