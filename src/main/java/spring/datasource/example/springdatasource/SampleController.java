package spring.datasource.example.springdatasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SampleController {


    @Autowired
    private SampleDao sampleDao;

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public  void create() throws SQLException {
        sampleDao.CreateTable();
    }

    @RequestMapping(value="/test", method= RequestMethod.GET)
    public List test3() throws SQLException {
        ArrayList<Map> result = new ArrayList<>();

        result.add(sampleDao.getData());
        result.add(sampleDao.redisTest());
        result.add(sampleDao.mongoTest());

        return result;
    }


}
