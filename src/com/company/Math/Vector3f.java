package com.company.Math;

/**
 * Created by Stanislav on 16.2.2015 г..
 */
public class Vector3f
{

    float x;
    float y;
    float z;

    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f()
    {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(Vector3f vec)
    {
        this(vec.GetX(), vec.GetY(), vec.GetZ());
    }

    public float Lenght()
    {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float Dot(Vector3f vec)
    {
        return x * vec.GetX() + y * vec.GetY() + z * vec.GetZ();
    }

    public float Max()
    {
        return (float) Math.max(x, Math.max(y, z));
    }

    public Vector3f Cross(Vector3f vec)
    {
        float x_ = y * vec.GetZ() - z * vec.GetY();
        float y_ = z * vec.GetX() - x * vec.GetZ();
        float z_ = x * vec.GetY() - y * vec.GetX();

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f Normalize()
    {
        float lenght = Lenght();
        return new Vector3f(x / lenght, y / lenght, z / lenght);
    }

    public Vector3f Rotate(Vector3f axis, float angle)
    {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);

        return this.Cross(axis.Mul(sinAngle)).Add((this.Mul(cosAngle)).Add(axis.Mul(this.Dot(axis.Mul(1 - cosAngle)))));
    }

    public Vector3f Lerp(Vector3f dest, float lerpFactor)
    {
        return dest.Sub(this).Mul(lerpFactor).Add(this);
    }

    public Vector3f Slerp(Vector3f dest, float slerpFactor)
    {
        float dot = this.Dot(dest);
        dot = (float) MathUtil.Clamp(dot, 1d, -1d);
        float theta = (float) Math.acos(dot) * slerpFactor;
        Vector3f relative = new Vector3f(dest.Sub(this.Mul(dot)));
        relative.Normalize();
        return this.Mul((float) (Math.cos(theta))).Add(relative.Mul((float) Math.sin(theta)));
    }

    public Vector3f Nlerp(Vector3f dest, float slerpFactor)
    {
        return Lerp(dest, slerpFactor);
    }

    public Vector3f Rotate(Quaternion rotation)
    {
        Quaternion conjugate = rotation.Conjugate();

        Quaternion w = rotation.Mul(this).Mul(conjugate);

        return new Vector3f(w.GetX(), w.GetY(), w.GetZ());
    }

    public Vector3f Add(float r)
    {
        return new Vector3f(x + r, y + r, z + r);
    }

    public Vector3f Add(Vector3f vec)
    {
        return new Vector3f(x + vec.GetX(), y + vec.GetY(), z + vec.GetZ());
    }

    public Vector3f Sub(float r)
    {
        return new Vector3f(x + -r, y - r, z - r);
    }

    public Vector3f Sub(Vector3f vec)
    {
        return new Vector3f(x - vec.GetX(), y - vec.GetY(), z - vec.GetZ());
    }

    public Vector3f Mul(float r)
    {
        return new Vector3f(x * r, y * r, z * r);
    }

    public Vector3f Mul(Vector3f vec)
    {
        return new Vector3f(x * vec.GetX(), y * vec.GetY(), z * vec.GetZ());
    }

    public Vector3f Div(float r)
    {
        return new Vector3f(x / r, y / r, z / r);
    }

    public Vector3f Div(Vector3f vec)
    {
        return new Vector3f(x / vec.GetX(), y / vec.GetY(), z / vec.GetZ());
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

    public void Set(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float GetZ()
    {
        return z;
    }

    public void SetZ(float z)
    {
        this.z = z;
    }

    public Vector3f Set(Vector3f position)
    {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
        return this;
    }

}
