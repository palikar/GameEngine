package com.company.Util;

import com.company.Math.Matrix4f;
import com.company.Rendering.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created by Stanislav on 21.2.2015 г..
 */
public class Util {

    public static FloatBuffer CreateFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer CreateIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static ByteBuffer CreateByteBuffer(int size) {
        return BufferUtils.createByteBuffer(size);
    }

    public static IntBuffer CreateFlippedBuffer(int... values) {
        IntBuffer buffer = CreateIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();

        return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = CreateFloatBuffer(vertices.length * Vertex.SIZE);

        for (int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].GetPosition().GetX());
            buffer.put(vertices[i].GetPosition().GetY());
            buffer.put(vertices[i].GetPosition().GetZ());
            buffer.put(vertices[i].GetTexCoord().GetX());
            buffer.put(vertices[i].GetTexCoord().GetY());
            buffer.put(vertices[i].GetNormal().GetX());
            buffer.put(vertices[i].GetNormal().GetY());
            buffer.put(vertices[i].GetNormal().GetZ());
        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer CreateFlippedBuffer(Matrix4f value) {
        FloatBuffer buffer = CreateFloatBuffer(4 * 4);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buffer.put(value.Get(i, j));
            }
        }

        buffer.flip();

        return buffer;
    }

    public static String[] RemoveEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < data.length; i++) {
            if (!data[i].equals("")) {
                result.add(data[i]);
            }
        }

        String[] res = new String[result.size()];
        result.toArray(res);

        return res;
    }

    public static int[] ToIntArray(Integer[] data) {
        int[] result = new int[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = data[i].intValue();
        }

        return result;
    }
}
