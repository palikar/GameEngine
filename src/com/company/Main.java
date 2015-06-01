package com.company;

import com.company.Core.CoreEngine;
import java.util.Arrays;
import java.util.function.Function;

public class Main {

    static {
        System.load("E:\\GameEngine\\lwjgl.dll");
        System.load("E:\\GameEngine\\OpenAL32.dll");

    }

    public static void add() {

    }

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(800, 600, 60, new TestGame());
        engine.CreateWindow("2D game Engine");
        engine.Start();

    }
}
