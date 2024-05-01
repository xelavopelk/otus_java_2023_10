package ru.otus.homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Program {
    private static final Logger logger = LoggerFactory.getLogger(Program.class);
    public static void main(String[] args) {
        logger.info("HW-10");
        TestLoggingInterface test = LogProxy.create(TestLoggingInterface.class, new TestLogging());
        test.calculation(1);
        test.calculation(1,2);
        test.calculation(1,2,"3");
    }
}
