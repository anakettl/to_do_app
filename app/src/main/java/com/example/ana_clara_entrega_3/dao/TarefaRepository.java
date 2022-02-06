package com.example.ana_clara_entrega_3.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.ana_clara_entrega_3.model.Tarefa;
import com.example.ana_clara_entrega_3.util.BDUtil;

import java.util.ArrayList;
import java.util.List;

public class TarefaRepository {
    private BDUtil bdUtil;

    public TarefaRepository(Context context){
        bdUtil =  new BDUtil(context);
    }

    public String insert(String nome, String descricao, String data, String hora){
        Log.d("TAREFA REPOSITORY", "Inicio do metodo insert");
        ContentValues valores = new ContentValues();
        valores.put("NOME", nome);
        valores.put("DESCRICAO", descricao);
        valores.put("DATA", data);
        valores.put("HORA", hora);

        Log.d("TAREFA REPOSITORY", "Antes de abrir a conexao com o banco");
        long resultado = bdUtil.getConexao().insert("TAREFA", null, valores);
        Log.d("TAREFA REPOSITORY", "Abriu a conexao e tentou inserir");
        if (resultado ==-1) {
            bdUtil.close();
            Log.d("TAREFA REPOSITORY", "ERRO AO INSERIR");
            return "Erro ao inserir registro";
        }
        Log.d("TAREFA REPOSITORY", "INSERIU COM SUCESSO!!!!!!!!!!!!!!!!!!!!!!!");
        return "Registro Inserido com sucesso";
    }

    @SuppressLint("Range")
    public List<Tarefa> getAll(){
        List<Tarefa> tarefas = new ArrayList<>();
        // monta a consulta
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append("SELECT _ID, NOME, DESCRICAO, DATA, HORA ");
        stringBuilderQuery.append("FROM  TAREFA ");
        stringBuilderQuery.append("ORDER BY NOME");
        //consulta os registros que estão no BD
        Cursor cursor = bdUtil.getConexao().rawQuery(stringBuilderQuery.toString(), null);
        //aponta cursos para o primeiro registro
        cursor.moveToFirst();
        Tarefa tarefa = null;
        //Percorre os registros até atingir o fim da lista de registros
        while (!cursor.isAfterLast()){
            // Cria objetos do tipo tarefa
            tarefa =  new Tarefa();
            //adiciona os dados no objeto
            tarefa.set_id(cursor.getInt(cursor.getColumnIndex("_ID")));
            tarefa.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            tarefa.setDescricao(cursor.getString(cursor.getColumnIndex("DESCRICAO")));
            tarefa.setData(cursor.getString(cursor.getColumnIndex("DATA")));
            tarefa.setHora(cursor.getString(cursor.getColumnIndex("HORA")));
            //adiciona o objeto na lista
            tarefas.add(tarefa);
            //aponta para o próximo registro
            cursor.moveToNext();
        }
        bdUtil.close();
        //retorna a lista de objetos
        return tarefas;
    }

    public Integer delete(int codigo){
        Integer linhasAfetadas = bdUtil.getConexao().delete("TAREFA","_id = ?", new String[]{Integer.toString(codigo)});
        bdUtil.close();
        return linhasAfetadas;
    }

    public int update(Tarefa tarefa){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("NOME",       tarefa.getNome());
        contentValues.put("DESCRICAO",   tarefa.getDescricao());
        contentValues.put("DATA",       tarefa.getData());
        contentValues.put("HORA",       tarefa.getHora());
        //atualiza o objeto usando a chave
        int retorno = bdUtil.getConexao().update("TAREFA", contentValues, "_id = ?", new String[]{Integer.toString(tarefa.get_id())});
        bdUtil.close();
        return retorno;
    }
}
