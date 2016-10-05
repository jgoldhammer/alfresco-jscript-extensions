package de.jgoldhammer.alfresco.jscript;

import org.alfresco.repo.jscript.ScriptNode;
import org.mozilla.javascript.*;

import java.util.*;

/**
 * Utility methods for converting data between Mozilla Rhino
 * representation and standard Java collections.
 *
 * @author Bulat Yaminov
 */
public class RhinoUtils {

    public static Map<String, Object> convertToMap(ScriptableObject o) {
        Map<String, Object> map = new HashMap<>();
        Object[] propIds = o.getIds();
        for (Object propId : propIds) {
            if (propId instanceof String) {
                String key = (String) propId;
                Object value = o.get(key, o);
                map.put(key, value);
            }
        }
        return map;
    }

    public static int getInteger(Map<String, Object> map, String key, int defaultValue) {
        int result = defaultValue;
        if (map.get(key) != null) {
            if (map.get(key) instanceof Number) {
                result = ((Number) map.get(key)).intValue();
                if (result < 0) {
                    throw new IllegalArgumentException(key + " must be a positive number, but is instead: " +
                            map.get(key));
                }
            } else {
                throw new IllegalArgumentException(key + " must be an integer, but is instead: " + map.get(key));
            }
        }
        return result;
    }

    public static Function getFunction(Map<String, Object> map, String key) {
        Function result = null;
        if (map.get(key) != null) {
            if (map.get(key) instanceof Function) {
                result = (Function) map.get(key);
            } else {
                throw new IllegalArgumentException(key + " must be a function, but is instead: " + map.get(key));
            }
        }
        return result;
    }

    public static List<Object> getArray(Map<String, Object> map, String key) {
        List<Object> result = null;
        Object value = map.get(key);
        if (value != null) {
            if (value instanceof NativeArray) {
                NativeArray array = (NativeArray) value;
                Object[] propIds = array.getIds();
                result = new ArrayList<>(propIds.length);
                for (Object propId : propIds) {
                    if (propId instanceof Integer) {
                        result.add(array.get((Integer) propId, array));
                    }
                }
            } else if (value instanceof Object[]) {
                result = Arrays.asList((Object[]) value);
            } else if (value instanceof NativeJavaArray) {
                result = Arrays.asList((Object[]) ((NativeJavaArray) value).unwrap());
            } else {
                throw new IllegalArgumentException(key +
                        " must be a NativeArray or Object[], but is instead: " + value);
            }
        }
        return result;
    }

    public static boolean getBoolean(Map<String, Object> map, String key, boolean defaultValue) {
        boolean result = defaultValue;
        if (map.get(key) != null) {
            if (map.get(key) instanceof Boolean) {
                result = (Boolean) map.get(key);
            } else {
                throw new IllegalArgumentException(key + " must be a boolean, but is instead: " + map.get(key));
            }
        }
        return result;
    }

    public static ScriptNode getScriptNode(Map<String, Object> map, String key) {
        ScriptNode result = null;
        if (map.get(key) != null) {
            if (map.get(key) instanceof NativeJavaObject) {
                Object o = ((NativeJavaObject) map.get(key)).unwrap();
                if (o instanceof ScriptNode) {
                    result = (ScriptNode) o;
                } else {
                    throw new IllegalArgumentException(key +
                            " must have a ScriptNode as Java object, but has instead: " + o);
                }
            } else {
                throw new IllegalArgumentException(key + " must be a JavaObject, but is instead: " + map.get(key));
            }
        }
        return result;
    }
}
