package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteView {

    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> clienteList;
    private JButton btnAdicionar, btnVerPerfil;

    public ClienteView() {
        frame = new JFrame("Gerenciamento de Clientes");

        // Centraliza a janela
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Layout e componentes
        listModel = new DefaultListModel<>();
        clienteList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(clienteList);

        btnAdicionar = new JButton("Adicionar Cliente");
        btnVerPerfil = new JButton("Ver Perfil");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAdicionar);
        panelButtons.add(btnVerPerfil);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelButtons, BorderLayout.SOUTH);
    }

    public void mostrarTela() {
        frame.setVisible(true);
    }

    public void atualizarListaClientes(List<Cliente> clientes, Servico servico) {
        listModel.clear();
        for (Cliente cliente : clientes) {
            String servicoNome = (servico != null) ? servico.getNome() : "Nenhum serviço";
            listModel.addElement(cliente.getId() + " - " + cliente.getNome() + " | Serviço: " + servicoNome);
        }
    }

    public int getClienteSelecionado() {
        int selectedIndex = clienteList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedValue = listModel.get(selectedIndex);
            return Integer.parseInt(selectedValue.split(" - ")[0]);
        }
        return -1;
    }

    // Métodos para adicionar listeners aos botões
    public void adicionarListener(ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }

    public void verPerfilListener(ActionListener listener) {
        btnVerPerfil.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(frame, mensagem);
    }

    public Cliente getClienteForm(String editarCliente, Cliente clienteSelecionado) {
    }
}
