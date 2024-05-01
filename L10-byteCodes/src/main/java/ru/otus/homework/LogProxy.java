package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class LogProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(Program.class);
    private final Object realObject;

    private LogProxy(Object toProxy) {
        this.realObject = toProxy;
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceType, T realObject) {
        return (T) Proxy.newProxyInstance(
                realObject.getClass().getClassLoader(),
                new Class<?>[]{interfaceType},
                new LogProxy(realObject));
    }

    private Boolean checkArgs(Class<?>[] papamTypes, Object[] args) {
        return Arrays.stream(papamTypes).toList().equals(Arrays.stream(args).map(Object::getClass).toList());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method[] declaredMethods = realObject.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(method.getName())) {
                if (Objects.nonNull(declaredMethod.getAnnotation(Log.class)) && checkArgs(declaredMethod.getParameterTypes(), args)) {
                    String params = Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(","));
                    logger.info("executed method {} params: {}", method.getName(), params);
                }
            }
        }
        return method.invoke(realObject, args);
    }
}
