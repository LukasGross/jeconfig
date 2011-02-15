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

package org.jeconfig.client.internal;

import org.jeconfig.api.autosave.IConfigAutoSaveService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public final class Activator implements BundleActivator {
	private static BundleContext bundleContext;
	private ServiceTracker tracker;
	private IConfigAutoSaveService service;

	private static void setBundleContext(final BundleContext ctx) {
		bundleContext = ctx;
	}

	@Override
	public void start(final BundleContext context) throws Exception {
		setBundleContext(context);
		tracker = new ServiceTracker(context, IConfigAutoSaveService.class.getName(), new ServiceTrackerCustomizer() {
			@Override
			public void removedService(final ServiceReference reference, final Object service) {}

			@Override
			public void modifiedService(final ServiceReference reference, final Object service) {}

			@Override
			public Object addingService(final ServiceReference reference) {
				service = (IConfigAutoSaveService) context.getService(reference);
				return service;
			}
		});
		tracker.open();
	}

	public static BundleContext getContext() {
		return bundleContext;
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		setBundleContext(null);
		tracker.close();
		if (service != null) {
			service.close();
			service = null;
		}
	}

}