package com.company;

import com.company.Animation2D.Keyframe;
import com.company.Animation2D.Timeline;
import com.company.Components.Components2D.PlaneTextureRenderer;
import com.company.Core.CoreEngine;
import com.company.Core.Game;
import com.company.Core.GameObject;
import com.company.Math.Vector2f;
import com.company.Rendering.Texture;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * Created by Stanislav on 22.2.2015 г..
 */
public class TestGame extends Game
{

    PlaneTextureRenderer ren;
    GameObject ground;
    Timeline<Double> anim;

    @Override

    public void Init(CoreEngine engine)
    {
        super.Init(engine);
        try
        {

            Texture tilesTexture = new Texture("tiles.bmp");
        
            ren = new PlaneTextureRenderer(32 * 50, 32 * 50, 0, GetRenderingEngine().GetSampler(tilesTexture));
            ren.SetTileSize(new Vector2f(1 / 10f, 1 / 10f));
            ren.SetOffSet(new Vector2f(2, 0));
            ren.SetTexCoordMult(new Vector2f(50, 50));
            ground = new GameObject();
            ground.AddComponent(ren);
            AddObject(ground);

            anim = new Timeline<Double>();

            anim.AddKeyframe(new Keyframe<Double>(1).PutKeyValue("value", 1.0d));
            anim.AddKeyframe(new Keyframe<Double>(1).PutKeyValue("value", 2.0d));
            anim.AddKeyframe(new Keyframe<Double>(1).PutKeyValue("value", 3.0d));

        } catch (URISyntaxException | IOException ex)
        {
            Logger.getLogger(TestGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void Render()
    {
        super.Render();

    }

    @Override
    protected void Input(com.company.Core.Input input)
    {
        super.Input(input);
    }

    @Override
    protected void Update(double delta)
    {
        super.Update(delta);
        anim.Update(delta);

    }

    @Override
    protected void InitCamera()
    {

        GetRenderingEngine().GetCamera().InitOrthographic(
                -GetEngine().GetWidth() / 7,
                GetEngine().GetWidth() / 7,
                -GetEngine().GetHeight() / 7,
                GetEngine().GetHeight() / 7,
                -1, 1);
        GetRenderingEngine().GetCamera().SetMoveSpeed(10f);
        GetRenderingEngine().GetCamera().SetScrollEnable(true);
        GetRenderingEngine().GetCamera().InitPrescpectiveProjection((float) Math.toRadians(90), 3f / 4f, 0.001f, 10000f);

    }

}
