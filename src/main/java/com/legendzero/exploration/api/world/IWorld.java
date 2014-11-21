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
package com.legendzero.exploration.api.world;

import com.legendzero.exploration.api.IExploration;
import com.legendzero.exploration.api.entity.IEntity;
import com.legendzero.exploration.api.item.IMaterial;
import com.legendzero.exploration.api.render.Renderable;
import com.legendzero.exploration.api.tiles.ITile;
import com.legendzero.exploration.util.Location;
import java.util.Set;
import javax.vecmath.Vector2d;

/**
 *
 * @author CrypticStorm
 */
public interface IWorld extends Renderable {

    public void update(IExploration game);

    public long getSeed();

    public String getName();

    public int getWidth();

    public int getHeight();

    public ITile[][] getMap();

    public Vector2d getGravity();

    public Vector2d getTerminalVelocity();

    public ITile getTile(int x, int y);

    public void setTile(int x, int y, IMaterial material);

    public Location getSpawnLocation();

    public Set<IEntity> getEntities();

    public void addEntity(IEntity entity);

    public void spawnEntity(IEntity entity, Location location);

    public void removeEntity(IEntity entity);

    public ITile[][] generateWorld();
}
