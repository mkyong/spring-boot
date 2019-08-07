package com.mkyong.repository;

import com.mkyong.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public int save(Book book) {
        return jdbcTemplate.update(
                "insert into books (name, price) values(?,?)",
                book.getName(), book.getPrice());
    }

    @Override
    public int update(Book book) {
        return jdbcTemplate.update(
                "update books set price = ? where id = ?",
                book.getPrice(), book.getId());
    }


    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete books where id = ?",
                id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(
                "select * from books",
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price")
                        )
        );
    }

    // jdbcTemplate.queryForObject, populates a single object
    @Override
    public Optional<Book> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from books where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Book(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price")
                        ))
        );
    }

    @Override
    public List<Book> findByNameAndPrice(String name, BigDecimal price) {
        return jdbcTemplate.query(
                "select * from books where name like ? and price <= ?",
                new Object[]{"%" + name + "%", price},
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getBigDecimal("price")
                        )
        );
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select name from books where id = ?",
                new Object[]{id},
                String.class
        );
    }

    @Override
    public int[] batchUpdate(List<Book> books) {

        return this.jdbcTemplate.batchUpdate(
                "update books set price = ? where id = ?",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setBigDecimal(1, books.get(i).getPrice());
                        ps.setLong(2, books.get(i).getId());
                    }

                    public int getBatchSize() {
                        return books.size();
                    }

                });

    }

    @Override
    public int[][] batchUpdate(List<Book> books, int batchSize) {

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "update books set price = ? where id = ?",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<Book>() {
                    public void setValues(PreparedStatement ps, Book argument) throws SQLException {
                        ps.setBigDecimal(1, argument.getPrice());
                        ps.setLong(2, argument.getId());
                    }
                });
        return updateCounts;

    }

    @Override
    public int[] batchInsert(List<Book> books) {

        return this.jdbcTemplate.batchUpdate(
                "insert into books (name, price) values(?,?)",
                new BatchPreparedStatementSetter() {

                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, books.get(i).getName());
                        ps.setBigDecimal(2, books.get(i).getPrice());
                    }

                    public int getBatchSize() {
                        return books.size();
                    }

                });
    }

    // Any failure causes the entire operation to roll back, none of the book will be added
    @Transactional
    @Override
    public int[][] batchInsert(List<Book> books, int batchSize) {

        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "insert into books (name, price) values(?,?)",
                books,
                batchSize,
                new ParameterizedPreparedStatementSetter<Book>() {
                    public void setValues(PreparedStatement ps, Book argument) throws SQLException {
                        ps.setString(1, argument.getName());
                        ps.setBigDecimal(2, argument.getPrice());
                    }
                });
        return updateCounts;

    }
}