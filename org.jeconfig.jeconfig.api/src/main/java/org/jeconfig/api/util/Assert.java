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

package org.jeconfig.api.util;

import java.util.Collection;

public final class Assert {

	private Assert() {}

	/**
	 * Ensure that param is not null.
	 * 
	 * @param object
	 * @param name
	 * @throws IllegalArgumentException
	 *             if object is null
	 */
	public static void paramNotNull(final Object object, final String name) {
		if (object == null) {
			throw new IllegalArgumentException("The parameter '" + name + "' must not be null!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Ensure that string is not null and not empty.
	 * 
	 * @param string
	 * @param name
	 * @throws IllegalArgumentException
	 *             if string is null or size < 0
	 */
	public static void paramNotEmpty(final String string, final String name) {
		if (string == null || string.length() <= 0) {
			throw new IllegalArgumentException("The parameter '" + name + "' must not be empty!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Ensure that collection is not null and not empty.
	 * 
	 * @param collection
	 * @param name
	 * @throws IllegalArgumentException
	 *             if string is null or size < 0
	 */
	public static void paramNotEmpty(final Collection<?> collection, final String name) {
		if (collection == null || collection.size() <= 0) {
			throw new IllegalArgumentException("The parameter '" + name + "' must not be empty!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

}
