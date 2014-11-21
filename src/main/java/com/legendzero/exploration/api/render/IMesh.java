package com.legendzero.exploration.api.render;

import org.lwjgl.BufferUtils;

import javax.vecmath.Color4f;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

public interface IMesh extends Renderable {

    DoubleBuffer getVertexBuffer();

    void setVertexBuffer(DoubleBuffer buffer, int size);

    FloatBuffer getColorBuffer();

    void setColorBuffer(FloatBuffer buffer, int size);

    DoubleBuffer getNormalBuffer();

    void setNormalBuffer(DoubleBuffer buffer, int size);

    DoubleBuffer getTexCoordBuffer();

    void setTexCoordBuffer(DoubleBuffer buffer, int size);

    int getItemSize();

    void setItemSize(int itemSize);

    int getBeginMode();

    void setBeginMode(int beginMode);

    public static FloatBuffer getStaticFloatBuffer(Color4f color) {
        return (FloatBuffer)BufferUtils.createFloatBuffer(16)
                .put(color.x).put(color.y).put(color.z).put(color.w)
                .put(color.x).put(color.y).put(color.z).put(color.w)
                .put(color.x).put(color.y).put(color.z).put(color.w)
                .put(color.x).put(color.y).put(color.z).put(color.w)
                .flip();
    }
}
