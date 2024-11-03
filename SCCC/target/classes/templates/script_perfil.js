// Função para obter o ID do cliente da URL
function getClienteIdFromURL() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

// Função para buscar as informações do cliente pelo ID
async function carregarPerfilCliente() {
    const clienteId = getClienteIdFromURL(); // Obtém o ID do cliente da URL
    if (!clienteId) {
        alert("ID do cliente não encontrado na URL.");
        return;
    }

    try {
        // Faz a requisição GET para buscar o cliente pelo ID
        const response = await fetch(`http://localhost:8080/api/clientes/${clienteId}`);
        if (!response.ok) {
            throw new Error("Erro ao buscar o cliente.");
        }

        const cliente = await response.json(); // Converte a resposta para JSON

        // Preenche os campos do formulário com os dados do cliente
        document.querySelector(".perfil-id").textContent = `ID - ${cliente.id}`;
        document.getElementById("nome").value = cliente.nome;
        document.getElementById("email").value = cliente.email;
        document.getElementById("telefone").value = cliente.telefone;
        document.getElementById("cpf").value = cliente.cpf;
        document.getElementById("endereco").value = cliente.endereco;

        // Exibe serviços alocados em um formato de texto, caso exista
        document.getElementById("servicos").value = cliente.servicos ? cliente.servicos.join(", ") : "Nenhum serviço alocado";
        
    } catch (error) {
        console.error("Erro ao carregar o perfil do cliente:", error);
        alert("Erro ao carregar o perfil do cliente.");
    }
}

// Função para redirecionar para a página de alteração com o ID do cliente
function redirecionarParaAlteracao() {
    const clienteId = getClienteIdFromURL();
    if (clienteId) {
        // Redireciona para a página de alteração com o ID do cliente na URL
        window.location.href = `alterar.html?id=${clienteId}`;
    } else {
        alert("ID do cliente não encontrado. Não é possível redirecionar para a alteração.");
    }
}

// Adiciona eventos ao carregar a página
document.addEventListener("DOMContentLoaded", () => {
    carregarPerfilCliente(); // Carrega o perfil do cliente

    // Adiciona o evento de clique ao botão "Alterar Cliente"
    document.getElementById("alterar-cliente-btn").addEventListener("click", redirecionarParaAlteracao);
});
