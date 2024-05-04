package ru.otus.cachehw;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;

import javax.sql.DataSource;


public class HWCacheDemo {
    private static final Logger logger = LoggerFactory.getLogger(HWCacheDemo.class);
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }

    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        // Общая часть
        var app = new HWCacheDemo();
        app.demo();
        app.hwCachedDbService(dataSource);
    }

    private void hwCachedDbService(DriverManagerDataSource dataSource) throws NoSuchMethodException {
        var clientDB = new DbServiceClientCachedFactory().create(dataSource);
        var c1 = clientDB.saveClient(new Client("dbServiceTest"));
        clientDB.getClient(c1.getId());
    }
    private void demo() {
        HwCache<String, Integer> cache = new MyCache<>();

        // пример, когда Idea предлагает упростить код, при этом может появиться "спец"-эффект
        @SuppressWarnings("java:S1604")
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };

        cache.addListener(listener);
        cache.put("1", 1);

        logger.info("getValue:{}", cache.get("1"));
        cache.remove("1");
        cache.removeListener(listener);
    }
}
