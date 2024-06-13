package br.com.sgv.controller;

import br.com.sgv.model.Item;
import br.com.sgv.model.VendaClienteClube;
import br.com.sgv.repository.ClienteClubeRepository;
import br.com.sgv.repository.ProdutoRepository;
import br.com.sgv.repository.VendaClienteClubeRepository;
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
 * @brief class VendaClienteClubeController
 */
@Controller
public class VendaClienteClubeController {

    @Autowired
    private VendaClienteClubeRepository vendaClienteClubeRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ClienteClubeRepository clienteClubeRepository;
    private VendaClienteClube vendaClienteClube;

    @GetMapping("/vendas-cliente-clube")
    public String listarVendasClienteClube(Model model) {
        model.addAttribute("listaVendas", vendaClienteClubeRepository.findAll());
        return "gerenciar_vendas_cliente_clube";
    }

    @GetMapping("/vendas-cliente-clube/novo")
    public String novaVendaClienteClube(Model model) {
        vendaClienteClube = new VendaClienteClube();
        vendaClienteClubeRepository.save(vendaClienteClube);
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        model.addAttribute("listaClientes", clienteClubeRepository.findAll());
        model.addAttribute("vendaClienteClube", vendaClienteClube);
        model.addAttribute("item", new Item());
        return "editar_venda_cliente_clube";
    }

    @GetMapping("/vendas-cliente-clube/{id}")
    public String editar(@PathVariable("id") long idVenda, Model model) {
        Optional<VendaClienteClube> busca = vendaClienteClubeRepository.findById(idVenda);
        vendaClienteClube = busca.get();
        model.addAttribute("vendaClienteClube", vendaClienteClube);
        model.addAttribute("item", new Item());
        model.addAttribute("listaProdutos", produtoRepository.findAll());
        model.addAttribute("listaClientes", clienteClubeRepository.findAll());


        return "editar_venda_cliente_clube";
    }

    @PostMapping("/vendas-cliente-clube")
    public String salvar(@Valid VendaClienteClube vendaClienteClube, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_venda_cliente_clube";
        }
        this.vendaClienteClube.setDataVenda(vendaClienteClube.getDataVenda());
        this.vendaClienteClube.setClienteClube(vendaClienteClube.getClienteClube());
        vendaClienteClubeRepository.save(this.vendaClienteClube);
        return "redirect:/vendas-cliente-clube";
    }
    
    @PostMapping("/vendas-cliente-clube/itens")
    public String adicionarItem(@Valid Item item, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_venda_cliente_clube";
        }
        if (item.getProduto() != null){
            vendaClienteClube.adicionarItem(item);
            item.setVenda(vendaClienteClube);
            vendaClienteClubeRepository.save(vendaClienteClube);
        }
        String url = "redirect:/vendas-cliente-clube/"+ vendaClienteClube.getId();
        return url;
    }

    @GetMapping("/vendas-cliente-clube/itens/{id}")
    public String removerItem(@PathVariable("id") long id) {
        Item aux = null;
        Iterator<Item> iterator = vendaClienteClube.getListaItens().iterator();
        while (iterator.hasNext()){
            Item i = iterator.next();
            if (i.getId() == id){
                aux = i;
                break;
            }
        }
        if (aux != null){
            vendaClienteClube.removerItem(aux);
            aux.setVenda(null);
            vendaClienteClubeRepository.save(vendaClienteClube);
        }
        String url = "redirect:/vendas-cliente-clube/"+ vendaClienteClube.getId();
        return url;
    }
    
    @DeleteMapping("/vendas-cliente-clube/{id}")
    public String excluir(@PathVariable("id") long id) {
        vendaClienteClubeRepository.deleteById(id);
        return "redirect:/vendas-cliente-clube";
    }
}
