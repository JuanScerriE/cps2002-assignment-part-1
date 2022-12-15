package com.cps2002.customermanagementservice.services.subscribers;

public interface Subscriber<T> {
    T notifyOfDelete(String uuid);
}
