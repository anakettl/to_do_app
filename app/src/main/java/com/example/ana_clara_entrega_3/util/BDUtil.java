package com.example.ana_clara_entrega_3.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDUtil extends SQLiteOpenHelper {
    private static final String BASE_DE_DADOS = "TO_DO";
    private static final int VERSAO = 1;

    public BDUtil(Context context){
        super(context,BASE_DE_DADOS,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("BDUTIL", "DENTRO DE ON CREATE");
        StringBuilder criarTabela = new StringBuilder();
        criarTabela.append(" CREATE TABLE TAREFA (");
        criarTabela.append(" _ID   INTEGER PRIMARY KEY AUTOINCREMENT, ");
        criarTabela.append(" NOME  TEXT    NOT NULL,");
        criarTabela.append(" DESCRICAO   TEXT    NOT NULL,");
        criarTabela.append(" DATA TEXT    NOT NULL,");
        criarTabela.append(" HORA TEXT    NOT NULL)");
        db.execSQL(criarTabela.toString());
    }

    /*Método abaixo é executado quando troca a versão do BD*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("BDUTIL", "DENTRO DE ON UPDATE");
        db.execSQL("DROP TABLE IF EXISTS TAREFA");
        onCreate(db);

    }

    /*Método usado para obter a conexão com o BD*/
    public SQLiteDatabase getConexao(){
        Log.d("BDUTIL", "ANTES DE CHEGAR EM getWritableDatabase");
        return this.getWritableDatabase();
    }

}
