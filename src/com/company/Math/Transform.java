package com.company.Math;

/**
 * Created by Stanislav on 2.3.2015 г..
 */
public class Transform
{

    private Transform parent;
    private Matrix4f parentMatrix;

    private Vector3f position;
    private Quaternion rotation;
    private Vector3f scale;

    private Vector3f oldPositon;
    private Quaternion oldRotation;
    private Vector3f oldScale;

    public Transform()
    {
        position = new Vector3f();
        rotation = new Quaternion();
        scale = new Vector3f(1f, 1f, 1f);

        parentMatrix = new Matrix4f().InitIdentity();
    }

    public void Update()
    {
        if (oldPositon != null)
        {
            oldPositon.Set(position);
            oldRotation.Set(rotation);
            oldScale.Set(scale);

        } else
        {
            oldPositon = new Vector3f(0, 0, 0).Set(position).Add(1.0f);
            oldRotation = new Quaternion(0, 0, 0, 0).Set(rotation).Mul(0.5f);
            oldScale = new Vector3f(0, 0, 0).Set(scale).Add(1.0f);
        }
    }

    public boolean HasChanged()
    {
        if (parent != null && parent.HasChanged())
        {
            return true;
        }

        if (!position.equals(oldPositon))
        {
            return true;
        }

        if (!rotation.equals(oldRotation))
        {
            return true;
        }

        if (!scale.equals(oldScale))
        {
            return true;
        }

        return false;
    }

    public void Rotate(Vector3f axis, float angle)
    {
        rotation = new Quaternion(axis, angle).Mul(rotation).Normalize();
    }

    public void LookAt(Vector3f point, Vector3f up)
    {
        rotation = GetLookAtRotation(point, up);
    }

    public Matrix4f GetTransformation()
    {
        Matrix4f translationMatrix = new Matrix4f().InitTranslation(position.GetX(), position.GetY(), position.GetZ());
        Matrix4f rotationMatrix = rotation.ToRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().InitScale(scale.GetX(), scale.GetY(), scale.GetZ());

        return GetParentMatrix().Mul(translationMatrix.Mul(rotationMatrix.Mul(scaleMatrix)));
    }

    private Matrix4f GetParentMatrix()
    {
        if (parent != null && parent.HasChanged())
        {
            parentMatrix = parent.GetTransformation();
        }

        return parentMatrix;
    }

    public void SetParent(Transform parent)
    {
        this.parent = parent;
    }

    public Vector3f GetTransformedPos()
    {
        return GetParentMatrix().Transform(position);
    }

    public Quaternion GetTransformedRot()
    {
        Quaternion parentRotation = new Quaternion(0, 0, 0, 1);

        if (parent != null)
        {
            parentRotation = parent.GetTransformedRot();
        }

        return parentRotation.Mul(rotation);
    }

    public Quaternion GetLookAtRotation(Vector3f point, Vector3f up)
    {
        return new Quaternion(new Matrix4f().InitRotation(point.Sub(position).Normalize(), up));
    }

    public Vector3f GetScale()
    {
        return scale;
    }

    public Transform SetScale(Vector3f scale)
    {
        this.scale = scale;
        return this;
    }

    public Quaternion GetRotation()
    {
        return rotation;
    }

    public Transform SetRotation(Quaternion rotation)
    {
        this.rotation = rotation;
        return this;
    }

    public Vector3f GetPosition()
    {
        return position;
    }

    public Transform SetPosition(Vector3f position)
    {
        this.position = position;
        return this;
    }
}
