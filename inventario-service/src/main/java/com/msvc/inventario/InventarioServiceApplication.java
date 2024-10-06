package com.msvc.inventario;
/*
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;*/
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.msvc.inventario.repository.InventarioRepository;
import org.springframework.context.annotation.Bean;
import com.msvc.inventario.model.Inventario;
@SpringBootApplication
//@EnableDicoveryClient
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventarioRepository inventarioRepository){
		return args -> {
			Inventario inventario = new Inventario();
			inventario.setCodigoSku("iphone_12");
			inventario.setCantidad(100);

			Inventario inventario2 = new Inventario();
			inventario2.setCodigoSku("iphone_12_blue");
			inventario2.setCantidad(0);

			inventarioRepository.save(inventario);
			inventarioRepository.save(inventario2);
		};
	}
}
