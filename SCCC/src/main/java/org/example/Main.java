package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ClienteView clienteView = new ClienteView();
        ClienteDAO clienteDAO = new ClienteDAO("clientes");
        ServicoDAO servicoDAO = new ServicoDAO("clientes");

        atualizarListaClientes(clienteView, clienteDAO, servicoDAO);
        clienteView.mostrarTela();

        // Listener para adicionar cliente
        clienteView.adicionarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente novoCliente = clienteView.getClienteForm("Adicionar Cliente", null);
                if (novoCliente != null) {
                    clienteDAO.inserirCliente(novoCliente);
                    clienteView.mostrarMensagem("Cliente adicionado com sucesso!");
                    atualizarListaClientes(clienteView, clienteDAO, servicoDAO);
                }
            }
        });

        // Listener para ver perfil do cliente
        clienteView.verPerfilListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = clienteView.getClienteSelecionado();
                if (id != -1) {
                    Cliente clienteSelecionado = clienteDAO.obterClientePorId(id);
                    List<Servico> servicoCliente = servicoDAO.obterServicoDoCliente(idCliente);

                    ClientePerfilView perfilView = new ClientePerfilView(clienteSelecionado, servicoCliente);

                    // Listener para editar cliente
                    perfilView.editarListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Cliente clienteAtualizado = clienteView.getClienteForm("Editar Cliente", clienteSelecionado);
                            if (clienteAtualizado != null) {
                                clienteDAO.alterarCliente(clienteAtualizado);
                                perfilView.mostrarMensagem("Cliente atualizado com sucesso!");
                                atualizarListaClientes(clienteView, clienteDAO, servicoDAO);
                                perfilView.atualizarDadosCliente(clienteAtualizado, servicoCliente);
                            }
                        }
                    });

                    // Listener para deletar cliente
                    perfilView.deletarListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            clienteDAO.apagarCliente(id);
                            clienteView.mostrarMensagem("Cliente deletado com sucesso!");
                            atualizarListaClientes(clienteView, clienteDAO, servicoDAO);
                            perfilView.fecharPerfil();
                        }
                    });

                    perfilView.mostrarPerfil();
                } else {
                    clienteView.mostrarMensagem("Selecione um cliente para ver o perfil.");
                }
            }
        });
    }

    private static void atualizarListaClientes(ClienteView clienteView, ClienteDAO clienteDAO, ServicoDAO servicoDAO) {
        List<Cliente> clientes = clienteDAO.obterTodosClientes();

        for (Cliente cliente : clientes) {
            Servico servico = servicoDAO.buscarServicoPorId(cliente.getServicoId());
            clienteView.atualizarListaClientes(clientes, servico);
        }
    }
}
