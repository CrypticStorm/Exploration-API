package com.legendzero.exploration.util;

import javax.vecmath.Vector2d;

public class IntersectData {

    private final Vector2d normal;
    private final double time;

    public IntersectData(Vector2d normal, double time) {
        this.normal = normal;
        this.time = time;
    }

    public Vector2d getNormal() {
        return this.normal;
    }

    public double getTime() {
        return this.time;
    }

    public boolean equals(Object other) {
        if (other instanceof IntersectData) {
            IntersectData otherData = (IntersectData) other;
            return this.getNormal().equals(otherData.getNormal()) && this.getTime() == otherData.getTime();
        } else {
            return false;
        }
    }
}