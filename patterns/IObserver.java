package com.yurt.patterns;

public interface IObserver {
    // Bildirim geldiğinde çalışacak metod
    void update(String mesaj);
}