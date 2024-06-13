package br.com.sgv.controller;

import br.com.sgv.model.ClienteClube;
import br.com.sgv.repository.ClienteClubeRepository;
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
 * @brief class ClienteClubeController
 */
@Controller
public class ClienteClubeController {

    @Autowired
    private ClienteClubeRepository clienteClubeRepository;

    @GetMapping("/clientes-clube")
    public String listar(Model model) {
        model.addAttribute("listaClientesClube", clienteClubeRepository.findAll());
        return "gerenciar_clientes_clube";
    }

    @GetMapping("/clientes-clube/novo")
    public String novo(Model model) {
        model.addAttribute("clienteClube", new ClienteClube());
        return "editar_cliente_clube";
    }

    @GetMapping("/clientes-clube/{id}")
    public String editar(@PathVariable("id") long id, Model model) {
        Optional<ClienteClube> clienteClube = clienteClubeRepository.findById(id);
        model.addAttribute("clienteClube", clienteClube.get());
        return "editar_cliente_clube";
    }

    @PostMapping("/clientes-clube")
    public String salvar(@Valid ClienteClube clienteClube, BindingResult result) {
        if (result.hasErrors()) {
            return "editar_cliente_clube";
        }
        clienteClubeRepository.save(clienteClube);
        return "redirect:/clientes-clube";
    }

    @DeleteMapping("/clientes-clube/{id}")
    public String excluir(@PathVariable("id") long id) {
        clienteClubeRepository.deleteById(id);
        return "redirect:/clientes-clube";
    }
}
