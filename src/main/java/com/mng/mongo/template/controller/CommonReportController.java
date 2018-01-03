package com.mng.mongo.template.controller;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

import org.apache.commons.lang.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mng.domain.ReportRecordNew;
import com.mng.mongo.template.service.CommonReportService;
import com.mng.utils.page.PageResult;
import com.mng.utils.page.Pager;

public class CommonReportController
{
    private static ApplicationContext app;
    @Autowired
    private static CommonReportService mtService;
    
    @BeforeClass
    public static void initSpring() {
        app = new ClassPathXmlApplicationContext(new String[] { "classpath:spring/framework-context.xml", "classpath:spring/mongodb.xml" });
        mtService = (CommonReportService) app.getBean("commonReportService");
    }


//    @Autowired
//    private MongoTemplate mongoTemplate;
//    @Autowired
//    private MongoTemplateRepository mongoTemplateRepository;

    /**
     * 暂未实现 具体调用见测试类{@link CommonReportControllerTest}
     */
    
}
