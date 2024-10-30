package org.example;

import java.sql.SQLException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO implements AutoCloseable {

    private final Connection conexao;

    public ServicoDAO(String nomeBanco) {
        try {
            Path currentRelativaPath = Paths.get("");
            String projectPath = currentRelativaPath.toAbsolutePath().toString();

            String dbDirectory = projectPath + File.separator + "db";
            File directory = new File(dbDirectory);

            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Diretório criado: " + dbDirectory);
            }

            String url = "jdbc:sqlite:" + dbDirectory + nomeBanco + ".db";
            conexao = DriverManager.getConnection(url);
            criarTabelaServicos();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou criar o banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ou criar o banco de dados", e);
        }
    }

    private void criarTabelaServicos() {
        String sql = "CREATE TABLE IF NOT EXISTS servicos ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "descricao TEXT,"
                + "preco REAL)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.execute();
            System.out.println("Tabela 'serviços' criada/verificada com sucesso");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela de serviços: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela de serviços", e);
        }
    }

    public void inserirServico(Servico servico) {
        String sql = "INSERT INTO servicos (nome, descricao, preco) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, servico.getNome());
            statement.setString(2, servico.getDescricao());
            statement.setDouble(3, servico.getPreco());
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir serviço no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir serviço no banco de dados", e);
        }
    }

    public List<Servico> obterTodosServicos() {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT id, nome, descricao, preco FROM servicos";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    servicos.add(new Servico(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getString("descricao"),
                            result.getDouble("preco")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter todos os serviços: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os serviços", e);
        }
        return servicos;
    }

    @Override
    public void close() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com o banco de dados: " + e.getMessage());
        }
    }
}