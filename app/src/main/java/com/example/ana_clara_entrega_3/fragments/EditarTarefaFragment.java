package com.example.ana_clara_entrega_3.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.example.ana_clara_entrega_3.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;

public class EditarTarefaFragment extends Fragment {

    private int _id=0;
    private EditText txtNome;
    private EditText txtData;
    private EditText txtHora;
    private EditText txtDescricao;

    private Button btnEditarTarefa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_editar_tarefa, container, false);

        Bundle bundle = getArguments();

        _id = Integer.valueOf(bundle.getString("_ID"));

        String nome = bundle.getString("NOME");
        txtNome = root.findViewById(R.id.editTxtNome);
        txtNome.setText(nome);

        String data = bundle.getString("DATA");
        txtData = root.findViewById(R.id.editTxtData);
        txtData.setText(data);

        String hora = bundle.getString("HORA");
        txtHora = root.findViewById(R.id.editTxtHora);
        txtHora.setText(hora);

        String desc = bundle.getString("DESC");
        txtDescricao = root.findViewById(R.id.editTxtDescricao);
        txtDescricao.setText(desc);

        btnEditarTarefa = root.findViewById(R.id.btnSalvarAlteracaoTarefa);

        btnEditarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterar(_id, root);
                Snackbar snackbar = Snackbar.make(view, "Tarefa: ", Snackbar.LENGTH_LONG);
                snackbar.show();
                Navigation.findNavController(view).navigate(R.id.action_nav_editar_tarefasFragment_to_nav_home);
            }
        });

        return root;
    }

    private void alterar(int _id, View root){

        Tarefa tarefa = new Tarefa();
        tarefa.set_id(_id);
        tarefa.setNome(txtNome.getText().toString().trim());
        tarefa.setDescricao(txtDescricao.getText().toString().trim());
        tarefa.setData(txtData.getText().toString().trim());
        tarefa.setHora(txtHora.getText().toString().trim());
        int linhasAfetadas = new TarefaRepository(root.getContext()).update(tarefa);
        String msg = "Registro alterado com sucesso! ";
        if(linhasAfetadas == 0 ) msg = "Registro não foi alterado! ";
        //mostrando caixa de diálogo de sucesso
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(root.getContext());
        alertDialog.setTitle(R.string.app_name);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
//                // Forçando que o código retorne para a tela de consulta
//                Intent intent = new Intent(getApplicationContext(), ListTarefasActivity.class);
//                startActivity(intent);
//                finish();
            }
        });
        alertDialog.show();
    }

}
