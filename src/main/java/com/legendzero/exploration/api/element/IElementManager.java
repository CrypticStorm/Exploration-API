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

import com.legendzero.exploration.api.element.loader.IElementLoader;
import java.io.File;

/**
 *
 * @author CrypticStorm
 */
public interface IElementManager {

    public void registerLoader(Class<? extends IElementLoader> loader) throws IllegalArgumentException;
    
    public IElement getElement(String name);
    
    public IElement[] getElements();
    
    public boolean isElementEnabled(String name);
    
    public boolean isElementEnabled(IElement element);
    
    public IElement loadElement(File file);
    
    public IElement[] loadElements(File directory);
    
    public void disableElements();
    
    public void enableElements();
    
    public void clearElements();
    
    public void enableElement(IElement element);
    
    public void disableElement(IElement element);
    
    
}
