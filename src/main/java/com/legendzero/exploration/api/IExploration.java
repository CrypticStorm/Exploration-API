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
package com.legendzero.exploration.api;

import com.legendzero.exploration.api.entity.IPlayer;
import com.legendzero.exploration.api.world.IWorld;
import com.legendzero.exploration.util.AABB;
import org.newdawn.slick.TrueTypeFont;

import java.util.List;

/**
 *
 * @author CrypticStorm
 */
public interface IExploration {

    public IPlayer getPlayer();

    public AABB getVisibleAABB();

    public List<IWorld> getWorlds();

    public void addWorld(IWorld world);

    public int getViewSize();

    public TrueTypeFont getFont();

    public void stop();
}
