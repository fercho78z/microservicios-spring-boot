package com.msvc.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import io.netty.resolver.DefaultAddressResolverGroup;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

	
	/*De forma predeterminada, HttpClient utiliza el mecanismo de búsqueda de nombres de dominio de Netty que resuelve un nombre de dominio
	 * de forma asincrónica. Esto es una alternativa al solucionador de bloqueo integrado de la JVM.
	 * porque no resuleve el localhost el cual esta asociada a la IP que asigna infinitum que es la 192.168.1.70 por eso en OrderService
	 * webCliente.get se le pone la ip 127.0.0.1:8092
	 * y cuando se use eureka se debe configurar asi eureka.instance.hostname=localhost en todos los micrositios o usar eureka.instance.prefer-ip-address=true 
	 * igual en todos los micrositios o usar spring.cloud.discovery.enabled=true
	*/
	/*El problema de la injection de dependencia se resolvio al actualizar todo eclipse: Error: 
	 * Parameter 0 of method webClient in com.msvc.order.config.WebClientConfig required a bean of type "org.springframework.web.reactive.function.client.WebClient" that could not be found
	 * The injection point has the following annotations:
     * @org.springframework.beans.factory.annotation.Autowired(required=true)
	 * "Consider defining a bean of type org.springframework.web.reactive.function.client.WebClient "
	 *  se cguardo la pagina de la solcuion en programas de stackoverflow
	 * */
	
	@Bean
	@Primary
    public WebClient builder(){
		 HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
		 return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .build();
	}
	
	/*
    @Bean
    public WebClient builder(){
        return WebClient.builder().build();
    }
    @Bean
    public HttpClient httpClient(){
        return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
    }*/
    
}
