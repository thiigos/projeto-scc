package com.trab.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ClienteView {

    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JList<String> clienteList;
    private JButton btnVerPerfil, btnAdicionar, btnEditar, btnDeletar;

    public ClienteView() {
        frame = new JFrame("Sistema de Cadastro de Clientes");

        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        listModel = new DefaultListModel<>();
        clienteList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(clienteList);

        btnAdicionar = new JButton("Adicionar Cliente");
        btnVerPerfil = new JButton("Ver Perfil");
        btnEditar = new JButton("Editar Cliente");
        btnDeletar = new JButton("Deletar Cliente");

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnAdicionar);
        panelButtons.add(btnVerPerfil);
        panelButtons.add(btnEditar);
        panelButtons.add(btnDeletar);

        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelButtons, BorderLayout.SOUTH);
    }

    public void mostrarTela() {
        frame.setVisible(true);
    }

    public void atualizarListaClientes(List<Cliente> clientes) {
        listModel.clear();
        for (Cliente cliente : clientes) {
            listModel.addElement(cliente.getId() + " - " + cliente.getNome() +
                    " (Serviço: " + cliente.getServico() + ")");
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

    public Cliente getClienteForm(String titulo) {
        return getClienteForm(titulo, null);
    }

    public Cliente getClienteForm(String titulo, Cliente clienteExistente) {
        JTextField nomeField = new JTextField(clienteExistente != null ? clienteExistente.getNome() : "");
        JTextField emailField = new JTextField(clienteExistente != null ? clienteExistente.getEmail() : "");
        JTextField telefoneField = new JTextField(clienteExistente != null ? clienteExistente.getTelefone() : "");
        JTextField enderecoField = new JTextField(clienteExistente != null ? clienteExistente.getEndereco() : "");
        JTextField cpfField = new JTextField(clienteExistente != null ? clienteExistente.getCpf() : "");

        String[] servicos = {"Legalizações", "Assessoria Comercial", "Consultoria"};
        JComboBox<String> servicoCombo = new JComboBox<>(servicos);

        if (clienteExistente != null) {
            servicoCombo.setSelectedItem(clienteExistente.getServico());
        }

        Object[] inputFields = {
                "Nome completo:", nomeField,
                "Email:", emailField,
                "Telefone:", telefoneField,
                "Endereço:", enderecoField,
                "CPF:", cpfField,
                "Serviços:", servicoCombo,
        };

        int option = JOptionPane.showConfirmDialog(frame, inputFields, titulo, JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String email = emailField.getText();
            String telefone = telefoneField.getText();
            String endereco = enderecoField.getText();
            String cpf = cpfField.getText();

            if (clienteExistente != null) {
                clienteExistente.setNome(nome);
                clienteExistente.setEmail(email);
                clienteExistente.setTelefone(telefone);
                clienteExistente.setEndereco(endereco);
                clienteExistente.setCpf(cpf);
                clienteExistente.setServico((String) servicoCombo.getSelectedItem());
                return clienteExistente;
            } else {
                return new Cliente(0, nome, email, telefone, endereco, cpf,
                        (String) servicoCombo.getSelectedItem());
            }
        }
        return null;
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(frame, mensagem);
    }

    public void verPerfilListener(ActionListener listener) {
        btnVerPerfil.addActionListener(listener);
    }

    public void adicionarListener(ActionListener listener) {
        btnAdicionar.addActionListener(listener);
    }

    public void editarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }

    public void deletarListener(ActionListener listener) {
        btnDeletar.addActionListener(listener);
    }

    public Component getFrame() {
        return frame;
    }
}
