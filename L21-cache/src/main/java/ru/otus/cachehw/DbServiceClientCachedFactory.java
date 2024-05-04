package ru.otus.cachehw;

import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.*;

public final class DbServiceClientCachedFactory {

    public DBServiceClient create(DriverManagerDataSource dataSource) throws NoSuchMethodException {
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        EntityResultSetFactory<Client> f = new ClientResultSetFactory();
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, f);

        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        return new DbServiceClientCachedImpl(dbServiceClient);
    }
}
