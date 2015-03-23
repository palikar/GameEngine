package com.company.Rendering;

import com.company.Math.Vector2f;
import com.company.Math.Vector3f;

/**
 * Created by Stanislav on 2.3.2015 г..
 */
public class Vertex {

    public static final int SIZE = 8;
    private Vector3f position;
    private Vector2f texCoord;
    private Vector3f normal;

    public Vertex(Vector3f position) {
        this(position, new Vector2f());
    }

    public Vertex(Vector3f position, Vector2f texCoord) {
        this(position, texCoord, new Vector3f());

    }

    public Vertex(Vector3f position, Vector2f texCoord, Vector3f normal) {
        this.position = position;
        this.texCoord = texCoord;
        this.normal = normal;
    }

    public Vector3f GetPosition() {
        return position;
    }

    public void SetPosition(Vector3f position) {
        this.position = position;
    }

    public Vector2f GetTexCoord() {
        return texCoord;
    }

    public void SetTexCoord(Vector2f texCoord) {
        this.texCoord = texCoord;
    }

    public Vector3f GetNormal() {
        return normal;
    }

    public void SetNormal(Vector3f normal) {
        this.normal = normal;
    }

}
