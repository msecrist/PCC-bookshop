package io.pivotal.bookshop.services;

import io.pivotal.bookshop.dao.BookJdbcDao;
import io.pivotal.bookshop.dao.CustomerJdbcDao;
import io.pivotal.bookshop.dao.CustomerRepository;
import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.bookshop.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service is designed to front the database interactions in the database via the JdbcTemplate. This assumes that the JdbcTemplate
 * is already configured by Spring Boot and injected with the proper datasource.
 */
@Service
public class DataService {

    private Logger logger = LoggerFactory.getLogger("CustomerService");
    private BookJdbcDao bookDao;
    private CustomerJdbcDao customerDao;
    private CustomerRepository repo;

    @Autowired
    public DataService(  BookJdbcDao bookDao, CustomerJdbcDao customerDao, CustomerRepository repo) {
        this.bookDao = bookDao;
        this.customerDao = customerDao;
        this.repo = repo;
    }

    /**
     * Perform a basic fetch of a customer from the customers table in the database, returning as a Map
     *
     * @param id The customer_number to query for
     * @return A HashMap of the columns and values for the given customer or an empty HashMap on failure
     *
     */
    public Customer getCustomerById(int id) {
        Optional<Customer> c = repo.findById(id);
        if (c.isPresent() ) {
            logger.info("Loaded customer: " + c.get());
            return c.get();
        } else {
            logger.info("Customer not found for customerNumber: " + id);
            return new Customer();
        }
/*
        try {
            return customerDao.getCustomer(id);
        } catch (DataAccessException dae) {
            logger.info("Exception thrown on query: " + dae);
            return new Customer();
        }
*/
    }

    /**
     * Perform a basic fetch of a book from the books table in the database.
     * Enhanced with a CacheAside solution.
     *
     * @param id The item_number to query for
     * @return A HashMap of the columns and values for the given customer or an empty HashMap on failure
     *
     */
    @Cacheable(value = "Books")
    public BookMaster getBookById(int id) {
        try {
            return bookDao.getBook(id);
        } catch (DataAccessException dae) {
            logger.info("Exception thrown on query: " + dae);
            return new BookMaster();
        }
    }

    /**
     * A simple test to ensure that the tables have been initialized
     *
     * @return True if data is found in both customers and books table, false otherwise.
     */
    public boolean isDbInitialized() {
        try {
            return (customerDao.countCustomers() > 0 && bookDao.countBooks() > 0);
        } catch (DataAccessException dae) {
            logger.info("Exception thrown on query: " + dae);
            return false;
        }
    }
}
