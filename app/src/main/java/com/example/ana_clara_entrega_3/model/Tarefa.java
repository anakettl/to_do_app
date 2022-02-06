package com.example.ana_clara_entrega_3.model;

import com.example.ana_clara_entrega_3.dao.TarefaRepository;

import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private int _id;
    private String nome;
    private String descricao;
    private String data;
    private String hora;

    public Tarefa(){}
    public Tarefa(int id, String nome, String descricao, String data, String hora) {
        this._id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.hora = hora;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                '}';
    }
}
