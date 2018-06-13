package code4kix.example;

import org.apache.catalina.Context;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "tomcat.staticResourceCustomizer.enabled", matchIfMissing = true)
public class TomcatConfiguration {
	@Bean
	public WebServerFactoryCustomizer<WebServerFactory> staticResourceCustomizer() {
		return new WebServerFactoryCustomizer<WebServerFactory>() {
		
		@Override
		public void customize(WebServerFactory factory) {
			if (factory instanceof TomcatServletWebServerFactory) {
				((TomcatServletWebServerFactory) factory)
						.addContextCustomizers(new org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer() {
							@Override
							public void customize(Context context) {
								context.addLifecycleListener(new StaticResourceConfigurer(context));
							}
						});
				}
			}
		};
	}
}
