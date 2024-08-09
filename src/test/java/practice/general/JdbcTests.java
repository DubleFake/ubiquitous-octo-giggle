package practice.general;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import practice.general.backend.database.IphoneDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
//@Sql({"classpath:static/database.sql", "classpath:static/test-data.sql"})
public class JdbcTests {

    private JdbcTemplate jdbcTemplate;
    private IphoneDatabase iphoneDatabase;

    public JdbcTests() {
        iphoneDatabase = new IphoneDatabase();
        jdbcTemplate = new JdbcTemplate(iphoneDatabase.dataSource());
    }

    @Test
    void testIfAllDataLoads() {
        String count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM iphone", String.class);
        assertThat(count).isEqualTo("5");
    }
}
