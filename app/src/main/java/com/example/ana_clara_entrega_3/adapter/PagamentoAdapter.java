package com.example.ana_clara_entrega_3.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.example.ana_clara_entrega_3.model.Pagamento;
import com.example.ana_clara_entrega_3.model.Tarefa;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PagamentoAdapter extends RecyclerView.Adapter<PagamentoAdapter.PagamentoViewHolder> {
    List<Pagamento> listaPagamentos = new ArrayList<>();
    Context context;

    public PagamentoAdapter(List<Pagamento> pagamentos) {
        this.listaPagamentos = pagamentos;
    }

    @NonNull
    @Override
    public PagamentoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //chamado para criar as visualizações - somente as primeiras que aparecem para o usuário
        //convertendo o XML em uma visualização
        //cria um objeto do tipo view
        View itemList = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.pagamento_adapter_card_icones, viewGroup, false);
        //se usar adapter_card -> ajustar o ViewHolder para usar Button
        //retorna o itemList que é passado para o construtor da MyViewHolder
        this.context = viewGroup.getContext();

        return new PagamentoViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull PagamentoViewHolder pagamentoViewHolder, int position) {
        //exibe os itens no Recycler
        Pagamento p = listaPagamentos.get(position);
        pagamentoViewHolder.id.setText(String.valueOf(p.get_id()));
        pagamentoViewHolder.nome.setText(p.getNome());
        pagamentoViewHolder.data.setText(p.getData());
        pagamentoViewHolder.valor.setText(p.getValor());
        pagamentoViewHolder.descricao.setText(p.getDescricao());
        pagamentoViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerItem(pagamentoViewHolder, position, p);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("_ID", String.valueOf(listaPagamentos.get(position).get_id()));
        bundle.putString("NOME", listaPagamentos.get(position).getNome());
        bundle.putString("DESC", listaPagamentos.get(position).getDescricao());
        bundle.putString("DATA", listaPagamentos.get(position).getData());
        bundle.putString("VALOR", listaPagamentos.get(position).getValor());
        bundle.putString("KEY", listaPagamentos.get(position).get_id());
        pagamentoViewHolder.btnEdit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_editar_pagamentosFragment, bundle));
    }

    @Override
    public int getItemCount() {
        //retorna a quantidade de itens que será exibida
        return listaPagamentos.size();
    }

    public void removerItem(@NonNull PagamentoViewHolder pagamentoViewHolder, final int position, Pagamento p) {
        new AlertDialog.Builder(context)
                .setTitle("Deletar Pagamento")
                .setMessage("Tem certeza?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pagamentos");
                        reference.child(listaPagamentos.get(position).get_id()).removeValue();
                        listaPagamentos.remove(position);
                        notifyItemRemoved(position);

                    }}).setNegativeButton("Não", null).show();
    }

    public class PagamentoViewHolder extends RecyclerView.ViewHolder{
        //dados do pagamento que serão exibidos no recycler
        TextView id;
        TextView nome;
        TextView data;
        TextView valor;
        TextView descricao;
        ImageButton btnDelete;
        ImageButton btnEdit;

        public PagamentoViewHolder(View itemView){
            super(itemView);
//            passa uma referência para os componentes que estão na interface
            id = itemView.findViewById(R.id.textViewId);
            nome = itemView.findViewById(R.id.textViewNome);
            data = itemView.findViewById(R.id.textViewData);
            valor = itemView.findViewById(R.id.textViewValor);
            descricao = itemView.findViewById(R.id.textViewDescrição);
            btnEdit= itemView.findViewById(R.id.btnEditarPagamento);
            btnDelete = itemView.findViewById(R.id.btnDeletarPagamento);
        }
    }
}
