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

public class ClienteDAO implements AutoCloseable {

    private final Connection conexao;

    public ClienteDAO(String nomeBanco) {
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
            criarTabelaClientes();
            criarTabelaClienteServico();
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou criar o banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar ou criar o banco de dados", e);
        }
    }

    private void criarTabelaClientes() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nome TEXT,"
                + "email TEXT,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "cpf TEXT)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela clientes: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela clientes", e);
        }
    }

    private void criarTabelaClienteServico() {
        String sql = "CREATE TABLE IF NOT EXISTS cliente_servico ("
                + "cliente_id INTEGER,"
                + "servico_id INTEGER,"
                + "PRIMARY KEY (cliente_id, servico_id),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id),"
                + "FOREIGN KEY (servico_id) REFERENCES servicos(id))";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela cliente_servico: " + e.getMessage());
            throw new RuntimeException("Erro ao criar tabela cliente_servico", e);
        }
    }

    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, email, telefone, endereco, cpf) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getTelefone());
            statement.setString(4, cliente.getEndereco());
            statement.setString(5, cliente.getCpf());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente no banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao inserir cliente no banco de dados", e);
        }
    }

    public List<Cliente> obterTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, email, telefone, endereco, cpf FROM clientes";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    clientes.add(new Cliente(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getString("email"),
                            result.getString("telefone"),
                            result.getString("endereco"),
                            result.getString("cpf")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter todos os clientes: " + e.getMessage());
            throw new RuntimeException("Erro ao obter todos os clientes", e);
        }
        return clientes;    }

        public Cliente obterClientePorId(int id) {
            String sql = "SELECT id, nome, email, telefone, endereco, cpf FROM clientes WHERE id = ?";
            try (PreparedStatement statement = conexao.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    if (result.next()) {
                        return new Cliente(
                            result.getInt("id"),
                            result.getString("nome"),
                            result.getString("email"),
                            result.getString("telefone"),
                            result.getString("endereco"),
                            result.getString("cpf")
                        );
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erro ao obter cliente por ID: " + e.getMessage());
                throw new RuntimeException("Erro ao obter cliente por ID", e);
            }
            return null;
        }
        
    public void alterarCliente(int id, String nome, String email, String telefone, String endereco) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE clientes SET ");
        List<Object> parametros = new ArrayList<>();
        if (nome != null) {
            sqlBuilder.append("nome = ? ,");
            parametros.add(nome);
        }
        if (email != null) {
            sqlBuilder.append("email = ? ,");
            parametros.add(email);
        }
        if (telefone != null) {
            sqlBuilder.append("telefone = ? ,");
            parametros.add(telefone);
        }
        if (endereco != null) {
            sqlBuilder.append("endereco = ? ,");
            parametros.add(endereco);
        }
        if (parametros.isEmpty()) {
            throw new RuntimeException("Nenhum campo para atualizar foi fornecido.");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 1);
        sqlBuilder.append(" WHERE id = ?");
        parametros.add(id);
        try (PreparedStatement statement = conexao.prepareStatement(sqlBuilder.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                statement.setObject(i + 1, parametros.get(i));
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao alterar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao alterar cliente", e);
        }
    }

    public void apagarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao apagar cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao apagar cliente", e);
        }
    }

    public void alocarServicoAoCliente(int clienteId, int servicoId) {
        String sql = "INSERT INTO cliente_servico (cliente_id, servico_id) VALUES (?, ?)";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, clienteId);
            statement.setInt(2, servicoId);
            statement.execute();
        } catch (SQLException e) {
            System.err.println("Erro ao alocar serviço ao cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao alocar serviço ao cliente", e);
        }
    }

    public List<Servico> obterServicosDoCliente(int clienteId) {
        List<Servico> servicos = new ArrayList<>();
        String sql = "SELECT s.id, s.nome, s.descricao, s.preco FROM servicos s "
                + "INNER JOIN cliente_servico cs ON s.id = cs.servico_id "
                + "WHERE cs.cliente_id = ?";
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, clienteId);
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
            System.err.println("Erro ao obter serviços do cliente: " + e.getMessage());
            throw new RuntimeException("Erro ao obter serviços do cliente", e);
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