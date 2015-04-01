package com.company.Core;

import com.company.Rendering.RenderingEngine;
import com.company.Util.GlobalSpace;
import com.company.Util.Time;
import com.company.Rendering.Window;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class CoreEngine
{

    private int width, height;
    private double frameTime;
    private boolean isRunning;

    private Game game;
    private Window window;
    private Input input;

    public CoreEngine(int width, int height, int frameRate, Game game)
    {
        this.width = width;
        this.height = height;
        this.frameTime = 1d / (double) frameRate;
        this.game = game;

        isRunning = false;

        window = Window.getInstance();
        input = Input.getIntance();

    }

    public void CreateWindow(String title)
    {
        window.CreateWindow(width, height, title);
        Init();
    }

    private void Init()
    {
        input.Inint(window.getWindow());
        game.Init(this);
    }

    public void Start()
    {
        if (isRunning)
        {
            return;
        }

        isRunning = true;
        Run();
    }

    private void Run()
    {
        int frames = 0;
        double frameCounter = 0;

        double lastTime = Time.GetTime();
        double unprocessedTime = 0;

        while (isRunning)
        {

            boolean render = false;

            double startTime = Time.GetTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;
            unprocessedTime += passedTime;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime)
            {
                render = true;
                unprocessedTime -= frameTime;
                if (window.isCloseRequested())
                {
                    Stop();
                }
                game.Input(input);
                game.Update(frameTime);
                input.Update();

                if (frameCounter >= 1.0)
                {
                    if (GlobalSpace.DEBUG)
                    {
                        window.SetTitle("FPS:" + frames);
                    }
                    frames = 0;
                    frameCounter = 0;
                }
                if (render)
                {
                    game.Render();
                    window.Render();
                    frames++;
                } else
                {
                    try
                    {
                        Thread.sleep(1);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        CleanUp();

    }

    public void Stop()
    {
        if (!isRunning)
        {
            return;
        }
        isRunning = false;
    }

    public void CleanUp()
    {
        window.Dispose();
        input.Dispose();
    }

    public int GetWidth()
    {
        return width;
    }

    public int GetHeight()
    {
        return height;
    }

}
