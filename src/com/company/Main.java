package com.company;

import com.company.Core.CoreEngine;

public class Main {

    static {
        System.load("C:\\Users\\User\\Documents\\NetBeansProjects\\GameEngine\\lwjgl.dll");
        System.load("C:\\Users\\User\\Documents\\NetBeansProjects\\GameEngine\\OpenAL32.dll");

    }

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(800, 600, 60, new TestGame());
        engine.CreateWindow("2D game Engine");
        engine.Start();
    }
}
