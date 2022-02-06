package com.example.ana_clara_entrega_3.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.model.Pagamento;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CriarPagamentoFragment extends Fragment {
    private TextInputEditText txtNome;
    private TextInputLayout layoutTxtNome;
    private EditText txtData;
    private EditText txtValor;
    private TextInputLayout layoutTxtDescricao;
    private TextInputEditText txtDescricao;

    private Button btnCriarPagamento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_criar_pagamento, container, false);

        layoutTxtNome = root.findViewById(R.id.layoutTxtNome);
        layoutTxtDescricao = root.findViewById(R.id.layoutTxtDescricao);

        txtNome = root.findViewById(R.id.editTxtNome);
        txtData = root.findViewById(R.id.editTxtData);
        txtValor = root.findViewById(R.id.editTxtValor);
        txtDescricao = root.findViewById(R.id.editTxtDescricao);

        btnCriarPagamento = root.findViewById(R.id.btnSalvarAlteracaoPagamento);

        btnCriarPagamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampo()) {
                    Log.d("VALIDAR CAMPO", "ANTES CHAMAR O INSERT");

                    String nome = txtNome.getText().toString();
                    String descricao = txtDescricao.getText().toString();
                    String data = txtData.getText().toString();
                    String valor = txtValor.getText().toString();

                    cadPagamento(view, nome, descricao, data, valor);

                }
            }
        });
        return root;
    }

    public void cadPagamento(View view, String nome, String descricao, String data, String valor){

        Pagamento pagamento = new Pagamento(nome, descricao, data, valor);
        DatabaseReference pagamentos = FirebaseDatabase.getInstance().getReference().child("pagamentos");
        //gera um identificador único para cada pagamento
        //salva o pagamento na base de dados
        pagamentos.push().setValue(pagamento).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(view.getContext(), "Pagamento criado com sucesso", Toast.LENGTH_LONG).show();
                Navigation.findNavController(view).navigate(R.id.action_nav_criar_pagamentosFragment_to_nav_home);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "resultado", Toast.LENGTH_LONG).show();
                        Snackbar.make(view, "Erro ao cadastrar pagamento!", Snackbar.LENGTH_LONG)
                                .setTextColor(Color.RED).show();
                    }
                });
    }

    private boolean validarCampo(){
        if(txtNome.getText().toString().isEmpty()){
            layoutTxtNome.setErrorEnabled(true);
            layoutTxtNome.setError("Nome é obrigatório");
            return false;
        }else{
            layoutTxtNome.setErrorEnabled(false);
        }
        if(txtDescricao.getText().toString().isEmpty()){
            layoutTxtDescricao.setErrorEnabled(true);
            layoutTxtDescricao.setError("Descrição é obrigatória");
            return false;
        }else{
            layoutTxtDescricao.setErrorEnabled(false);
        }
        Log.d("validacao", "saiu no validar");
        return true;
    }
}