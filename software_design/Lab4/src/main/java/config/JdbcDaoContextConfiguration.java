package config;

import dao.TodoListsJdbcDao;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by nikita on 09.11.16.
 */
public class JdbcDaoContextConfiguration {
    @Bean
    public TodoListsJdbcDao productJdbcDao(DataSource dataSource) {
        return new TodoListsJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:todo.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}