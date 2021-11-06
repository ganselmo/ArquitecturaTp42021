package tp4.despensa.fillers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp4.despensa.entities.Cliente;
import tp4.despensa.repositories.ClienteRepository;

@Configuration
public class DbFiller {
	private static Logger LOG = LoggerFactory.getLogger(DbFiller.class);
	@Bean
	public CommandLineRunner initDb(ClienteRepository libros) {
		return args-> {
			IntStream.range(0, 10).forEach(i->{
				Cliente l = new Cliente("Nombre "+i,"Apellido "+i,"3000000"+i);
				LOG.info(l.toString());
				libros.save(l);
			});
		};
	}
}
