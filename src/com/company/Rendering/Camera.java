/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Rendering;

import com.company.Core.GameObject;
import com.company.Core.Input;
import com.company.Math.Matrix4f;
import com.company.Math.Vector2f;
import com.company.Math.Vector3f;
import org.lwjgl.glfw.GLFW;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class Camera extends GameObject
{

    public static final int PLANE_MODE = 0;
    public static final int SPACE_MODE = 1;

    private Matrix4f projectionMatrix;
    private int mode = 1;

    private Vector2f center = null;
    private boolean mouseLock = false;

    private float moveSpeed = 5.010f;
    private float rotSpeed = 0.05f;
    private float scrollSpeedMul = 25.0f;

    private float left, right, bottom, top, near, far;
    private boolean scrollEnable = false;

    public Camera()
    {

    }

    public void InitPrescpectiveProjection(float fov, float aspectRatio, float zNear, float zFar)
    {
        projectionMatrix = new Matrix4f().InitPerspective(fov, aspectRatio, zNear, zFar);
        mode = SPACE_MODE;
    }

    public void InitOrthographic(float left, float right, float bottom, float top, float near, float far)
    {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
        this.near = near;
        this.far = far;
        projectionMatrix = new Matrix4f().InitOrthographic(left, right, bottom, top, near, far);
        mode = PLANE_MODE;
    }

    private void SetMode(int mode)
    {
        this.mode = mode;
    }

    public Matrix4f GetViewProjection()
    {
        if (projectionMatrix == null)
        {
            throw new RuntimeException("The main camera is not Initialized");
        }
        Matrix4f rotation = GetTransform().GetRotation().Conjugate().ToRotationMatrix();
        Vector3f position = GetTransform().GetPosition().Mul(-1f);
        Matrix4f translation = new Matrix4f().InitTranslation(position.GetX(), position.GetY(), position.GetZ());

        return projectionMatrix.Mul(rotation.Mul(translation));
    }

    public void Move(Vector3f dir, float amt)
    {
        GetTransform().SetPosition(GetTransform().GetPosition().Add(dir.Mul(amt)));
    }

    public void Rotate(Vector3f axis, float amt)
    {
        GetTransform().Rotate(axis, amt);
    }

    @Override
    public void Input(com.company.Core.Input input)
    {
        super.Input(input);

        if (mode == PLANE_MODE)
        {
            MoveInPlane(input);
        } else if (mode == SPACE_MODE)
        {
            MoveInSpace(input);
        }

    }

    private void MoveInPlane(Input input)
    {
        if (input.IsKeyPressed(GLFW.GLFW_KEY_W))
        {

            Move(GetTransform().GetRotation().GetUp(), moveSpeed);

        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_S))
        {
            Move(GetTransform().GetRotation().GetDown(), moveSpeed);

        }

        if (input.IsKeyPressed(GLFW.GLFW_KEY_A))
        {
            Move(GetTransform().GetRotation().GetLeft(), moveSpeed);
        }

        if (input.IsKeyPressed(GLFW.GLFW_KEY_D))
        {
            Move(GetTransform().GetRotation().GetRight(), moveSpeed);

        }
        if (scrollEnable)
        {
            float scrollDelta = input.getLastScrollDirection() - input.getScrollPos();
            scrollDelta *= scrollSpeedMul;

            projectionMatrix = new Matrix4f().InitOrthographic(left - scrollDelta, right + scrollDelta, bottom - scrollDelta, top + scrollDelta, near, far);

        }
    }

    private void MoveInSpace(Input input)
    {
        if (center == null)
        {
            center = new Vector2f(GetGame().GetEngine().GetWidth() / 2, GetGame().GetEngine().GetHeight() / 2);

        }

        if (input.IsKeyPressed(GLFW.GLFW_KEY_W))
        {

            Move(GetTransform().GetRotation().GetForward(), moveSpeed);

        }
        if (input.IsKeyPressed(GLFW.GLFW_KEY_S))
        {
            Move(GetTransform().GetRotation().GetBack(), moveSpeed);

        }

        if (input.IsKeyPressed(GLFW.GLFW_KEY_A))
        {
            Move(GetTransform().GetRotation().GetLeft(), moveSpeed);
        }

        if (input.IsKeyPressed(GLFW.GLFW_KEY_D))
        {
            Move(GetTransform().GetRotation().GetRight(), moveSpeed);

        }

        if (input.isMouseButtonPressed(GLFW.GLFW_MOUSE_BUTTON_1) && !mouseLock)
        {

            mouseLock = true;
            input.HideCursor();
            input.SetMousePos(center);
            return;
        }

        if (input.isMouseButtonRealsed(GLFW.GLFW_MOUSE_BUTTON_1))
        {
            mouseLock = false;
            input.ShowCursor();
        }

        if (mouseLock)
        {
            Vector2f deltaPos = input.getMousePos().Sub(input.getLastMousePos());

            boolean rotX = deltaPos.GetX() != input.getLastMousePos().GetX();
            boolean rotY = deltaPos.GetY() != input.getLastMousePos().GetY();

            if (rotY)
            {
                GetTransform().Rotate(new Vector3f(0, 1, 0),
                        (float) Math.toRadians(deltaPos.GetX() * rotSpeed));
            }
            if (rotX)
            {
                GetTransform().Rotate(GetTransform().GetRotation().GetLeft(),
                        (float) Math.toRadians(-deltaPos.GetY() * rotSpeed));
            }

            if ((rotY || rotX))
            {
                input.SetMousePos(center);
            }
        }
    }

    public float GetMoveSpeed()
    {
        return moveSpeed;
    }

    public float GetRotSpeed()
    {
        return rotSpeed;
    }

    public float GetScrollSpeedMul()
    {
        return scrollSpeedMul;
    }

    public void SetMoveSpeed(float moveSpeed)
    {
        this.moveSpeed = moveSpeed;
    }

    public void SetRotSpeed(float rotSpeed)
    {
        this.rotSpeed = rotSpeed;
    }

    public void SetScrollSpeedMul(float scrollSpeedMul)
    {
        this.scrollSpeedMul = scrollSpeedMul;
    }

    public void SetScrollEnable(boolean value)
    {
        scrollEnable = value;
    }

}
