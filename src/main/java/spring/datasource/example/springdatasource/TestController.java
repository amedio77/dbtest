package spring.datasource.example.springdatasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {


    @Autowired
    private TestDao testDao;

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public  void create() throws SQLException {
        testDao.CreateTable();
    }

    @RequestMapping(value="/hello", method= RequestMethod.GET)
    public HashMap<String, Object> test3() throws SQLException {
        HashMap map = testDao.getData();
        return map;
    }
}
