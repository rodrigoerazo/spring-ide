/*******************************************************************************
 * Copyright (c) 2017, 2019 Pivotal, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal, Inc. - initial API and implementation
 *******************************************************************************/
package org.springframework.ide.eclipse.boot.dash.cf.model;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.springframework.ide.eclipse.beans.ui.live.model.LiveBeansModel;
import org.springframework.ide.eclipse.boot.dash.cf.runtarget.CloudFoundryRunTarget;
import org.springframework.ide.eclipse.boot.dash.model.BootDashModel;
import org.springframework.ide.eclipse.boot.dash.model.WrappingBootDashElement;
import org.springframework.ide.eclipse.boot.dash.model.actuator.ActuatorClient;
import org.springframework.ide.eclipse.boot.dash.model.actuator.RequestMapping;
import org.springframework.ide.eclipse.boot.dash.model.actuator.env.LiveEnvModel;
import org.springsource.ide.eclipse.commons.livexp.core.AsyncLiveExpression;
import org.springsource.ide.eclipse.commons.livexp.core.LiveExpression;

import com.google.common.collect.ImmutableList;

public abstract class CloudDashElement<T> extends WrappingBootDashElement<T> {

	public CloudDashElement(BootDashModel bootDashModel, T delegate) {
		super(bootDashModel, delegate);
	}

	private LiveExpression<ImmutableList<RequestMapping>> liveRequestMappings;
	private LiveExpression<LiveBeansModel> liveBeans;
	private LiveExpression<LiveEnvModel> liveEnv;

	protected ActuatorClient getActuatorClient(URI target) {
		return new RestActuatorClient(target, getTypeLookup(), getRestClient());
	}

	protected Client getRestClient() {
		return ClientBuilder.newClient();
	}

	@Override
	public List<RequestMapping> getLiveRequestMappings() {
		synchronized (this) {
			if (liveRequestMappings==null) {
				final LiveExpression<URI> actuatorUrl = getActuatorUrl();
				liveRequestMappings = new AsyncLiveExpression<ImmutableList<RequestMapping>>(null, "Fetch request mappings for '"+getName()+"'") {
					@Override
					protected ImmutableList<RequestMapping> compute() {
						URI target = actuatorUrl.getValue();
						if (target!=null) {
							ActuatorClient client = getActuatorClient(target);
							List<RequestMapping> list = client.getRequestMappings();
							if (list!=null) {
								return ImmutableList.copyOf(client.getRequestMappings());
							}
						}
						return null;
					}

				};
				liveRequestMappings.dependsOn(actuatorUrl);
				addElementState(liveRequestMappings);
				addDisposableChild(liveRequestMappings);
			}
		}
		return liveRequestMappings.getValue();
	}

	@Override
	public LiveBeansModel getLiveBeans() {
		synchronized (this) {
			if (liveBeans == null) {
				final LiveExpression<URI> actuatorUrl = getActuatorUrl();
				liveBeans = new AsyncLiveExpression<LiveBeansModel>(null, "Fetch beans for '"+getName()+"'") {
					@Override
					protected LiveBeansModel compute() {
						URI target = actuatorUrl.getValue();
						if (target != null) {
							ActuatorClient client = getActuatorClient(target);
							return client.getBeans();
						}
						return null;
					}

				};
				liveBeans.dependsOn(actuatorUrl);
				addElementState(liveBeans);
				addDisposableChild(liveBeans);
			}
		}
		return liveBeans.getValue();
	}

	@Override
	public LiveEnvModel getLiveEnv() {
		synchronized (this) {
			if (liveEnv == null) {
				final LiveExpression<URI> actuatorUrl = getActuatorUrl();
				liveEnv = new AsyncLiveExpression<LiveEnvModel>(null, "Fetch env for '"+getName()+"'") {
					@Override
					protected LiveEnvModel compute() {
						URI target = actuatorUrl.getValue();
						if (target != null) {
							ActuatorClient client = getActuatorClient(target);
							return client.getEnv();
						}
						return null;
					}

				};
				liveEnv.dependsOn(actuatorUrl);
				addElementState(liveEnv);
				addDisposableChild(liveEnv);
			}
		}
		return liveEnv.getValue();
	}

	protected LiveExpression<URI> getActuatorUrl() {
		return LiveExpression.constant(null);
	}

	@Override
	public CloudFoundryRunTarget getTarget() {
		return (CloudFoundryRunTarget) super.getTarget();
	}

}
