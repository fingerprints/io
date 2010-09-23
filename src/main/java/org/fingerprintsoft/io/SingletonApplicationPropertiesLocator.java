/*
 * SingletonApplicationPropertiesLocator.java
 *
 * Copyright (C) 2010 Fingerprints Software
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.fingerprintsoft.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Fuzail Sarang
 *
 */
/**
 * A convenient stash for a set of immutable properties which which are loaded
 * once, but accessed multiple times.
 * 
 * <p>
 * The set of properties is loaded from a file specified by the system property
 * "application.properties".
 * </p>
 * 
 * <p>
 * The class is intended to be used as singleton, reading the properties once
 * and storing them for future access.
 * </p>
 */
public class SingletonApplicationPropertiesLocator {

    private static final Map<String, Properties> instances = new HashMap<String, Properties>();
    private static final Log logger = LogFactory
            .getLog(SingletonApplicationPropertiesLocator.class);
    private static final String LOCATION_PROPERTIES = "application.properties";

    /** Pseudo URL prefix for loading from the class path: "classpath:" */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /** URL prefix for loading from the file system: "file:" */
    public static final String FILE_URL_PREFIX = "file:";

    /**
     * Default constructor
     */
    public SingletonApplicationPropertiesLocator() {
    }

    /**
     * Retrieve the properties loaded from a file, the location of which is
     * specified by the system property 'application.properties'.
     * 
     * @return The properties, or null if the location of the properties file
     *         does not exist, or could not be found.
     */
    public Properties getProperties() {
        return getProperties(null);
    }

    /**
     * Retrieve the properties loaded from a file, the location of which is
     * specified by the selector specified 'application.properties'.
     * 
     * @param selector
     *            The properties file to load.
     * 
     * @return The properties, or null if the location of the properties file
     *         does not exist, or could not be found.
     */
    public Properties getProperties(String selector) {

        String resourceLocation = selector;

        if (resourceLocation == null) {
            resourceLocation = System.getProperty(LOCATION_PROPERTIES);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("Using [" + resourceLocation
                        + "] to load properties.");
            }
        }

        if (resourceLocation == null) {
            if (logger.isDebugEnabled()) {
                // under certain circumstances (e.g. the running of test, this
                // is normal)
                logger.info("Resource location not specified.  No properties were loaded.");
            }

            return null;
        }

        Properties properties;
        synchronized (instances) {
            properties = instances.get(resourceLocation);
        }

        if (properties != null) {
            // we already have them loaded
            return properties;
        }

        // we don't have them - so load them
        try {
            properties = loadProperties(resourceLocation);
        } catch (IOException ex) {
            logger.warn("Failed to read properties from '" + resourceLocation
                    + "': " + ex.getMessage());
            return null;
        }

        // update our cache
        synchronized (instances) {
            instances.put(resourceLocation, properties);
        }

        return properties;
    }

    /**
     * @param selector
     *            The selector
     * @return The properties for the given selector
     * 
     */
    public Properties getInstance(String selector) {
        return getProperties(selector);
    }

    private Properties loadProperties(String resourceLocation)
            throws IOException {

        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX) == false
                && resourceLocation.startsWith(FILE_URL_PREFIX) == false) {
            logger.warn("Assuming that " + resourceLocation
                    + " is a file resource.  "
                    + "Please specify the location with either the '"
                    + CLASSPATH_URL_PREFIX + "' or '" + FILE_URL_PREFIX
                    + "' prefix to avoid this warning.");

            resourceLocation = FILE_URL_PREFIX + resourceLocation;
        }

        Properties properties = new Properties();
        properties.load(getInputStream(resourceLocation));

        return properties;
    }

    private InputStream getInputStream(String resourceLocation)
            throws IOException {
        InputStream is = null;
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {

            is = this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            resourceLocation.substring(CLASSPATH_URL_PREFIX
                                    .length()));
        }

        if (resourceLocation.startsWith(FILE_URL_PREFIX)) {
            URL url = new URL(resourceLocation);
            URLConnection con = url.openConnection();
            con.setUseCaches(false);
            is = con.getInputStream();

        }

        if (is == null) {
            throw new FileNotFoundException(resourceLocation
                    + " cannot be opened because it does not exist");
        }

        return is;

    }

    /**
     * For unit testing purposed only.
     */
    void clearPropertyCache() {
        instances.clear();
    }
}
