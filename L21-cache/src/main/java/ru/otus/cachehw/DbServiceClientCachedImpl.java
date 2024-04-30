package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DbServiceClientCachedImpl implements DBServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientCachedImpl.class);
    final MyCache<Long, Client> cache = new MyCache<>();
    final DBServiceClient dbClient;

    public DbServiceClientCachedImpl(DBServiceClient dbClient) {
        this.dbClient = dbClient;
        HwListener<String, Integer> listener = new HwListener<String, Integer>() {
            @Override
            public void notify(String key, Integer value, String action) {
                logger.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
    }

    @Override
    public Client saveClient(Client client) {
        cache.put(client.getId(), client);
        return dbClient.saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long l) {
        var cached = cache.get(l);
        if (Objects.isNull(cached)) {
            var toCache = dbClient.getClient(l);
            toCache.ifPresent(client -> cache.put(client.getId(), client));
            return toCache;
        } else {
            return Optional.of(cached);
        }
    }

    @Override
    public List<Client> findAll() {
        var toCache = dbClient.findAll();
        for (var client : toCache) {
            cache.put(client.getId(), client);
        }
        return toCache;
    }
}
