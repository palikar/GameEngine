package com.company.Math;

/**
 * Created by Stanislav on 15.2.2015 г..
 */
public class Vector2f
{

    float x, y;

    public Vector2f(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2f()
    {
        this(0.0f, 0.0f);
    }

    public Vector2f(Vector2f vec)
    {
        this(vec.GetX(), vec.GetY());
    }

    public float Lenght()
    {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float Max()
    {
        return (float) Math.max(x, y);
    }

    public float Dot(Vector2f vec)
    {
        return x * vec.GetX() + y * vec.GetY();
    }

    public Vector2f Normalize()
    {
        float lenght = Lenght();
        return new Vector2f(x / lenght, y / lenght);
    }

    public Vector2f Lerp(Vector2f dest, float lerpFactor)
    {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    public Vector2f Slerp(Vector2f dest, float slerpFactor)
    {
        float dot = this.Dot(dest);
        dot = (float) MathUtil.Clamp(dot, 1d, -1d);
        float theta = (float) Math.acos(dot) * slerpFactor;
        Vector2f relative = new Vector2f(dest.Sub(this.Mul(dot)));
        relative.Normalize();
        return this.Mul((float) (Math.cos(theta))).Add(relative.Mul((float) Math.sin(theta)));
    }

    public Vector2f Nlerp(Vector2f dest, float slerpFactor)
    {
        return Lerp(dest, slerpFactor).Normalize();
    }

    public Vector2f Rotate(float angle)
    {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    public Vector2f Add(float r)
    {
        return new Vector2f(x + r, y + r);
    }

    public Vector2f Add(Vector2f vec)
    {
        return new Vector2f(x + vec.GetX(), y + vec.GetY());
    }

    public Vector2f Sub(float r)
    {
        return new Vector2f(x + -r, y - r);
    }

    public Vector2f Sub(Vector2f vec)
    {
        return new Vector2f(x - vec.GetX(), y - vec.GetY());
    }

    public Vector2f Mul(float r)
    {
        return new Vector2f(x * r, y * r);
    }

    public Vector2f Mul(Vector2f vec)
    {
        return new Vector2f(x * vec.GetX(), y * vec.GetY());
    }

    public Vector2f Div(float r)
    {
        return new Vector2f(x / r, y / r);
    }

    public Vector2f Div(Vector2f vec)
    {
        return new Vector2f(x / vec.GetX(), y / vec.GetY());
    }

    public void SetX(float x)
    {
        this.x = x;
    }

    public void SetY(float y)
    {
        this.y = y;
    }

    public float GetX()
    {
        return x;
    }

    public float GetY()
    {
        return y;
    }

    public Vector2f Set(float x, float y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f Set(Vector2f vec)
    {
        return Set(vec.GetX(), vec.GetY());
    }
}
