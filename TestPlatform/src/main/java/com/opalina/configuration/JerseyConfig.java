package com.opalina.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.opalina.controller.AdminController;
import com.opalina.controller.LoginController;

@Component
@ApplicationPath("/v1")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(LoginController.class);
		register(AdminController.class);
		register(MultiPartFeature.class);
	}
}
