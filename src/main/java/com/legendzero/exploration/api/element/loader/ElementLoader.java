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

import com.legendzero.exploration.api.IExploration;
import com.legendzero.exploration.api.element.Element;
import com.legendzero.exploration.api.element.IElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;

/**
 *
 * @author CrypticStorm
 */
public class ElementLoader implements IElementLoader {

    private final IExploration game;
    private final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();
    private final Map<String, ElementClassLoader> loaders = new LinkedHashMap<String, ElementClassLoader>();
    private final Pattern[] fileFilters = new Pattern[]{Pattern.compile("\\.jar$")};

    public ElementLoader(IExploration game) {
        this.game = game;
    }

    public Pattern[] getFileFilters() {
        return this.fileFilters;
    }

    public Element loadElement(File file) {
        if (file == null || !file.exists()) {
            return null;
        }

        Document document = this.getElementDocument(file);
        nu.xom.Element root = document.getRootElement();
        String name = root.getFirstChildElement("name").getValue();
        File dataFolder = new File(file.getParentFile(), name);

        if (dataFolder.exists()) {
            if (!dataFolder.isDirectory()) {
                return null;
            }
        } else {
            dataFolder.mkdir();
        }

        ElementClassLoader loader;
        Element result;

        try {
            URL[] urls = new URL[1];

            urls[0] = file.toURI().toURL();

            loader = new ElementClassLoader(this, urls, this.getClass().getClassLoader(), null);

            Class<?> jarClass = Class.forName(root.getFirstChildElement("main").getValue(), true, loader);
            Class<? extends Element> element = jarClass.asSubclass(Element.class);

            Constructor<? extends Element> constructor = element.getConstructor();

            result = constructor.newInstance();

            result.initialize(this.game, loader, this, name);
        } catch (Exception e) {
            return null;
        }

        loaders.put(name, loader);

        return result;
    }

    public void enableElement(IElement element) {
        if (element instanceof Element) {
            Element e = (Element) element;
            String name = e.getName();

            if (!this.loaders.containsKey(name)) {
                this.loaders.put(name, (ElementClassLoader) e.getClassLoader());
            }

            try {
                e.setEnabled(true);
            } catch (Exception ex) {
                System.out.println("Error Enabling Element: " + name);
            }
        }
    }

    public void disableElement(IElement element) {
        if (element instanceof Element && element.isEnabled()) {
            Element e = (Element) element;
            String name = e.getName();
            ClassLoader classLoader = e.getClassLoader();

            try {
                e.setEnabled(false);
            } catch (Exception ex) {
                System.out.println("Error Disabling Element: " + name);
            }

            this.loaders.remove(name);

            if (classLoader instanceof ElementClassLoader) {
                ElementClassLoader loader = (ElementClassLoader) classLoader;
                Set<String> names = loader.getClasses();

                for (String n : names) {
                    removeClass(n);
                }
            }
        }
    }

    public Document getElementDocument(File file) {
        JarFile jar = null;
        InputStream stream = null;

        try {
            jar = new JarFile(file);
            JarEntry entry = jar.getJarEntry("element.xml");

            if (entry == null) {
                return null;
            }

            stream = jar.getInputStream(entry);

            return new Builder().build(stream);
        } catch (IOException e) {
            return null;
        } catch (ParsingException e) {
            return null;
        } finally {
            if (jar != null) {
                try {
                    jar.close();
                } catch (IOException e) {
                }
            }
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    Class<?> getClassByName(String name) {
        Class<?> cachedClass = classes.get(name);

        if (cachedClass != null) {
            return cachedClass;
        } else {
            for (String current : loaders.keySet()) {
                ElementClassLoader loader = loaders.get(current);

                try {
                    cachedClass = loader.findClass(name, false);
                } catch (ClassNotFoundException e) {
                }
                if (cachedClass != null) {
                    return cachedClass;
                }
            }
        }
        return null;
    }

    void setClass(String name, Class<?> clazz) {
        if (!classes.containsKey(name)) {
            classes.put(name, clazz);
        }
    }

    private void removeClass(String name) {
        classes.remove(name);
    }
}
