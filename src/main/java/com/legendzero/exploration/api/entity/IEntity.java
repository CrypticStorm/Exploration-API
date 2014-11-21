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
package com.legendzero.exploration.api.entity;

import com.legendzero.exploration.api.IExploration;
import com.legendzero.exploration.api.render.Renderable;
import com.legendzero.exploration.util.Location;
import javax.vecmath.Color4f;
import javax.vecmath.Vector2d;

/**
 *
 * @author CrypticStorm
 */
public interface IEntity extends Renderable {

    public void update(IExploration game);

    public Location getLocation();

    public void setLocation(Location location);

    public Vector2d getVelocity();

    public void setVelocity(Vector2d velocity);

    public String getName();

    public double getHealth();

    public double getMaxHealth();

    public double getWidth();

    public double getHeight();

    public double heal(double amount);

    public double damage(double amount);

    public boolean isDamageable();

    public boolean isAlive();

    public boolean isOnGround();

    public boolean isFlying();

    public void setFlying(boolean isFlying);
    
}
