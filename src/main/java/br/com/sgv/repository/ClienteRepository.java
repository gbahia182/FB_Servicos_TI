package br.com.sgv.repository;


import br.com.sgv.model.Cliente;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Pablo Rangel <pablo.rangel@gmail.com>
 */
public interface ClienteRepository extends CrudRepository<Cliente,Long>{
    
}
