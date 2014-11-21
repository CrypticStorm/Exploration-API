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

/**
 *
 * @author CrypticStorm
 */
public enum Direction {

    LEFT(-1, 0),
    RIGHT(1, 0),
    DOWN(0, -1),
    UP(0, 1);

    static {
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
        DOWN.opposite = UP;
        UP.opposite = DOWN;
    }

    private final int dx;
    private final int dy;
    private Direction opposite;

    private Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public final int getDX() {
        return this.dx;
    }

    public final int getDY() {
        return this.dy;
    }

    public Direction getOpposite() {
        return this.opposite;
    }
}
