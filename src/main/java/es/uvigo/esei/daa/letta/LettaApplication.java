package es.uvigo.esei.daa.letta;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import es.uvigo.esei.daa.letta.rest.EventResource;
import es.uvigo.esei.daa.letta.rest.UsersResource;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationPath("/rest/*")
public class LettaApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return Stream.of(
				UsersResource.class,
				EventResource.class
		)
			.collect(Collectors.toSet());
	}
	
	@Override
	public Map<String, Object> getProperties() {
		// Activates JSON automatic conversion in JAX-RS
		return Collections.singletonMap(
			"com.sun.jersey.api.json.POJOMappingFeature", true
		);
	}

}