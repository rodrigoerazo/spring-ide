/*******************************************************************************
 * Copyright (c) 2020 Pivotal Software, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.model.remote;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import javax.inject.Provider;
import javax.management.remote.JMXConnector;

import org.springframework.ide.eclipse.boot.dash.api.App;
import org.springframework.ide.eclipse.boot.dash.api.JmxConnectable;
import org.springframework.ide.eclipse.boot.dash.model.AbstractDisposable;
import org.springframework.ide.eclipse.boot.dash.model.RunState;
import org.springframework.ide.eclipse.boot.launch.util.JMXClient;
import org.springframework.ide.eclipse.boot.launch.util.SpringApplicationLifeCycleClientManager;
import org.springframework.ide.eclipse.boot.launch.util.SpringApplicationLifecycleClient;
import org.springsource.ide.eclipse.commons.livexp.core.AsyncLiveExpression;
import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;
import org.springsource.ide.eclipse.commons.livexp.util.Log;

public class JmxRunStateTracker extends AbstractDisposable {

	private final GenericRemoteAppElement bde;
	private final LiveExpression<RunState> _baseRunState;
	private final LiveExpression<App> app;
	private final Callable<JMXConnector> connectionProvider;
	private SpringApplicationLifeCycleClientManager clientMgr;

	private static final long APP_STARTUP_TIMEOUT = 60_000;
	private static final long POLLING_INTERVAL = 500;

	long creationTime = System.currentTimeMillis();


	private void debug(String string) {
		System.out.println(this+" : "+string);
	}

	public final LiveExpression<RunState> augmentedRunState = new AsyncLiveExpression<RunState>(RunState.INACTIVE) {


		{
			setRefreshDelay(POLLING_INTERVAL);
		}

		@Override
		protected RunState compute() {
			debug("Computing augmented runstate...");
			RunState baseRunState = _baseRunState.getValue();
			debug("baseRunState = "+baseRunState);
			if (baseRunState==RunState.RUNNING) {
				try {
					SpringApplicationLifecycleClient client = clientMgr.getLifeCycleClient();
					if (client==null || client.isReady()) {
						debug("jmxClient.isReady() => true");
						return baseRunState;
					}
					//client.isReady() => false
				} catch (Exception e) {
					// failed to connect
					e.printStackTrace();
				}
				// failed to connect or client.isReady -> false
				try {
					refreshMaybe();
					return RunState.STARTING;
				} catch (TimeoutException e1) {
					return RunState.UNKNOWN;
				}
			} else {
				return baseRunState;
			}
		}


		private void refreshMaybe() throws TimeoutException {
			if (!isDisposed()) {
				long age = System.currentTimeMillis()-creationTime;
				debug("age = "+ age);
				if (age < APP_STARTUP_TIMEOUT) {
					refresh();
				} else {
					throw new TimeoutException();
				}
			}
		}
	};

	public JmxRunStateTracker(GenericRemoteAppElement bde, LiveExpression<RunState> baseRunState, LiveExpression<App> app) {
		this.bde = bde;
		this._baseRunState = baseRunState;
		this.app = app;
		this.connectionProvider = () -> {
			App data = app.getValue();
			if (data instanceof JmxConnectable) {
				String url = ((JmxConnectable) data).getJmxUrl();
				debug("jmxUrl = "+url);
				if (url!=null) {
					return JMXClient.createJmxConnectorFromUrl(url);
				}
			}
			return null;
		};
		this.clientMgr = new SpringApplicationLifeCycleClientManager(connectionProvider);
		_baseRunState.onChange(this, (_e, _v) -> {
			creationTime = System.currentTimeMillis();
			augmentedRunState.refresh();
		});
		augmentedRunState.dependsOn(app);
		onDispose(d -> clientMgr.disposeClient());
	}

	@Override
	public String toString() {
		return "JmxRunStateTracker("+bde+")";
	}
}
