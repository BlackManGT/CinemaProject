package com.example.cinema.core;

import com.example.cinema.core.exception.ApiException;

public interface DataCall<T> {
    void success(T result);
    void fail(ApiException e);
}
