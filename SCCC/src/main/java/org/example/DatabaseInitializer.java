package org.example;

public class DatabaseInitializer {

    public static void inicializarServicos(ServicoDAO servicoDAO) {

        if (servicoDAO.obterTodosServicos().isEmpty()) {
            System.out.println("Inserindo serviços pré-definidos no banco de dados...");


            Servico servico1 = new Servico(0, "Consultoria Básica", "Consultoria para pequenas empresas", 500.00);
            Servico servico2 = new Servico(1, "Consultoria Completa", "Consultoria para grandes empresas", 1500.00);
            Servico servico3 = new Servico(2, "Legalização de Empresas", "Serviço completo de legalização", 2000.00);


            servicoDAO.inserirServico(servico1);
            servicoDAO.inserirServico(servico2);
            servicoDAO.inserirServico(servico3);

            System.out.println("Serviços pré-definidos inseridos com sucesso.");
        } else {
            System.out.println("Serviços já estão cadastrados no banco.");
        }
    }
}