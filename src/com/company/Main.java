package com.company;


import com.company.Core.CoreEngine;

public class Main {

    public static void main(String[] args) {
       CoreEngine engine = new CoreEngine(800, 600, 60, new TestGame());
        engine.CreateWindow("2D game Engine");
        engine.Start();
    }
}
