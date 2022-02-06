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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.example.ana_clara_entrega_3.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {
    List<Tarefa> listaTarefas = new ArrayList<>();
    Context context;

    public TarefaAdapter(List<Tarefa> tarefas) {
        this.listaTarefas = tarefas;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //chamado para criar as visualizações - somente as primeiras que aparecem para o usuário
        //convertendo o XML em uma visualização
        //cria um objeto do tipo view
        View itemList = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tarefa_adapter_card_icones, viewGroup, false);
        //se usar adapter_card -> ajustar o ViewHolder para usar Button
        //retorna o itemList que é passado para o construtor da MyViewHolder
        this.context = viewGroup.getContext();

        return new TarefaViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder tarefaViewHolder, int position) {
        //exibe os itens no Recycler
        Tarefa t = listaTarefas.get(position);
        tarefaViewHolder.id.setText(String.valueOf(t.get_id()));
        tarefaViewHolder.nome.setText(t.getNome());
        tarefaViewHolder.data.setText(t.getData());
        tarefaViewHolder.hora.setText(t.getHora());
        tarefaViewHolder.descricao.setText(t.getDescricao());
        tarefaViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removerItem(tarefaViewHolder, position, t);
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("_ID", String.valueOf(listaTarefas.get(position).get_id()));
        bundle.putString("NOME", listaTarefas.get(position).getNome());
        bundle.putString("DESC", listaTarefas.get(position).getDescricao());
        bundle.putString("DATA", listaTarefas.get(position).getData());
        bundle.putString("HORA", listaTarefas.get(position).getHora());
        tarefaViewHolder.btnEdit.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_editar_tarefasFragment, bundle));
    }

    @Override
    public int getItemCount() {
        //retorna a quantidade de itens que será exibida
        return listaTarefas.size();
    }

    public void removerItem(@NonNull TarefaViewHolder tarefaViewHolder, final int position, Tarefa t) {
        new AlertDialog.Builder(context)
                .setTitle("Deletar Tarefa")
                .setMessage("Tem certeza?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TarefaRepository tarefaRepository = new TarefaRepository(tarefaViewHolder.itemView.getContext());
                        listaTarefas.remove(position);
                        notifyItemRemoved(position);

                        String mensagem = "Registro excluído com sucesso!";
                        Integer retorno = tarefaRepository.delete(t.get_id());

                        Log.d("POSIÇÃO DA TAREFA", String.valueOf(position));
                        Log.d("ID PASSADO PRO METODO", String.valueOf(t.get_id()));
                        Log.d("TAREFA EXCLUIDA:", t.toString());
                        if(retorno == 0) {
                            mensagem = "Erro ao excluir registro!";
                        }
                        Log.d("TAREFA ADAPTER", mensagem);

                    }}).setNegativeButton("Não", null).show();
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder{
        //dados da pessoa que serão exibidos no recycler
        TextView id;
        TextView nome;
        TextView data;
        TextView hora;
        TextView descricao;
        ImageButton btnDelete;
        ImageButton btnEdit;
        //se usar adapter_card -> ajustar o ViewHolder para usar Button
        //Button btnDelete;
        //Button btnEdit;
        public TarefaViewHolder(View itemView){
            super(itemView);
//            passa uma referência para os componentes que estão na interface
            id = itemView.findViewById(R.id.textViewId);
            nome = itemView.findViewById(R.id.textViewNome);
            data = itemView.findViewById(R.id.textViewData);
            hora = itemView.findViewById(R.id.textViewHora);
            descricao = itemView.findViewById(R.id.textViewDescrição);
            btnEdit= itemView.findViewById(R.id.btnEditarTarefa);
            btnDelete = itemView.findViewById(R.id.btnDeletarTarefa);
        }
    }
}
