/*
 * Copyright (C) 2013 Legend Zero
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.legendzero.exploration.util;

import com.legendzero.exploration.api.entity.IEntity;
import com.legendzero.exploration.api.tiles.ITile;
import com.legendzero.exploration.api.world.IWorld;
import org.lwjgl.BufferUtils;

import javax.vecmath.Point2d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Vector2d;
import java.nio.DoubleBuffer;

/**
 *
 * @author CrypticStorm
 */
public class AABB {

    private Tuple2d min;
    private Tuple2d max;

    public AABB(Tuple2d min, Tuple2d max) {
        this.min = min;
        this.max = max;
    }

    public AABB(ITile tile) {
        this.min = new Point2d(tile.getLocation().getX(), tile.getLocation().getY());
        (this.max = new Point2d(this.min)).add(new Point2d(1, 1));
    }

    public AABB(IEntity entity) {
        this.min = new Point2d(entity.getLocation().getX() - entity.getWidth() / 2, entity.getLocation().getY());
        this.max = new Point2d(entity.getLocation().getX() + entity.getWidth() / 2, entity.getLocation().getY() + entity.getHeight());
    }

    public AABB(IWorld world) {
        this.min = new Point2d(0, 0);
        this.max = new Point2d(world.getWidth(), world.getHeight());
    }

    public AABB copy() {
        return new AABB((Tuple2d) this.min.clone(), (Tuple2d) this.max.clone());
    }

    public DoubleBuffer getBuffer() {
        return (DoubleBuffer)BufferUtils.createDoubleBuffer(12)
                .put(this.min.x).put(this.min.y).put(0)
                .put(this.max.x).put(this.min.y).put(0)
                .put(this.max.x).put(this.max.y).put(0)
                .put(this.min.x).put(this.max.y).put(0)
                .flip();
    }

    public Tuple2d getMin() {
        return this.min;
    }

    public Tuple2d getMax() {
        return this.max;
    }

    public double getLeft() {
        return this.min.x;
    }

    public double getRight() {
        return this.max.x;
    }

    public double getBottom() {
        return this.min.y;
    }

    public double getTop() {
        return this.max.y;
    }

    public boolean collides(AABB other) {
        return this.max.x >= other.min.x && this.max.y >= other.min.y && this.min.x <= other.max.x && this.min.y <= other.max.y;
    }

    public boolean collidesStrict(AABB other) {
        return this.max.x > other.min.x && this.max.y > other.min.y && this.min.x < other.max.x && this.min.y < other.max.y;
    }

    public boolean hitsTopOf(AABB other) {
        return this.getMin().y <= other.getMax().y && this.getMax().y >= other.getMax().y;
    }

    public boolean hitsBottomOf(AABB other) {
        return this.getMax().y >= other.getMin().y && this.getMin().y <= other.getMin().y;
    }

    public boolean hitsLeftOf(AABB other) {
        return this.getMin().x <= other.getMin().x && this.getMax().x >= other.getMin().x;
    }

    public boolean hitsRightOf(AABB other) {
        return this.getMax().x >= other.getMax().x && this.getMin().x <= other.getMax().x;
    }

    public AABB add(Tuple2d vector) {
        this.min.add(vector);
        this.max.add(vector);
        return this;
    }

    public AABB expand(Tuple2d vector) {
        if (vector.x < 0.0) {
            this.min.x += vector.x;
        } else {
            this.max.x += vector.x;
        }

        if (vector.y < 0.0) {
            this.min.y += vector.y;
        } else {
            this.max.y += vector.y;
        }

        return this;
    }

    public AABB parentAABB(AABB other) {
        return new AABB(
                new Point2d(Math.min(this.min.x, other.min.x), Math.min(this.min.y, other.min.y)),
                new Point2d(Math.max(this.max.x, other.max.x), Math.max(this.max.y, other.max.y)));
    }

    public boolean[] bound(AABB bound) {
        boolean[] collisions = new boolean[4];
        if (this.min.x < bound.min.x) {
            double diff = bound.min.x - this.min.x;
            this.min.x += diff;
            this.max.x += diff;
            collisions[0] = true;
        }
        if (this.max.x > bound.max.x) {
            double diff = bound.max.x - this.max.x;
            this.min.x += diff;
            this.max.x += diff;
            collisions[1] = true;
        }
        if (this.min.y < bound.min.y) {
            double diff = bound.min.y - this.min.y;
            this.min.y += diff;
            this.max.y += diff;
            collisions[2] = true;
        }
        if (this.max.y > bound.max.y) {
            double diff = bound.max.y - this.max.y;
            this.min.y += diff;
            this.max.y += diff;
            collisions[3] = true;
        }
        return collisions;
    }

    public IntersectData collide(AABB other, Vector2d velocity) {
        double xInvEntry, yInvEntry;
        double xInvExit, yInvExit;

        // find the distance between the objects on the near and far sides for both x and y
        if (velocity.x > 0) {
            xInvEntry = other.min.x - this.max.x;
            xInvExit = other.max.x - this.min.x;
        } else {
            xInvEntry = other.max.x - this.min.x;
            xInvExit = other.min.x - this.max.x;
        }

        if (velocity.y > 0) {
            yInvEntry = other.min.y - this.max.y;
            yInvExit = other.max.y - this.min.y;
        } else {
            yInvEntry = other.max.y - this.min.y;
            yInvExit = other.min.y - this.max.y;
        }

        double xEntry, yEntry;
        double xExit, yExit;

        if (velocity.x == 0) {
            xEntry = Double.NEGATIVE_INFINITY;
            xExit = Double.POSITIVE_INFINITY;
        } else {
            xEntry = xInvEntry / velocity.x;
            xExit = xInvExit / velocity.x;
        }

        if (velocity.y == 0) {
            yEntry = Double.NEGATIVE_INFINITY;
            yExit = Double.POSITIVE_INFINITY;
        } else {
            yEntry = yInvEntry / velocity.y;
            yExit = yInvExit / velocity.y;
        }

        double entryTime = Math.max(xEntry, yEntry);
        double exitTime = Math.min(xExit, yExit);

        if (entryTime > exitTime || (xEntry < 0 && yEntry < 0)) {
            return new IntersectData(new Vector2d(0, 0), 1);
        }
        if (xEntry < 0) {
            if (this.max.x <= other.min.x || this.min.x >= other.max.x) {
                return new IntersectData(new Vector2d(0, 0), 1);
            }
        }
        if (yEntry < 0) {
            if (this.max.y <= other.min.y || this.min.y >= other.max.y) {
                return new IntersectData(new Vector2d(0, 0), 1);
            }
        }

        if (xEntry > yEntry) {
            if (xInvEntry <= 0) {
                return new IntersectData(new Vector2d(1, 0), entryTime);
            } else {
                return new IntersectData(new Vector2d(-1, 0), entryTime);
            }
        } else {
            if (yInvEntry <= 0) {
                return new IntersectData(new Vector2d(0, 1), entryTime);
            } else {

                return new IntersectData(new Vector2d(0, -1), entryTime);
            }
        }
    }

}
