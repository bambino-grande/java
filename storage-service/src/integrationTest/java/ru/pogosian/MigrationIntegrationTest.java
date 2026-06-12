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
    void allTablesCreatedSuccessfully() {
        assertTableExists("users");
        assertTableExists("car_detail");
        assertTableExists("car_model");
        assertTableExists("cars");
        assertTableExists("car_configuration");
        assertTableExists("assembly_orders");
        assertTableExists("assembly_order_required_details");
        assertTableExists("outbox_events");
        assertTableExists("processed_events");
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
