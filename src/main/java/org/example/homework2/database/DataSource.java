package org.example.homework2.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static final HikariConfig config;
    private static final HikariDataSource  ds;
    private static final Logger logger= LoggerFactory.getLogger(DataSource.class);

     static {
        InputStream input = DataSource.class.getClassLoader().getResourceAsStream("datasource.properties");
            Properties properties = new Properties();
            try {
                properties.load(input);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            config = new HikariConfig(
                    properties);
            ds = new HikariDataSource(config);
        }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
