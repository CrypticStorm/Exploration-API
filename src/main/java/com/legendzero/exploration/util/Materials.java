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

import com.legendzero.exploration.api.item.IMaterial;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author CrypticStorm
 */
public class Materials {

    private static Map<String, IMaterial> materials = new HashMap<String, IMaterial>();

    public static boolean addMaterial(IMaterial material) {
        if (materials.containsKey(material.getName())) {
            return false;
        } else {
            System.out.println("Material Registry: Added '" + material.getName() + "'");
            materials.put(material.getName(), material);
            return true;
        }
    }

    public static IMaterial getMaterial(String name) {
        return materials.get(name);
    }
}
