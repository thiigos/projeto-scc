package com.trab.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexao;

    public ClienteDAO() {
        try {
            String url = "jdbc:sqlite:cliente.db";
            conexao = DriverManager.getConnection(url);
            criarTabelaClientes();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private void criarTabelaClientes() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "cpf TEXT NOT NULL, " +
                "servico TEXT)";
        try (Statement stmt = conexao.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone, endereco, cpf, servico) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setString(5, cliente.getCpf());
            pstmt.setString(6, cliente.getServico());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }
    }

    public void alterarCliente(int id, String nome, String email, String telefone, String endereco, String cpf,
                               String servico) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ?, endereco = ?, cpf = ?, servico = ? WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.setString(3, telefone);
            pstmt.setString(4, endereco);
            pstmt.setString(5, cpf);
            pstmt.setString(6, servico);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    public void apagarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    public List<Cliente> obterTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone, endereco, cpf, servico FROM clientes";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setServico(rs.getString("servico"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter clientes: " + e.getMessage());
        }
        return clientes;
    }

    public Cliente obterClientePorId(int id) {
        String sql = "SELECT id, nome, email, telefone, endereco, cpf, servico FROM clientes WHERE id = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("telefone"),
                            rs.getString("endereco"),
                            rs.getString("cpf"),
                            rs.getString("servico"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter cliente por ID: " + e.getMessage());
        }
        return null;
    }
}
