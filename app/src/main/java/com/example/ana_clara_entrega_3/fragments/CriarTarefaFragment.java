package com.example.ana_clara_entrega_3.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ana_clara_entrega_3.R;
import com.example.ana_clara_entrega_3.dao.TarefaRepository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CriarTarefaFragment extends Fragment {

    private TextInputEditText txtNome;
    private TextInputLayout layoutTxtNome;
    private EditText txtData;
    private EditText txtHora;
    private TextInputLayout layoutTxtDescricao;
    private TextInputEditText txtDescricao;

    private Button btnCriarTarefa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_criar_tarefa, container, false);

        layoutTxtNome = root.findViewById(R.id.layoutTxtNome);
        layoutTxtDescricao = root.findViewById(R.id.layoutTxtDescricao);

        txtNome = root.findViewById(R.id.editTxtNome);
        txtData = root.findViewById(R.id.editTxtData);
        txtHora = root.findViewById(R.id.editTxtHora);
        txtDescricao = root.findViewById(R.id.editTxtDescricao);

        btnCriarTarefa = root.findViewById(R.id.btnSalvarAlteracaoTarefa);

        btnCriarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validarCampo() == true ) {
                    Log.d("VALIDAR CAMPO", "ANTES CHAMAR O INSERT");
                    TarefaRepository tarefaRepository = new TarefaRepository(root.getContext());
                    String resultado = tarefaRepository.insert(
                            txtNome.getText().toString(),
                            txtDescricao.getText().toString(),
                            txtData.getText().toString(),
                            txtHora.getText().toString()
                    );
                    Toast.makeText(root.getContext(), resultado, Toast.LENGTH_LONG).show();

                    Log.d("RESULTADO", "resultado");

                    Snackbar snackbar = Snackbar.make(view, "Tarefa: ", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Navigation.findNavController(view).navigate(R.id.action_nav_criar_tarefasFragment_to_nav_home);
                }
            }
        });

        return root;
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