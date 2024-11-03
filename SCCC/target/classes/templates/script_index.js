// Seleciona o formulário e os campos de email e senha
const formulario = document.querySelector("#form-cadastro");
const Iemail = document.querySelector("#email");
const Ipassword = document.querySelector("#password");

// Função de Login
function login() {
    fetch("http://localhost:8080/api/clientes/login", {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        method: "POST",
        body: new URLSearchParams({
            email: Iemail.value,
            password: Ipassword.value
        })
    })
    .then(function(response) {
        if (response.ok) {
            window.location.href = 'BemVindo.html'; // Redireciona para a página de boas-vindas em caso de sucesso
        } else {
            alert("Email ou senha incorretos. Tente novamente.");
        }
    })
    .catch(function(error) {
        console.error("Erro ao fazer login:", error);
    });
}

// Adiciona o evento de submit ao formulário
formulario.addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o comportamento padrão de submissão do formulário

    login(); // Chama a função de login
});
