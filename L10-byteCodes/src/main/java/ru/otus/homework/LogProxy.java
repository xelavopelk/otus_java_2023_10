package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class LogProxy implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(LogProxy.class);
    private final Object realObject;
    private final Set<String> loggedMethods;

    private LogProxy(Object toProxy) {
        this.realObject = toProxy;
        Method[] declaredMethods = realObject.getClass().getDeclaredMethods();
        loggedMethods = Arrays.stream(declaredMethods)
                .filter(m -> Objects.nonNull(m.getAnnotation(Log.class)))
                .map(this::shortName)
                .collect(Collectors.toSet());
    }

    private String shortName(Method m) {
        return m.getName() + ":" + Arrays.stream(m.getParameterTypes()).map(String::valueOf).collect(Collectors.joining(","));
    }

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceType, T realObject) {
        return (T) Proxy.newProxyInstance(
                realObject.getClass().getClassLoader(),
                new Class<?>[]{interfaceType},
                new LogProxy(realObject));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (loggedMethods.contains(shortName(method))) {
            String params = Arrays.stream(args).map(String::valueOf).collect(Collectors.joining(","));
            logger.info("executed method {} params: {}", method.getName(), params);
        }
        return method.invoke(realObject, args);
    }
}
