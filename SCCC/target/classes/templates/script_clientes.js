// Função para listar todos os clientes
async function listarClientes() {
    try {
        // Faz uma requisição GET para o endpoint que retorna todos os clientes
        const response = await fetch("http://localhost:8080/api/clientes/listar");
        const clientes = await response.json(); // Converte a resposta para JSON

        // Seleciona o contêiner onde os clientes serão exibidos
        const listaClientes = document.querySelector(".clientes-lista");
        listaClientes.innerHTML = ""; // Limpa a lista atual

        // Itera sobre cada cliente e cria o elemento HTML correspondente
        clientes.forEach(cliente => {
            const clienteItem = document.createElement("a");
            clienteItem.href = `perfil.html?id=${cliente.id}`; // Link para a página de perfil do cliente
            clienteItem.className = "cliente-item";
            clienteItem.innerHTML = `<span>${cliente.nome}</span> <span>ID ${cliente.id}</span>`;
            listaClientes.appendChild(clienteItem);
        });
    } catch (error) {
        console.error("Erro ao buscar clientes:", error);
    }
}

// Chama a função ao carregar a página
document.addEventListener("DOMContentLoaded", listarClientes);