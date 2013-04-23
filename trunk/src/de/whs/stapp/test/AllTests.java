package de.whs.stapp.test;

import junit.framework.Test;
import junit.framework.TestCase;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * Diese Test-Suite sammelt alle Testklassen unterhalb des Packages
 * de.whs.stapp.test.
 * 
 * @author Fabian
 */
public class AllTests extends TestCase {

	/**
	 * Erstellt eine Test-Suite mit allen Testfällen in diesem Projekt.
	 * 
	 * @return Test-Suite.
	 */
	public static Test suite() {
		TestSuiteBuilder builder = new TestSuiteBuilder(AllTests.class);
		builder.includeAllPackagesUnderHere();
		return builder.build();
	}

}
