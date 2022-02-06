package com.example.ana_clara_entrega_3.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.adapter.TarefaAdapter;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.example.ana_clara_entrega_3.model.Tarefa;

import java.util.ArrayList;
import java.util.List;

public class ListTarefaFragment extends Fragment {
    private Button btnCriarTarefa;
    public ArrayList<Tarefa> ListTarefas = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_list_tarefa, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewTarefa);
        //configurar o adapter - que formata que o layout de cada item do recycler

        TarefaAdapter tarefaAdapter = new TarefaAdapter(getAll());
        recyclerView.setAdapter(tarefaAdapter);
        //linha de código usada para otimizar o recycler
        recyclerView.setHasFixedSize(true);

        //configurar o gerenciador de layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        //definindo o layout do recycler
        recyclerView.setLayoutManager(layoutManager);


        btnCriarTarefa = root.findViewById(R.id.btnCriarTarefa);
        btnCriarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_listar_tarefasFragment_to_nav_criar_tarefasFragment);
            }
        });

        // Inflate the layout for this fragment
        return root;

    }

    protected List<Tarefa> getAll(){
        TarefaRepository tarefaRepository = new TarefaRepository(getContext());
        List<Tarefa> tarefas = tarefaRepository.getAll();

        return tarefas;
    }

}