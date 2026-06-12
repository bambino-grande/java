package ru.pogosian;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MigrationIntegrationTest extends BaseIntegrationTest{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void migrationsAppliedSuccessfully() {
        var count = jdbcTemplate.queryForObject("select count(*) from car_model", Integer.class);
        assertTrue(count > 0);
    }

    @Test
    void allTablesCreatedSuccessfully() {
        assertTableExists("users");
        assertTableExists("cars");
        assertTableExists("car_model");
        assertTableExists("car_detail");
        assertTableExists("car_configuration");
        assertTableExists("in_stock_car_orders");
        assertTableExists("complectation_car_orders");
        assertTableExists("test_drive_request");
    }


    private void assertTableExists(String tableName) {
        var count = jdbcTemplate.queryForObject(
                """
                        select count(*)
                        from information_schema.tables
                        where table_name = ?
                        """, Integer.class, tableName);
        assertEquals(1, (int) count);
    }
}
