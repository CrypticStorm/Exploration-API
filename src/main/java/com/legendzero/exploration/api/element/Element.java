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
public class Element implements IElement {

    private IExploration game = null;
    private ClassLoader classLoader = null;
    private IElementLoader loader = null;
    private String name = null;
    private boolean isEnabled = false;

    public Element() {
    }

    public final void initialize(IExploration game, ClassLoader classLoader, IElementLoader loader, String name) {
        this.game = game;
        this.classLoader = classLoader;
        this.loader = loader;
        this.name = name;

        this.onInit();
    }

    public final IExploration getGame() {
        return this.game;
    }

    public final ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public final IElementLoader getElementLoader() {
        return this.loader;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }

    public void setEnabled(boolean enabled) {
        if (this.isEnabled != enabled) {
            this.isEnabled = enabled;
            if (this.isEnabled) {
                this.onEnable();
            } else {
                this.onDisable();
            }
        }
    }

    public void onInit() {
    }

    public void onEnable() {
    }

    public void onDisable() {
    }
}
