package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.example.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.tree.TreePath;

@Component
@Repository
public class CustomerService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private JdbcOperations jdbc;
    
    private static final String SQL_FIND_ONE = "SELECT codice_cliente, CodiceAzienda, Indrizzo FROM customers where codice_cliente = ?";
    public List<Customer> findAll() {
        /*return jdbcTemplate.query(
            "SELECT id, codice_cliente, Indrizzo FROM customers",
                (rs, rowNum) -> new Customer(rs.getInt("id"),
                rs.getString("codice_cliente"), rs.getString("Indrizzo")));
                */
    	return jdbcTemplate.query(
                "SELECT id, codice_cliente, Indrizzo, provincia, lat, log FROM customers",
                    (rs, rowNum) -> new Customer(rs.getInt("id"),
                    rs.getString("codice_cliente"), rs.getString("Indrizzo"), rs.getString("provincia"),
                    rs.getString("lat"), rs.getString("log")));
    }
    
    public List<Customer> select(String codiceAzienda, String codiceCliente, String indrizzo) {
        return jdbcTemplate.query(
                "SELECT * FROM customers WHERE CodiceAzienda= ?",
                new Object[] { codiceAzienda},
                (rs, rowNum) -> new Customer(rs.getString("CodiceAzienda"), rs.getString("Indrizzo"), rs.getString("codice_cliente")));
    }
 
    public Customer selectCustomer(String codCli) {
    	try {
    		Customer Cust = jdbcTemplate.queryForObject(
    		        "select codice_cliente, Indrizzo from customers where codice_cliente = ?",
    		        new Object[]{codCli},
    		        new RowMapper<Customer>() {
    		            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
    		            	Customer actor = new Customer();
    		                actor.setCodiceCliente(rs.getString("codice_cliente"));
    		                actor.setIndrizzo(rs.getString("Indrizzo"));    		                
    		                
    		              //  return actor;
    		                if  (actor.getIndrizzo()==null){
    		                	  return null;
    		                	}else { // list contains exactly 1 element
    		                	  return actor;
    		                	}
    		            }
    		        });
    		if  (Cust.getIndrizzo()==null){
          	  return null;
          	}else { // list contains exactly 1 element
          	  return Cust;
          	}
		//	return Cust;
    	}
    	catch (EmptyResultDataAccessException e) {
    		return null;
    	}
    	
    	  }
    public void update(Customer customer) {
        jdbcTemplate.update( 
        		"UPDATE customers SET codice_cliente=?, Indrizzo=? WHERE Indrizzo= ?",
                customer.getCodiceCliente(), customer.getIndrizzo());
    }
    
    public void updateTable(String CodiceCliente, String indrizzo, String latitude, String longitute) {
        this.jdbcTemplate.update(
        		//"update customers set indrizzo = ?, lat = ? , log= ? WHERE codice_cliente='" + CodiceCliente + "'",
        		"update customers set indrizzo = ?, lat = ? , log= ? WHERE codice_cliente=?",
        		indrizzo, latitude, longitute,CodiceCliente);
    }
    
    public void insertCustomer(String NomeAzienda, String CodiceCliente, String RegioneSociale, String Indrizzo, String comune, String provincia, String stato, String latitude, String longitute) {
    	String sql = "insert into customers" +
        		"(codice_cliente, NomeAzienda, RegioneSociale, Indrizzo, comune, provincia, stato, lat, log)"
        		+ "values(?,?,?,?,?,?,?,?,?)";

    	this.jdbcTemplate.update(sql, CodiceCliente, NomeAzienda, RegioneSociale, Indrizzo, comune, provincia, stato, latitude, longitute);
    }  

}