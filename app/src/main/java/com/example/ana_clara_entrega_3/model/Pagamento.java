package com.example.ana_clara_entrega_3.model;

public class Pagamento {
    private String _id;
    private String nome;
    private String descricao;
    private String data;
    private String valor;

    public Pagamento(String _id, String nome, String descricao, String data, String valor) {
        this._id = _id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public Pagamento(String nome, String descricao, String data, String valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
    }

    public Pagamento() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
