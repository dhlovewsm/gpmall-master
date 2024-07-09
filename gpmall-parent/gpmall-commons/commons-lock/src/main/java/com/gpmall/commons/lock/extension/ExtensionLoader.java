package com.gpmall.commons.lock.extension;

import com.gpmall.commons.lock.extension.annotation.LockSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ExtensionLoader<T> {


    private static final Logger logger = LoggerFactory.getLogger(ExtensionLoader.class);


    private static final String SERVICES_DIRECTORY = "META-INF/services/";

    private static final String LOCK_DIRECTORY = "META-INF/lock/";


    private static final String LOCK_INTERNAL_DIRECTORY = LOCK_DIRECTORY + "internal/";


    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();


    private final ConcurrentHashMap<Class<?>, String> cachedNames = new ConcurrentHashMap<>();


    private static final ConcurrentHashMap<Class<?>, Object> EXTENSION_INSTANCE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADER_MAP = new ConcurrentHashMap<>();


    private final ConcurrentHashMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();


    private static final Pattern NAME_SEPARATOR = Pattern.compile("\\s*[,]+\\s*");


    private Set<Class<?>> cachedWrapperClasses;


    private String cachedDefaultName;


    private final Class<?> type;


    public ExtensionLoader(Class<?> type){
        this.type = type;
        loadExtensionClasses();
    }


    public static ExtensionLoader getExtensionLoader(Class type) {

        check(type);
        ExtensionLoader extensionLoader = EXTENSION_LOADER_MAP.get(type);
        if (extensionLoader != null){
            return extensionLoader;
        }

        synchronized (EXTENSION_LOADER_MAP){
            extensionLoader = EXTENSION_LOADER_MAP.get(type);
            if (extensionLoader == null){
                extensionLoader = new ExtensionLoader(type);
                EXTENSION_LOADER_MAP.put(type, extensionLoader);
            }
        }
        return extensionLoader;
    }


    private static void check(Class type){
        if (type == null){
            throw new IllegalArgumentException("Extension type is null");
        }
        if(! type.isInterface()){
            throw new IllegalArgumentException("Extension type(" + type + ") is not interface!");
        }
        if(! withExtensionAnnotation(type)){
            throw new IllegalArgumentException("Extension type(" + type + ") is not extension, because without @" + LockSpi.class.getSimpleName() + "Annotation !");
        }
    }

    private static <T>boolean withExtensionAnnotation(Class<T> type) {
        return type.isAnnotationPresent(LockSpi.class);
    }

    public T getExtension(String name) {

        if (name == null || name.length() == 0 || "true".equals(name)){
            return getDefaultExtension();
        }
        if(cachedWrapperClasses.contains(name)){
            throw new IllegalArgumentException("name is error wrapper is forbidden");
        }
        Holder<Object> holder = cachedInstances.get(name);
        if (holder == null){
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        Object instance = holder.get();
        if (instance == null){
            synchronized (holder){
                instance = holder.get();
                if (instance == null){
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }

        return (T) instance;

    }

    private T getDefaultExtension() {

        getExtensionClasses();
        if (null == cachedDefaultName || cachedDefaultName.length() == 0 || "true".equals(cachedDefaultName)){
            return null;
        }
        return  getExtension(cachedDefaultName);
    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.get();
        if (classes == null){
            synchronized (cachedClasses){
                classes = cachedClasses.get();
                if (classes == null){
                    try {
                        classes = loadExtensionClasses();
                    } catch (Exception e){
                        throw new IllegalArgumentException(e);
                    }
                    cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private Map<String, Class<?>> loadExtensionClasses() {

        final LockSpi defaultAnnotation = type.getAnnotation(LockSpi.class);
        if (defaultAnnotation != null){
            String value = defaultAnnotation.value();
            if (value != null && (value = value.trim()).length() > 0){
                String[] names = NAME_SEPARATOR.split(value);
                if (names.length > 1){
                    throw new IllegalArgumentException("more than 1 default extension name on extension " + type.getName() + ": " + Arrays.toString(names));
                }

                if (names.length == 1) {
                    cachedDefaultName = names[0];
                }
            }
        }

        Map<String, Class<?>> extensionClasses = new HashMap<>();
        try {
            loadFile(extensionClasses, LOCK_DIRECTORY);
            loadFile(extensionClasses, LOCK_INTERNAL_DIRECTORY);
            loadFile(extensionClasses, SERVICES_DIRECTORY);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return extensionClasses;
    }

    private void loadFile(Map<String, Class<?>> extensionClasses, String dir) throws IOException {

        String fileName = dir + type.getName();
        Enumeration<URL> urls;
        ClassLoader classLoader = findClassLoader();
        if (classLoader != null){
            urls = classLoader.getResources(fileName);
        } else {
            urls = ClassLoader.getSystemResources(fileName);
        }

        if (urls != null){
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                BufferedReader reader = null;

                String line = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                    while((line = reader.readLine()) != null){
                        final int ci = line.indexOf('#');
                        if (ci > 0){
                            line = line.substring(0, ci).trim();
                        }
                        if (line.length() > 0){
                            String name = null;
                            int i = line.indexOf('=');
                            if (i > 0){
                                name = line.substring(0, i).trim();
                                line = line.substring(i + 1).trim();
                            }
                            if (line.length() > 0){
                                Class<?> clazz = Class.forName(line, true, classLoader);
                                if (! type.isAssignableFrom(clazz)){
                                    throw new IllegalArgumentException("Error when load extension class(interface: " + type + ", class line: " + clazz.getName() + "), class : " + clazz.getName() + "is not subtype of interface");
                                }

                                try {
                                    clazz.getConstructor(type);
                                    Set<Class<?>> wrappers = cachedWrapperClasses;
                                    if (wrappers == null){
                                        cachedWrapperClasses = new ConcurrentHashSet<Class<?>>();
                                        wrappers = cachedWrapperClasses;
                                    }
                                    wrappers.add(clazz);
                                } catch (NoSuchMethodException e) {
                                    clazz.getConstructor();
                                    if (name == null || name.length() == 0){
                                        throw new IllegalArgumentException("No such extension name for the class " + clazz.getName() + " in the config " + url);
                                    }
                                    String[] names = NAME_SEPARATOR.split(name);
                                    if (names != null && names.length > 0){
                                        for (String n : names){
                                            if (! cachedNames.containsKey(clazz)){
                                                cachedNames.put(clazz, n);
                                            }
                                            Class<?> c = extensionClasses.get(n);
                                            if (c == null){
                                                extensionClasses.put(n, clazz);
                                            } else if (c != clazz) {
                                                throw new IllegalArgumentException("Duplicate extension " + type.getName() + n + " on " + c.getName() + " and " + clazz.getName());
                                            }
                                        }
                                    }
                                }

                            }
                        }
                    }
                } catch (Throwable t){
                    throw new IllegalArgumentException("Failed to load extension class(interface: " + type + ", class line: " + line + ") in " + url + ", cause: " + t.getMessage(), t);
                } finally{
                    if (reader != null){
                        reader.close();
                    }
                }
            }
        }


    }

    private ClassLoader findClassLoader() {
        return ExtensionLoader.class.getClassLoader();
    }


    private T createExtension(String name) {
        Class<?> clazz = getExtensionClasses().get(name);
        if (clazz == null){
            throw new IllegalArgumentException();
        }
        try {
            T instance = (T) EXTENSION_INSTANCE.get(clazz);
            if (instance == null){
                EXTENSION_INSTANCE.putIfAbsent(clazz, (T) clazz.newInstance());
            }
            Set<Class<?>> wrapperClasses = cachedWrapperClasses;
            if (wrapperClasses != null && wrapperClasses.size() > 0){
                for (Class<?> wrapperClass : wrapperClasses){
                    instance = (T) wrapperClass.getConstructor(type).newInstance(instance);
                }
            }
            return instance;
        } catch (Throwable t){
            throw new IllegalArgumentException("Extension instance(name: " + name + ", class: " + type + ") could not be instantiated: " + t.getMessage(), t);
        }
    }
}
