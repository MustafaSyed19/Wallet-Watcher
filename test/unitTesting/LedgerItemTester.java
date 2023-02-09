package unitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import persistence.LedgerItem;

class LedgerItemTester {
	LedgerItem obj;
	String input = "xxxxxx";
	
	@BeforeEach
	void init() {
		obj = LedgerItem.getInstanceOf(input);
	}
	
	@Test
	void testStaticFactory() {
		
		assertTrue(LedgerItem.getInstanceOf(input) instanceof LedgerItem, "static factory method failed");
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	void testSetterGetter() {
		assertEquals("", obj.getNote());
		assertTrue(Math.abs(obj.getAmount() - 0.00) <= 0.0001);
		assertEquals("", obj.getItemName());
		assertEquals(null, obj.getDate());
		
		double amount = 15.63;
		String s = "Wendy's";
		String n = "double combo, with fries and coke";
		Date d = new Date();
		d.setYear(2023);
		d.setMonth(0);
		d.setDate(10);
		d.setHours(11);
		d.setMinutes(30);
		d.setSeconds(0);
		obj.setDate(d);
		obj.setAmount(amount);
		obj.setItemName(s);
		obj.setNote(n);
		
		assertEquals(n, obj.getNote());
		assertTrue(Math.abs(obj.getAmount() - amount) <= 0.0001);
		assertEquals(s, obj.getItemName());
		assertEquals(d, obj.getDate());
	}
}
