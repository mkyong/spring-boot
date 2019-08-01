package com.mkyong.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Customer customer) {
        return jdbcTemplate.update(
                "insert into customer (name, age, created_date) values(?,?,?)",
                customer.getName(), customer.getAge(), LocalDateTime.now());
    }

    public Customer findByCustomerId(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerRowMapper());

    }

    public Customer findByCustomerId2(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return (Customer) jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(Customer.class));

    }

    public Customer findByCustomerId3(Long id) {

        String sql = "SELECT * FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Customer(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getTimestamp("created_date").toLocalDateTime()
                ));

    }

    public List<Customer> findAll() {

        String sql = "SELECT * FROM CUSTOMER";

        List<Customer> customers = new ArrayList<>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        for (Map row : rows) {
            Customer obj = new Customer();

            obj.setID(((Integer) row.get("ID")).longValue());
            //obj.setID(((Long) row.get("ID"))); no, ClassCastException
            obj.setName((String) row.get("NAME"));
            obj.setAge(((BigDecimal) row.get("AGE")).intValue()); // Spring returns BigDecimal, need convert
            obj.setCreatedDate(((Timestamp) row.get("CREATED_DATE")).toLocalDateTime());
            customers.add(obj);
        }

        return customers;
    }

    public List<Customer> findAll2() {

        String sql = "SELECT * FROM CUSTOMER";

        List<Customer> customers = jdbcTemplate.query(
                sql,
                new CustomerRowMapper());

        return customers;
    }

    public List<Customer> findAll3() {

        String sql = "SELECT * FROM CUSTOMER";

        List<Customer> customers = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(Customer.class));

        return customers;
    }

    public List<Customer> findAll4() {

        String sql = "SELECT * FROM CUSTOMER";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Customer(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("age"),
                                rs.getTimestamp("created_date").toLocalDateTime()
                        )
        );
    }

    public String findCustomerNameById(Long id) {

        String sql = "SELECT NAME FROM CUSTOMER WHERE ID = ?";

        return jdbcTemplate.queryForObject(
                sql, new Object[]{id}, String.class);

    }

    public int count() {

        String sql = "SELECT COUNT(*) FROM CUSTOMER";

        // queryForInt() is Deprecated
        // https://www.mkyong.com/spring/jdbctemplate-queryforint-is-deprecated/
        //int total = jdbcTemplate.queryForInt(sql);

        return jdbcTemplate.queryForObject(sql, Integer.class);

    }

}
