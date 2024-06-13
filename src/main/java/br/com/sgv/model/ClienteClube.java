package br.com.sgv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Pablo Rangel <pablo.rangel@gmail.com>
 * @date 12/05/2021
 * @brief  class ClienteClube
 */
@Entity
@Getter
@Setter
public class ClienteClube {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Size(min=1,message = "O nome precisa ser válido.")
    private String nome;
    @Size(min=1,message = "O CPF precisa ser válido.")
    private String cpf;

    public boolean temDesconto() {
        return true;
    }
}
