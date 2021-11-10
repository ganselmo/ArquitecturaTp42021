package tp4.despensa.fillers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tp4.despensa.entities.Cliente;
import tp4.despensa.entities.Producto;
import tp4.despensa.entities.Venta;
import tp4.despensa.repositories.ClienteRepository;
import tp4.despensa.repositories.ProductoRepository;
import tp4.despensa.repositories.VentaRepository;

@Configuration
public class DbFiller {
	private static Logger LOG = LoggerFactory.getLogger(DbFiller.class);
	@Bean
	public CommandLineRunner initDb(ClienteRepository cr,ProductoRepository pr,VentaRepository vr) {
		return args-> {
			IntStream.range(0, 10).forEach(i->{
				Cliente c = new Cliente("Nombre "+i,"Apellido "+i,"3000000"+i);
				LOG.info(c.toString());
				cr.save(c);
			});
			
			IntStream.range(0, 10).forEach(i->{

				Producto p = new Producto("Nombre "+i,(int)(Math.random()*10)+1,(int) ((Math.random() * 10000) + 100) / 100.0);
				LOG.info(p.toString());
				pr.save(p);
			});
			
			
			IntStream.range(0, 10).forEach(i->{
				
				
				ArrayList<Producto> productos= new ArrayList<Producto>();
				
				int canter = (int)(Math.random()*3);
				int cant = canter;
			
				LOG.info(Integer.toString(canter));
				
				
				for (int j = 0; j < cant; j++) {
					
					int id = (int)(Math.random()*10)+1;
					LOG.info(Integer.toString(id));
					Producto p = pr.getById(id);
					
					LOG.info(p.toString());
					productos.add(p);
				}
		
				
				
				Cliente c = cr.getById((int)(Math.random()*10)+1);
		
				Venta v = new Venta(c,productos);
				
				vr.save(v);
				LOG.info(v.toString());
			});
		};
	}
}
