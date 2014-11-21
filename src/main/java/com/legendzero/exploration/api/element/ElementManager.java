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
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author CrypticStorm
 */
public class ElementManager implements IElementManager {

    private final IExploration game;
    private final Map<Pattern, IElementLoader> fileAssociations = new HashMap<Pattern, IElementLoader>();
    private final List<IElement> elements = new ArrayList<IElement>();
    private final Map<String, IElement> lookupNames = new HashMap<String, IElement>();

    public ElementManager(IExploration game) {
        this.game = game;
    }

    public void registerLoader(Class<? extends IElementLoader> loader) throws IllegalArgumentException {
        IElementLoader instance;

        if (IElementLoader.class.isAssignableFrom(loader)) {
            Constructor<? extends IElementLoader> constructor;

            try {
                constructor = loader.getConstructor(IExploration.class);
                instance = constructor.newInstance(game);
            } catch (Exception e) {
                return;
            }
        } else {
            return;
        }

        Pattern[] patterns = instance.getFileFilters();

        synchronized (this) {
            for (Pattern pattern : patterns) {
                this.fileAssociations.put(pattern, instance);
            }
        }
    }

    public synchronized IElement getElement(String name) {
        return this.lookupNames.get(name);
    }

    public IElement[] getElements() {
        return this.elements.toArray(new IElement[0]);
    }

    public boolean isElementEnabled(String name) {
        IElement element = this.getElement(name);
        
        return this.isElementEnabled(element);
    }

    public boolean isElementEnabled(IElement element) {
        if((element != null) && this.elements.contains(element)) {
            return element.isEnabled();
        } else {
            return false;
        }
    }

    public IElement loadElement(File file) {
        if (file == null) {
            return null;
        }

        Set<Pattern> filters = this.fileAssociations.keySet();
        IElementLoader loader = null;
        for (Pattern filter : filters) {
            Matcher match = filter.matcher(file.getName());
            if (match.find()) {
                loader = this.fileAssociations.get(filter);
            }
        }

        if (loader == null) {
            return null;
        }

        IElement element = loader.loadElement(file);
        this.elements.add(element);
        this.lookupNames.put(element.getName(), element);

        return element;

    }

    public IElement[] loadElements(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        List<IElement> result = new ArrayList<IElement>();
        Set<Pattern> filters = this.fileAssociations.keySet();

        for (File file : directory.listFiles()) {
            IElementLoader loader = null;
            for (Pattern filter : filters) {
                Matcher match = filter.matcher(file.getName());
                if (match.find()) {
                    loader = this.fileAssociations.get(filter);
                }
            }

            if (loader == null) {
                continue;
            }

            IElement element = loader.loadElement(file);
            result.add(element);
            this.elements.add(element);
            this.lookupNames.put(element.getName(), element);

        }

        return result.toArray(new IElement[result.size()]);
    }

    public void disableElements() {
        for(IElement element : this.getElements()) {
            this.disableElement(element);
        }
    }

    public void enableElements() {
        for(IElement element : this.getElements()) {
            this.enableElement(element);
        }
    }

    public void clearElements() {
        synchronized(this) {
            this.disableElements();
            this.elements.clear();
            this.lookupNames.clear();
            this.fileAssociations.clear();
        }
    }

    public void enableElement(IElement element) {
        if(!element.isEnabled()) {
            element.getElementLoader().enableElement(element);
        }
    }

    public void disableElement(IElement element) {
        if(element.isEnabled()) {
            element.getElementLoader().disableElement(element);
        }
    }

}
