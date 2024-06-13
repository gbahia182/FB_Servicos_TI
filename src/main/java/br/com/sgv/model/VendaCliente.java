package br.com.sgv.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Pablo Rangel <pablo.rangel@gmail.com>
 * @date 06/05/2021
 * @brief  class VendaCliente
 */
@Entity
@Getter
@Setter
public class VendaCliente extends Venda{
    @OneToOne
    private Cliente cliente;

    public double calcularTotal(){
        double soma = 0;
        for (Item i : getListaItens()){
            soma += i.getProduto().getPreco() * i.getQuantidade();
        }
        if (soma>=500) {
            return soma * 0.9;
        }else{
            return soma;
    }
    }
}
