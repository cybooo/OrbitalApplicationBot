package com.github.cybooo.orbital.database;

import com.github.cybooo.orbital.OrbitalBot;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MariaDB {

    private final OrbitalBot orbitalBot;
    private HikariDataSource hikariDataSource;

    public MariaDB(OrbitalBot orbitalBot) {
        this.orbitalBot = orbitalBot;
    }

    public void initialize(String hostname, int port, String username, String database, String password) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mariadb://" + hostname + ":" + port + "/" + database);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

}
