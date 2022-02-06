package com.example.ana_clara_entrega_3.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.example.ana_clara_entrega_3.model.Pagamento;
import com.example.ana_clara_entrega_3.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarPagamentoFragment extends Fragment {

    private TextInputEditText txtNome;
    private TextInputEditText txtDescricao;
    private EditText txtdata;
    private EditText txtvalor;
    private Button btnEditar;
    private String key;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_editar_pagamento, container, false);
        Bundle bundle = getArguments();
        txtNome = root.findViewById(R.id.editTxtNome);
        txtNome.setText(bundle.getString("NOME"));
        txtDescricao = root.findViewById(R.id.editTxtDescricao);
        txtDescricao.setText(bundle.getString("DESCRICAO"));
        txtdata = root.findViewById(R.id.editTxtData);
        txtdata.setText(bundle.getString("DATA"));
        txtvalor = root.findViewById(R.id.editTxtValor);
        txtvalor.setText(bundle.getString("VALOR"));
        btnEditar = root.findViewById(R.id.btnSalvarAlteracaoPagamento);
        key = bundle.getString("KEY");
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarItem();
            }
        });
        return root;
    }
    private void editarItem() {
        new AlertDialog.Builder(getContext())
                .setTitle("Editando pagamento")
                .setMessage("Tem certeza que deseja editar esse pagamento?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("IMPRIME A KEY", key);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pagamentos").child(key);
                        Pagamento p = new Pagamento(txtNome.getText().toString(), txtDescricao.getText().toString(), txtdata.getText().toString(), txtvalor.getText().toString());
                        reference.setValue(p);
                        Snackbar.make(getView(), "item editado!!!", Snackbar.LENGTH_LONG).show();
                        Navigation.findNavController(getView()).navigate(R.id.action_nav_editar_pagamentosFragment_to_nav_home);
                    }
                }).setNegativeButton("Não", null).show();
    }

}
