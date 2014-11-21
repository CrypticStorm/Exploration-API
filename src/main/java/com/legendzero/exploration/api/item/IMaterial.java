package com.legendzero.exploration.api.item;

import javax.vecmath.Color4f;

public interface IMaterial {
    String getName();

    String getTextureFile();

    Color4f getColor();

    boolean isSolid();

    boolean isLiquid();
}
