const formulario = document.querySelector("form");
const Inome = document.querySelector("#nome");
const Iemail = document.querySelector("#email");
const Itelefone = document.querySelector("#telefone");
const Iendereco = document.querySelector("#endereco");
const Icpf = document.querySelector("#cpf");
const Iservico = document.querySelector("#servicos");

function cadastrar() {
    fetch("http://localhost:8080/cadastrar",
        {
            headers: {
                'accept': 'application/json',
                'content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                nome: Inome.value,
                email: Iemail.value,
                telefone: Itelefone.value,
                endereco: Iendereco.value,
                cpf: Icpf.value,
                servicos: Iservico.value})
        })
        .then(function(res) {console.log(res) })
        .catch(function(res) {console.log(res) })
};

function limpar() {
    Inome.value = "";
    Iemail.value = "";
    Itelefone.value = "";
    Iendereco.value = "";
    Icpf.value = "";
    Iservico.value = "";
}

formulario.addEventListener('submit', function(event){
    event.preventDefault();

    cadastrar();
    limpar();
});