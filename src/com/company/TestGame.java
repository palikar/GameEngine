package com.company;

import com.company.Animation.Timeline;
import com.company.Components.Components2D.PlaneTextureRenderer;
import com.company.Components.Components2D.SpriteAnimator;
import com.company.Core.CoreEngine;
import com.company.Core.Game;
import com.company.Core.GameObject;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import com.company.Rendering.GuiTexture;
import com.company.Rendering.Texture;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Stanislav on 22.2.2015 г..
 */
public class TestGame extends Game {

    GameObject ground;
    GameObject person;
    Timeline<Double> anim;
    boolean freeLook;
    SpriteAnimator sprite;

    @Override

    public void Init(CoreEngine engine) {
        super.Init(engine);
        try {

            Texture tilesTexture = new Texture("tiles.png");
            Texture potion = new Texture("pixelArt/knightSheet.png");

            PlaneTextureRenderer ren = new PlaneTextureRenderer(GetRenderingEngine().GetSampler(tilesTexture));
            ren.SetTileSize(new Vector2f(1 / 10f, 1 / 10f));
            ren.SetOffSet(new Vector2f(0, 0));
            ren.SetTexCoordMult(new Vector2f(25, 25));
            ground = new GameObject();
            ground.AddComponent(ren);
            ground.GetTransform().SetScale(new Vector3f(32 * 10, 32 * 10, 1));
            AddObject(ground);

            sprite = new SpriteAnimator(GetRenderingEngine().GetSampler(potion), new Vector2f(0, 0));
            sprite.SetTileSize(new Vector2f(1f / 3f, 1));
            sprite.SetOffSet(new Vector2f(0, 0));
            ArrayList<Vector2f> keys = new ArrayList<>();
            keys.add(new Vector2f(1, 0));
            keys.add(new Vector2f(0, 0));
            keys.add(new Vector2f(2, 0));
            keys.add(new Vector2f(0, 0));

            sprite.AddAnimation("walk", keys, 0.25f, true);

            person = new GameObject();
            person.AddComponent(sprite);
            person.GetTransform().GetPosition().SetZ(0.1f);
            person.GetTransform().SetScale(new Vector3f(64, 64, 1));

            AddObject(person);

            // EnableGui();
            //GetGui().AddTexture(new GuiTexture(potion, new Vector2f(), new Vector2f(32, 32).Div(GetSize())));
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(TestGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void Render() {
        super.Render();

    }

    @Override
    protected void Input(com.company.Core.Input input) {
        super.Input(input);
        if (input.isKeyRealsed(GLFW.GLFW_KEY_S)) {
            sprite.GetAnimator().Stop();
        }
        if (input.IsKeyClicked(GLFW.GLFW_KEY_S)) {
            sprite.GetAnimator().Play("walk");
        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_S)) {
            person.GetTransform().SetPosition(person.GetTransform().GetPosition().Add(new Vector3f(0, -1, 0)));
        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_W)) {
            person.GetTransform().SetPosition(person.GetTransform().GetPosition().Add(new Vector3f(0, 1, 0)));
        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_D)) {
            person.GetTransform().SetPosition(person.GetTransform().GetPosition().Add(new Vector3f(1, 0, 0)));
        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_A)) {
            person.GetTransform().SetPosition(person.GetTransform().GetPosition().Add(new Vector3f(-1, 0, 0)));
        }

    }

    @Override
    protected void Update(double delta) {
        super.Update(delta);
        GetRenderingEngine().GetCamera().GetTransform().SetPosition(
                person.GetTransform().GetPosition().Add(new Vector3f(0 , 0, 0)));
    }

    @Override
    protected void InitCamera() {

        GetRenderingEngine().GetCamera().InitOrthographic(
                -GetEngine().GetWidth() / 7,
                GetEngine().GetWidth() / 7,
                -GetEngine().GetHeight() / 7,
                GetEngine().GetHeight() / 7,
                -1, 1);
        GetRenderingEngine().GetCamera().SetMoveSpeed(0f);
        // GetRenderingEngine().GetCamera().SetScrollEnable(true);
        // GetRenderingEngine().GetCamera().InitPrescpectiveProjection((float) Math.toRadians(90), 3f / 4f, 0.001f, 10000f);

    }

}
