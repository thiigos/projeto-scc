package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientePerfilView {

    private JFrame frame;
    private JLabel lblNome, lblEmail, lblServico;
    private JButton btnEditar, btnDeletar;

    public ClientePerfilView(Cliente cliente, Servico servico) {
        frame = new JFrame("Perfil do Cliente");

        // Centraliza a janela
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        lblNome = new JLabel("Nome: " + cliente.getNome());
        lblEmail = new JLabel("Email: " + cliente.getEmail());
        lblServico = new JLabel("Serviço: " + (servico != null ? servico.getNome() : "Nenhum"));

        JPanel panelDados = new JPanel(new GridLayout(3, 1));
        panelDados.add(lblNome);
        panelDados.add(lblEmail);
        panelDados.add(lblServico);

        JPanel panelButtons = new JPanel();
        panelButtons.add(btnEditar);
        panelButtons.add(btnDeletar);

        frame.setLayout(new BorderLayout());
        frame.add(panelDados, BorderLayout.CENTER);
        frame.add(panelButtons, BorderLayout.SOUTH);
    }

    public void atualizarDadosCliente(Cliente cliente, Servico servico) {
        lblNome.setText("Nome: " + cliente.getNome());
        lblEmail.setText("Email: " + cliente.getEmail());
        lblServico.setText("Serviço: " + (servico != null ? servico.getNome() : "Nenhum"));
    }

    public void mostrarPerfil() {
        frame.setVisible(true);
    }

    // Métodos para adicionar listeners aos botões
    public void editarListener(ActionListener listener) {
        btnEditar.addActionListener(listener);
    }

    public void deletarListener(ActionListener listener) {
        btnDeletar.addActionListener(listener);
    }

    public void mostrarMensagem(String s) {
    }

    public void fecharPerfil() {
    }
}
