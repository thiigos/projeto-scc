// Seleciona o formulário e os campos de entrada
const formulario = document.querySelector("#form-cadastro");
const Inome = document.querySelector("#nome");
const Iemail = document.querySelector("#email");
const Itelefone = document.querySelector("#telefone");
const Iendereco = document.querySelector("#endereco");
const Icpf = document.querySelector("#cpf");

// Função para alterar o cliente
function alterarCliente(id) {
    fetch(`http://localhost:8080/api/clientes/alterar/${id}`, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "PUT",
        body: JSON.stringify({
            nome: Inome.value,
            email: Iemail.value,
            telefone: Itelefone.value,
            endereco: Iendereco.value,
            cpf: Icpf.value
        })
    })
    .then(function(response) {
        if (response.ok) {
            alert("Cliente alterado com sucesso!");
            limpar(); // Limpa o formulário após a alteração
        } else {
            alert("Erro ao alterar cliente. Tente novamente.");
        }
    })
    .catch(function(error) {
        console.error("Erro ao fazer a alteração:", error);
    });
}

// Função para limpar o formulário
function limpar() {
    Inome.value = "";
    Iemail.value = "";
    Itelefone.value = "";
    Iendereco.value = "";
    Icpf.value = "";
}

// Adiciona o evento de submissão ao formulário
formulario.addEventListener('submit', function(event) {
    event.preventDefault(); // Evita o comportamento padrão do formulário

    const clienteId = 1; // Defina aqui o ID do cliente que você deseja alterar
    alterarCliente(clienteId); // Chama a função de alteração com o ID do cliente
});
