package de.whs.stapp.test.presentation;

import java.math.BigDecimal;

import de.whs.stapp.presentation.viewmodels.TrainingSession;
import de.whs.stapp.presentation.Javascript;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Testet die Erzeugung von Javascript-Funktionsaufrufen.
 * 
 * @author Fabian
 */
public class JavascriptTest extends TestCase {
	
	/**
	 * Testet Funktionen ohne Argumente.
	 */
	public void testNoArguments() {

		Assert.assertEquals("javascript:test()", Javascript.getFunctionCall("test"));
		
		Assert.assertEquals("javascript:stapp.foo()", Javascript.getFunctionCall("stapp.foo"));
	}
	
	/**
	 * Testet Funktionen mit Argumenten.
	 */
	public void testWithArguments() {
		
		TrainingSession session = new TrainingSession(new BigDecimal(5200), 80);
		Assert.assertEquals("javascript:test({\"distance\":\"5200\",\"heartfrequence\":\"80\"})", 
				Javascript.getFunctionCall("test", session));
	}
}
