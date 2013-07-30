/*******************************************************************************
 * Copyright (c) 2013 IBM Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *
 *     Samuel Padgett - initial implementation
 *******************************************************************************/
package org.eclipse.lyo.oslc4j.provider.jena.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.eclipse.lyo.oslc4j.core.model.Service;
import org.eclipse.lyo.oslc4j.core.model.ServiceProvider;
import org.eclipse.lyo.oslc4j.provider.jena.JenaModelHelper;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class ServiceProviderTest {
	@Test
	public void testUsage() throws Exception {
		// Based on http://open-services.net/bin/view/Main/OSLCCoreSpecTurtleExamples?sortcol=table;up=#Service_Provider_Resource
		// (oslc:usage added)
		InputStream is = ServiceProviderTest.class.getResourceAsStream("/provider.ttl");
		assertNotNull("Could not read file: provider.ttl", is);
		Model m = ModelFactory.createDefaultModel();
		m.read(is, null, "TURTLE");

		ServiceProvider[] providers = (ServiceProvider[]) JenaModelHelper.fromJenaModel(m, ServiceProvider.class);

		// Do some basic validation on the service provider document read in.
		assertEquals("Incorrect number of service providers", 1, providers.length);
		Service[] services = providers[0].getServices();
		assertEquals(1, services.length);
		URI[] usages = services[0].getUsages();
		assertEquals("Incorrect number of services", 2, usages.length);

		// Check that the usages are there.
		List<URI> usageList = Arrays.asList(usages);
		assertTrue("Missing ex:usage1", usageList.contains(new URI("http://example.com/ns#usage1")));
		assertTrue("Missing ex:usage2", usageList.contains(new URI("http://example.com/ns#usage2")));
	}
}