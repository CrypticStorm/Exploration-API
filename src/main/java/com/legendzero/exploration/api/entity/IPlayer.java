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
import com.legendzero.exploration.api.control.IController;
import java.util.Set;

/**
 *
 * @author CrypticStorm
 */
public interface IPlayer extends IEntity {

    public void update(IExploration game);

    public void renderGUI(IExploration game);

    public Set<IController> getControllers();

    public void addController(IController controller);

    public double getViewSize();

    public void resetViewSize();

    public void setViewSize(double viewSize);

    public int getMoney();

    public void setMoney(int amount);

    public void addMoney(int amount);

    public void takeMoney(int amount);

}
