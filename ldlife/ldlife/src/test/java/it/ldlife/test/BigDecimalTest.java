package it.ldlife.test;

import java.math.BigDecimal;

import org.junit.Test;

public class BigDecimalTest {

	@Test
	public void test1(){
//		System.out.println(0.05+0.1);
//		System.out.println(1.0-0.42);
//		System.out.println(4.015*100);
//		System.out.println(12.3/100);
	}
	@Test
	public void test2(){
		BigDecimal b1 = new BigDecimal(0.05);
		BigDecimal b2 = new BigDecimal(0.2);
		System.out.println(b1.add(b2));
	}
	
	@Test
	public void test3(){
		BigDecimal b1 = new BigDecimal("0.05");
		BigDecimal b2 = new BigDecimal("0.2");
		b1.add(b2);
		System.out.println(b1.add(b2));
	}
	
}
