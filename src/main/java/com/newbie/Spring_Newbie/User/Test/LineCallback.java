package com.newbie.Spring_Newbie.User.Test;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
