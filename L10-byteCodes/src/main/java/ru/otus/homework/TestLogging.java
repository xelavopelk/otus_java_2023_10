package ru.otus.homework;

public class TestLogging implements TestLoggingInterface {
    @Override
    @Log
    public Integer calculation(Integer param1) {
        return param1;
    }

    @Override
    public Integer calculation(Integer param1, Integer param2) {
        return param1 + param2;
    }

    @Override
    @Log
    public Integer calculation(Integer param1, Integer param2, String param3) {
        return param1 + param2 + Integer.parseInt(param3);
    }
}
