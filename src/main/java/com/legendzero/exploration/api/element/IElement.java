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
package com.legendzero.exploration.api.element;

import com.legendzero.exploration.api.IExploration;
import com.legendzero.exploration.api.element.loader.IElementLoader;

/**
 *
 * @author CrypticStorm
 */
public interface IElement {

    public void initialize(IExploration game, ClassLoader classLoader, IElementLoader loader, String name);

    public IExploration getGame();

    public ClassLoader getClassLoader();

    public IElementLoader getElementLoader();

    public String getName();

    public boolean isEnabled();

    public void setEnabled(boolean isEnabled);

    public void onInit();

    public void onEnable();

    public void onDisable();
}
