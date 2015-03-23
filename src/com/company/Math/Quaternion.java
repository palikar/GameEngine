package com.company.Math;

/**
 * Created by Stanislav on 2.3.2015 г..
 */
public class Quaternion
{

    private float x, y, z, w;

    public Quaternion()
    {
        this(0.0f, 0.0f, 0.0f, 1.0f);
    }

    public Quaternion(float x, float y, float z, float w)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3f axis, float angle)
    {
        float sinOfAngle = (float) Math.sin(angle / 2);
        float cosOfAngle = (float) Math.cos(angle / 2);

        this.x = axis.GetX() * sinOfAngle;
        this.y = axis.GetY() * sinOfAngle;
        this.z = axis.GetZ() * sinOfAngle;
        this.w = cosOfAngle;
    }

    public float Lenght()
    {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion Normalize()
    {
        float lenght = Lenght();
        return new Quaternion(x / lenght, y / lenght, z / lenght, w / lenght);
    }

    public Quaternion Conjugate()
    {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion Mul(float r)
    {
        return new Quaternion(x * r, y * r, z * r, w * r);
    }

    public Quaternion Mul(Quaternion r)
    {
        float w_ = w * r.GetW() - x * r.GetX() - y * r.GetY() - z * r.GetZ();
        float x_ = x * r.GetW() + w * r.GetX() + y * r.GetZ() - z * r.GetY();
        float y_ = y * r.GetW() + w * r.GetY() + z * r.GetX() - x * r.GetZ();
        float z_ = z * r.GetW() + w * r.GetZ() + x * r.GetY() - y * r.GetX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion Mul(Vector3f r)
    {
        float w_ = -x * r.GetX() - y * r.GetY() - z * r.GetZ();
        float x_ = w * r.GetX() + y * r.GetZ() - z * r.GetY();
        float y_ = w * r.GetY() + z * r.GetX() - x * r.GetZ();
        float z_ = w * r.GetZ() + x * r.GetY() - y * r.GetX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion Sub(Quaternion r)
    {
        return new Quaternion(x - r.GetX(), y - r.GetY(), z - r.GetZ(), w - r.GetW());
    }

    public Quaternion Add(Quaternion r)
    {
        return new Quaternion(x + r.GetX(), y + r.GetY(), z + r.GetZ(), w + r.GetW());
    }

    public Matrix4f ToRotationMatrix()
    {
        Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
        Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
        Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

        return new Matrix4f().InitRotation(forward, up, right);
    }

    public float Dot(Quaternion r)
    {
        return x * r.GetX() + y * r.GetY() + z * r.GetZ() + w * r.GetW();
    }

    public Quaternion NLerp(Quaternion dest, float lerpFactor, boolean shortest)
    {
        Quaternion correctedDest = dest;

        if (shortest && this.Dot(dest) < 0)
        {
            correctedDest = new Quaternion(-dest.GetX(), -dest.GetY(), -dest.GetZ(), -dest.GetW());
        }

        return correctedDest.Sub(this).Mul(lerpFactor).Add(this).Normalize();
    }

    public Quaternion(Matrix4f rot)
    {
        float trace = rot.Get(0, 0) + rot.Get(1, 1) + rot.Get(2, 2);

        if (trace > 0)
        {
            float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
            w = 0.25f / s;
            x = (rot.Get(1, 2) - rot.Get(2, 1)) * s;
            y = (rot.Get(2, 0) - rot.Get(0, 2)) * s;
            z = (rot.Get(0, 1) - rot.Get(1, 0)) * s;
        } else
        {
            if (rot.Get(0, 0) > rot.Get(1, 1) && rot.Get(0, 0) > rot.Get(2, 2))
            {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.Get(0, 0) - rot.Get(1, 1) - rot.Get(2, 2));
                w = (rot.Get(1, 2) - rot.Get(2, 1)) / s;
                x = 0.25f * s;
                y = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
                z = (rot.Get(2, 0) + rot.Get(0, 2)) / s;
            } else if (rot.Get(1, 1) > rot.Get(2, 2))
            {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.Get(1, 1) - rot.Get(0, 0) - rot.Get(2, 2));
                w = (rot.Get(2, 0) - rot.Get(0, 2)) / s;
                x = (rot.Get(1, 0) + rot.Get(0, 1)) / s;
                y = 0.25f * s;
                z = (rot.Get(2, 1) + rot.Get(1, 2)) / s;
            } else
            {
                float s = 2.0f * (float) Math.sqrt(1.0f + rot.Get(2, 2) - rot.Get(0, 0) - rot.Get(1, 1));
                w = (rot.Get(0, 1) - rot.Get(1, 0)) / s;
                x = (rot.Get(2, 0) + rot.Get(0, 2)) / s;
                y = (rot.Get(1, 2) + rot.Get(2, 1)) / s;
                z = 0.25f * s;
            }
        }

        float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
        x /= length;
        y /= length;
        z /= length;
        w /= length;
    }

    public Vector3f GetForward()
    {
        return new Vector3f(0, 0, 1).Rotate(this);
    }

    public Vector3f GetBack()
    {
        return new Vector3f(0, 0, -1).Rotate(this);
    }

    public Vector3f GetUp()
    {
        return new Vector3f(0, 1, 0).Rotate(this);
    }

    public Vector3f GetDown()
    {
        return new Vector3f(0, -1, 0).Rotate(this);
    }

    public Vector3f GetRight()
    {
        return new Vector3f(1, 0, 0).Rotate(this);
    }

    public Vector3f GetLeft()
    {
        return new Vector3f(-1, 0, 0).Rotate(this);
    }

    public float GetX()
    {
        return x;
    }

    public float GetY()
    {
        return y;
    }

    public float GetZ()
    {
        return z;
    }

    public float GetW()
    {
        return w;
    }

    public void SetX(float value)
    {
        this.x = value;
    }

    public void SetY(float value)
    {
        this.y = value;
    }

    public void SetZ(float value)
    {
        this.z = value;
    }

    public void SetW(float value)
    {
        this.w = value;
    }

    Quaternion Set(Quaternion rotation)
    {
        this.x = rotation.x;
        this.y = rotation.y;
        this.z = rotation.z;
        this.w = rotation.w;
        return this;
    }

}
