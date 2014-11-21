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
package com.legendzero.exploration.api.element.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author CrypticStorm
 */
public class ElementClassLoader extends URLClassLoader {
    private final ElementLoader loader;
    private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    
    ElementClassLoader(ElementLoader loader, URL[] urls, ClassLoader parent, Object methodSignature) {
        super(urls, parent);
        this.loader = loader;
    }
    
    public void addURL(URL url) {
        super.addURL(url);
    }
    
    protected Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        Class<?> result = classes.get(name);
        
        if(result == null) {
            if(checkGlobal) {
                result = loader.getClassByName(name);
            }
            
            if(result == null) {
                result = super.findClass(name);
                
                if(result != null) {
                    loader.setClass(name, result);
                }
            }
            
            classes.put(name, result);
        }
        
        return result;
    }
    
    Set<String> getClasses() {
        return classes.keySet();
    }
}
