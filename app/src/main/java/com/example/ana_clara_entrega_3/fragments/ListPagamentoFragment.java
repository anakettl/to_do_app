package com.example.ana_clara_entrega_3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.adapter.PagamentoAdapter;
import com.example.ana_clara_entrega_3.adapter.TarefaAdapter;
import com.example.ana_clara_entrega_3.model.Pagamento;
import com.example.ana_clara_entrega_3.model.Tarefa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListPagamentoFragment extends Fragment {

    private Button btnCriarPagamento;

    View root;
    RecyclerView recyclerView;
    List<Pagamento> listaPagamentos = new ArrayList<>();
    PagamentoAdapter pagamentoAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_list_pagamento, container, false);
        recyclerView = root.findViewById(R.id.recyclerViewPagamento);
        carregaPagamentos();

//
//        //configurar o gerenciador de layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//
//        //definindo o layout do recycler
        recyclerView.setLayoutManager(layoutManager);

        btnCriarPagamento = root.findViewById(R.id.btnCriarPagamento);
        btnCriarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_bottomnavpagamento_to_nav_criar_pagamentosFragment);
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    private void carregaPagamentos(){
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pagamentos");
        listaPagamentos = new ArrayList<>();

        //associar os eventos ao nó pessoas para poder buscar os dados
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            //é chamado sempre que consegue recuperar algum dado
            //DataSnapshot é o retorno do Firebase => resultado da consulta
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    //para buscar todos os nós filhos de pagamento
//                    Pagamento pagamento = dataSnapshot.getValue(Pagamento.class);
                    Log.d("A CHAVE", ds.getKey());
                    Log.d("PAGAMENTO", ds.toString());

                    String id = ds.getKey();
                    String data = ds.child("data").getValue(String.class);
                    String valor = ds.child("valor").getValue(String.class);
                    String nome = ds.child("nome").getValue(String.class);
                    String descricao = ds.child("descricao").getValue(String.class);
//
                    Pagamento pagamento = new Pagamento(id, nome, descricao, data, valor);
                    Log.d("PAGAMENTO_ID", id);

//                    Pagamento pagamento = ds.getValue(Pagamento.class);
//                    pagamento.set_id(Long.parseLong(ds.getKey()));
                    listaPagamentos.add(pagamento);
                }
                //configurar o adapter - que formata que o layout de cada item do recycler
                pagamentoAdapter = new PagamentoAdapter(listaPagamentos);
                recyclerView.setAdapter(pagamentoAdapter);
                recyclerView.setHasFixedSize(true);
                reference.removeEventListener(this);
            }
            @Override
            //chamado quando a requisição é cancelada
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}