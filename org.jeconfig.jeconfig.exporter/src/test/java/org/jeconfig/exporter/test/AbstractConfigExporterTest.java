/*
 * Copyright (c) 2011: Edmund Wagner, Wolfram Weidel
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the jeconfig nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jeconfig.exporter.test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jeconfig.api.ConfigServiceAccessor;
import org.jeconfig.api.IConfigService;
import org.jeconfig.api.IConfigSetupService;
import org.jeconfig.api.persister.IConfigPersistenceService;
import org.jeconfig.api.scope.ClassScopeDescriptor;
import org.jeconfig.api.scope.IScopePath;
import org.jeconfig.api.scope.IScopePropertyProvider;
import org.jeconfig.api.scope.IScopeRegistry;
import org.jeconfig.api.scope.UserScopeDescriptor;
import org.jeconfig.client.internal.ConfigServiceImpl;
import org.jeconfig.exporter.internal.ExportScopeDescriptor;
import org.jeconfig.exporter.internal.ExportScopePathGenerator;
import org.jeconfig.server.ConfigPersistenceServiceImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractConfigExporterTest {
	private static IConfigService configService;
	private static IConfigPersistenceService configPersistenceService;
	private static CountingDummyPersister persister;
	private static IConfigSetupService configSetupService;

	@BeforeClass
	public static void setUpClass() {
		final ConfigServiceImpl configServiceImpl = new ConfigServiceImpl();
		configService = configServiceImpl;

		configPersistenceService = new ConfigPersistenceServiceImpl();

		configSetupService = configServiceImpl;

		final IScopeRegistry scopeRegistry = configSetupService.getScopeRegistry();
		scopeRegistry.addScopeDescriptor(new ExportScopeDescriptor());
		scopeRegistry.addScopePropertyProvider(new IScopePropertyProvider() {
			@Override
			public String getScopeName() {
				return UserScopeDescriptor.NAME;
			}

			@Override
			public Map<String, String> getProperties(final Class<?> configClass) {
				final Map<String, String> result = new HashMap<String, String>();
				result.put(UserScopeDescriptor.PROP_USER_NAME, "lukas"); //$NON-NLS-1$
				return result;
			}
		});
		persister = new CountingDummyPersister();
		configPersistenceService.addConfigPersister(persister);
		configServiceImpl.bindConfigPersister(configPersistenceService);
	}

	@AfterClass
	public static void tearDownClass() {
		if (configService != null) {
			configService = null;
		}
		if (configPersistenceService != null) {
			configPersistenceService = null;
		}
	}

	@After
	public void tearDown() {
		configService.deleteAllOccurences(ClassScopeDescriptor.NAME, Collections.<String, String> emptyMap());
		if (persister != null) {
			persister.clear();
		}
	}

	public ConfigServiceAccessor getConfigService() {
		return new ConfigServiceAccessor(configService);
	}

	public static IConfigPersistenceService getConfigPersistenceService() {
		return configPersistenceService;
	}

	public IConfigPersistenceService getConfigSetupService() {
		return configPersistenceService;
	}

	public <T> IScopePath getExportScope(final IScopePath scopePath, final Class<T> configClass) {
		return new ExportScopePathGenerator().buildExportScopePath(scopePath, configClass);

	}
}
