package com.trab.java;

import java.util.Objects;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;
    private String cpf;
    private String servico;

    public Cliente() {
    }

    public Cliente(int id, String nome, String email, String telefone, String endereco, String cpf,
                   String servico) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cpf = cpf;
        this.servico = servico;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cliente)) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return id == cliente.id &&
                Objects.equals(nome, cliente.nome) &&
                Objects.equals(email, cliente.email) &&
                Objects.equals(telefone, cliente.telefone) &&
                Objects.equals(endereco, cliente.endereco) &&
                Objects.equals(cpf, cliente.cpf) &&
                Objects.equals(servico, cliente.servico);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, email, telefone, endereco, cpf, servico);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nome='" + getNome() + "'" +
                ", email='" + getEmail() + "'" +
                ", telefone='" + getTelefone() + "'" +
                ", endereco='" + getEndereco() + "'" +
                ", cpf='" + getCpf() + "'" +
                ", servico='" + getServico() + "'" +
                "}";
    }
}
