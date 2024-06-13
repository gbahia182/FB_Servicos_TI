package br.com.sgv.controller;

import br.com.sgv.model.Item;
import br.com.sgv.model.VendaCliente;
import br.com.sgv.repository.ClienteRepository;
import br.com.sgv.repository.ProdutoRepository;
import br.com.sgv.repository.VendaClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Iterator;
import java.util.Optional;

/**
 *
 * @author Pablo Rangel <pablo.rangel@gmail.com>
 * @date 22/04/2021
 * @brief class VendaClienteController
 */
@Controller
public class VendaClienteController {

    @Autowired
    private VendaClienteRepository vendaClienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    private VendaCliente vendaCliente;

    @GetMapping("/vendas-cliente")
    public String listarVendasCliente(Model model) {
        model.addAttribute("listaVendas", vendaClienteRepository.findAll());
        return "gerenciar_vendas_cliente";
    }

    @GetMapping("/vendas-cliente/novo")
    public String novaVendaCliente(Model model) {
        vendaCliente = new VendaCliente();
        vendaClienteRepository.save(vendaCliente);
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        model.addAttribute("listaClientes", clienteRepository.findAll());
        model.addAttribute("vendaCliente", vendaCliente);
        model.addAttribute("item", new Item());
        return "editar_venda_cliente";
    }

    @GetMapping("/vendas-cliente/{id}")
    public String editar(@PathVariable("id") long idVenda, Model model) {
        Optional<VendaCliente> busca = vendaClienteRepository.findById(idVenda);
        vendaCliente = busca.get();
        model.addAttribute("vendaCliente", vendaCliente);
        model.addAttribute("item", new Item());
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        model.addAttribute("listaClientes", clienteRepository.findAll());

        return "editar_venda_cliente";
    }

    @PostMapping("/vendas-cliente")
    public String salvar(@Valid VendaCliente vendaCliente, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_venda_cliente";
        }

        this.vendaCliente.setDataVenda(vendaCliente.getDataVenda());
        this.vendaCliente.setCliente(vendaCliente.getCliente());
        vendaClienteRepository.save(this.vendaCliente);
        return "redirect:/vendas-cliente";
    }
    
    @PostMapping("/vendas-cliente/itens")
    public String adicionarItem(@Valid Item item, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_venda_cliente";
        }
        if (item.getProduto() != null){
            vendaCliente.adicionarItem(item);
            item.setVenda(vendaCliente);
            vendaClienteRepository.save(vendaCliente);
        }
        String url = "redirect:/vendas-cliente/"+ vendaCliente.getId();
        return url;
    }

    @GetMapping("/vendas-cliente/itens/{id}")
    public String removerItem(@PathVariable("id") long id) {
        Item aux = null;
        Iterator<Item> iterator = vendaCliente.getListaItens().iterator();
        while (iterator.hasNext()){
            Item i = iterator.next();
            if (i.getId() == id){
                aux = i;
                break;
            }
        }
        if (aux != null){
            vendaCliente.removerItem(aux);
            aux.setVenda(null);
            vendaClienteRepository.save(vendaCliente);
        }
        String url = "redirect:/vendas-cliente/"+ vendaCliente.getId();
        return url;
    }
    
    @DeleteMapping("/vendas-cliente/{id}")
    public String excluir(@PathVariable("id") long id) {
        vendaClienteRepository.deleteById(id);
        return "redirect:/vendas-cliente";
    }
}
