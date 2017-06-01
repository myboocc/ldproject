package it.ldlife.test;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import it.ldlife.mongo.dao.CartDao;

public class AggregationTest {
	
	private static CartDao cartDaoImpl;    
    private static  ClassPathXmlApplicationContext  app;    
    @BeforeClass      
    public static void initSpring() {     
        try {           
         app = new ClassPathXmlApplicationContext("classpath:mongo-conf.xml");      
         cartDaoImpl = (CartDao) app.getBean("cartDaoImpl");     
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }

	@Test
	public void test1(){
		int count = cartDaoImpl.selectCartProductCount("1001");
		System.out.println(count);
		
		
	}
	
}
