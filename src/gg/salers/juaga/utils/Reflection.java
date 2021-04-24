package gg.salers.juaga.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Reflection {
	//Credits to Pseudow
	 private final static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
	    private final static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");

	    public static String getVersion() {
	        String packageName = Bukkit.getServer().getClass().getPackage().getName();;
	        String version = packageName.substring(packageName.lastIndexOf('.') + 1) + ".";
	        return version;
	    }

	    public static void sendPacket(Player player, Object packet) {
	        try {
	            Class<?> packetClass = getNMSClass("Packet");
	            Class<?> entityPlayerClass = getNMSClass("EntityPlayer");
	            Field playerConnectionField = getField(entityPlayerClass, "playerConnection");
	            assert playerConnectionField != null;
	            Method sendPacketMethod = getMethod(playerConnectionField.getType(), "sendPacket", packetClass);

	            Object entityPlayer = getHandle(player);
	            Object playerConnection = playerConnectionField.get(entityPlayer);

	            sendPacketMethod.invoke(playerConnection, packet);
	        } catch (IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	        }
	    }

	    public static Object getHandle(Object obj) {
	        try {
	            return getMethod(obj.getClass(), "getHandle").invoke(obj);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public static Field getField(Class<?> clazz, String name) {
	        try {
	            Field field = clazz.getDeclaredField(name);
	            field.setAccessible(true);
	            return field;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    public static Object invokeField(Object instance, String fieldName) {
	        try {
	            return getField(instance.getClass(), fieldName).get(instance);
	        } catch(Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    }

	    public static void setField(Field field, Object instance, Object value) {
	        if (field == null) throw new RuntimeException("No such field");
	        field.setAccessible(true);
	        try {
	            field.set(instance, value);
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	    }

	    public static Class<?> getOBCClass(String name) {
	        try {
	            return Class.forName(OBC_PREFIX + "." + name);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static Class<?> getNMSClass(String name) {
	        try {
	            return Class.forName(NMS_PREFIX + "." + name);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @SuppressWarnings("unchecked")
		public static Object getEnumValue(@SuppressWarnings("rawtypes") Class<Enum> enumFullName, String enumValue) {
	        return Enum.valueOf(enumFullName, enumValue);
	    }

	    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
	        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
	        for (Method method : clazz.getMethods()) {
	            if (!method.getName().equals(methodName) || !DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) {
	                continue;
	            }
	            return method;
	        }
	        return null;
	    }

	    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
	        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
	        for (Constructor<?> constructor : clazz.getConstructors()) {
	                if (!DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) {
	                    continue;
	                }
	                return constructor;
	            }
	        return null;
	    }

	    public static Object invokeMethod(Object instance, String methodName, Object... arguments) {
	        try {
	            return getMethod(instance.getClass(), methodName, DataType.getPrimitive(arguments)).invoke(instance, arguments);
	        } catch (IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static Object invokeStaticMethod(Class<?> clazz, String methodName, Object... arguments) {
	        try {
	            return getMethod(clazz, methodName, DataType.getPrimitive(arguments)).invoke(null, arguments);
	        } catch (IllegalAccessException | InvocationTargetException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    public static <T> T callConstructor(Constructor<T> constructor, Object... paramaters) {
	        if (constructor == null) throw new RuntimeException("No such constructor");
	        constructor.setAccessible(true);
	        try {
	            return constructor.newInstance(paramaters);
	        } catch (InvocationTargetException ex) {
	            throw new RuntimeException(ex.getCause());
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	    }


	        /**
	         * Represents an enumeration of Java data types with corresponding classes
	         * <p/>
	         * This class is part of the <b>ReflectionUtils</b> and follows the same usage conditions
	         *
	         * @author DarkBlade12
	         * @since 1.0
	         */
	    public enum DataType {
	        BYTE(byte.class, Byte.class),
	        SHORT(short.class, Short.class),
	        INTEGER(int.class, Integer.class),
	        LONG(long.class, Long.class),
	        CHARACTER(char.class, Character.class),
	        FLOAT(float.class, Float.class),
	        DOUBLE(double.class, Double.class),
	        BOOLEAN(boolean.class, Boolean.class);

	        private static final Map<Class<?>, DataType> CLASS_MAP = new HashMap<>();
	        private final Class<?> primitive;
	        private final Class<?> reference;

	        // Initialize map for quick class lookup
	        static {
	            for (DataType type : values()) {
	                CLASS_MAP.put(type.primitive, type);
	                CLASS_MAP.put(type.reference, type);
	            }
	        }

	        /**
	         * Construct a new data type
	         *
	         * @param primitive Primitive class of this data type
	         * @param reference Reference class of this data type
	         */
	        DataType(Class<?> primitive, Class<?> reference) {
	            this.primitive = primitive;
	            this.reference = reference;
	        }

	        /**
	         * Returns the data type with the given primitive/reference class
	         *
	         * @param clazz Primitive/Reference class of the data type
	         * @return The data type
	         */
	        public static DataType fromClass(Class<?> clazz) {
	            return CLASS_MAP.get(clazz);
	        }

	        /**
	         * Returns the primitive class of the data type with the given reference class
	         *
	         * @param clazz Reference class of the data type
	         * @return The primitive class
	         */
	        public static Class<?> getPrimitive(Class<?> clazz) {
	            DataType type = fromClass(clazz);
	            return type == null ? clazz : type.getPrimitive();
	        }

	        /**
	         * Returns the reference class of the data type with the given primitive class
	         *
	         * @param clazz Primitive class of the data type
	         * @return The reference class
	         */
	        public static Class<?> getReference(Class<?> clazz) {
	            DataType type = fromClass(clazz);
	            return type == null ? clazz : type.getReference();
	        }

	        /**
	         * Returns the primitive class array of the given class array
	         *
	         * @param classes Given class array
	         * @return The primitive class array
	         */
	        public static Class<?>[] getPrimitive(Class<?>[] classes) {
	            int length = classes == null ? 0 : classes.length;
	            Class<?>[] types = new Class<?>[length];
	            for (int index = 0; index < length; index++) {
	                types[index] = getPrimitive(classes[index]);
	            }
	            return types;
	        }

	        /**
	         * Returns the reference class array of the given class array
	         *
	         * @param classes Given class array
	         * @return The reference class array
	         */
	        public static Class<?>[] getReference(Class<?>[] classes) {
	            int length = classes == null ? 0 : classes.length;
	            Class<?>[] types = new Class<?>[length];
	            for (int index = 0; index < length; index++) {
	                types[index] = getReference(classes[index]);
	            }
	            return types;
	        }

	        /**
	         * Returns the primitive class array of the given object array
	         *
	         * @param objects Given object array
	         * @return The primitive class array
	         */
	        public static Class<?>[] getPrimitive(Object[] objects) {
	            int length = objects == null ? 0 : objects.length;
	            Class<?>[] types = new Class<?>[length];
	            for (int index = 0; index < length; index++) {
	                types[index] = getPrimitive(objects[index].getClass());
	            }
	            return types;
	        }

	        /**
	         * Returns the reference class array of the given object array
	         *
	         * @param objects Given object array
	         * @return The reference class array
	         */
	        public static Class<?>[] getReference(Object[] objects) {
	            int length = objects == null ? 0 : objects.length;
	            Class<?>[] types = new Class<?>[length];
	            for (int index = 0; index < length; index++) {
	                types[index] = getReference(objects[index].getClass());
	            }
	            return types;
	        }

	        /**
	         * Compares two class arrays on equivalence
	         *
	         * @param primary   Primary class array
	         * @param secondary Class array which is compared to the primary array
	         * @return Whether these arrays are equal or not
	         */
	        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
	            if (primary == null || secondary == null || primary.length != secondary.length) {
	                return false;
	            }
	            for (int index = 0; index < primary.length; index++) {
	                Class<?> primaryClass = primary[index];
	                Class<?> secondaryClass = secondary[index];
	                if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass)) {
	                    continue;
	                }
	                return false;
	            }
	            return true;
	        }

	        /**
	         * Returns the primitive class of this data type
	         *
	         * @return The primitive class
	         */
	        public Class<?> getPrimitive() {
	            return primitive;
	        }

	        /**
	         * Returns the reference class of this data type
	         *
	         * @return The reference class
	         */
	        public Class<?> getReference() {
	            return reference;
	        }
	    }

	}

