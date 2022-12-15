package com.cps2002.resourcemanagementservice.services.subscribers;

public interface Subscriber<T> {
    T notifyOfDelete(String uuid);
}
