package br.com.sgv.controller;

import br.com.sgv.model.Cliente;
import br.com.sgv.repository.ClienteRepository;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Pablo Rangel <pablo.rangel@gmail.com>
 * @date 22/04/2021
 * @brief class ClienteController
 */
@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    public String listar(Model model) {
        model.addAttribute("listaClientes", clienteRepository.findAll());
        return "gerenciar_clientes";
    }

    @GetMapping("/clientes/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "editar_cliente";
    }

    @GetMapping("/clientes/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        model.addAttribute("cliente", cliente.get());
        return "editar_cliente";
    }

    @PostMapping("/clientes")
    public String salvar(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_cliente";
        }
        clienteRepository.save(cliente);
        return "redirect:/clientes";
    }

    @DeleteMapping("/clientes/{id}")
    public String excluir(@PathVariable("id") long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";
    }
}
