package practice.general.backend.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import practice.general.backend.Iphone;

import javax.sql.DataSource;
import java.util.List;

public class IphoneDatabase {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public IphoneDatabase(){
        jdbcTemplate = new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:static/database.sql")
                .addScript("classpath:static/test-data.sql").build();
    }

    public List<Iphone> getAllRecords(){
        RowMapper<Iphone> rowMapper = new BeanPropertyRowMapper<>(Iphone.class);
        return jdbcTemplate.query("SELECT * FROM iphone", rowMapper);
    }

    public String addNewRecord(Iphone iphone){
        System.out.println(iphone.getId());
        System.out.println(iphone.getName());
        System.out.println(iphone.getPrice());
        System.out.println(iphone.getDescription());
        jdbcTemplate.execute("INSERT INTO iphone (Name, Price, Description) VALUES ('" + iphone.getName() + "','" + iphone.getPrice() + "','" + iphone.getDescription() + "')");
        return "Created " + iphone.getId();
    }

    public String removeRecord(String ID){
        jdbcTemplate.execute("DELETE FROM iphone WHERE ID=" + ID);
        return "Deleted " + ID;
    }

    public List<Iphone> getOneRecord(String ID){
        RowMapper<Iphone> rowMapper = new BeanPropertyRowMapper<>(Iphone.class);
        return jdbcTemplate.query("SELECT * FROM iphone WHERE ID="+ ID, rowMapper);
    }

}
