package com.example.ana_clara_entrega_3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ana_clara_entrega_3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadUsuario extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText edtEmail;
    private TextInputEditText edtSenha;
    private TextInputEditText edtConfSenha;
    private Button btnCancelar;
    private Button btnCadastrar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_usuario);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfSenha = findViewById(R.id.edtConfSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // se os dados do usuario nao estiverem em branco
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("") && !edtConfSenha.getText().toString().equals("")){
                    Log.d("CAD USUARIO", "Verificou que os campos nao sao vazios");
                    if( edtSenha.getText().toString().equals(edtConfSenha.getText().toString())) {
                        Log.d("CAD USUARIO", "Verificou que a senha e a confirmação sao iguais");
                        // cria um usuario com email e senha
                        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(), edtSenha.getText().toString()).
                                addOnCompleteListener(CadUsuario.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Log.d("CAD USUARIO", "Entrou no metodo de on complete");
                                        if(task.isSuccessful()) {
                                            Log.d("CAD USUARIO", "Tudo certo no cadastro");
                                            // chama a tela de login atraves da tela atual para fazer a autenticacao
                                            Intent intent = new Intent(CadUsuario.this, LoginActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(CadUsuario.this, "sucesso!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Log.d("CAD USUARIO", "Deu ruim  no cadastro");
                                            Toast.makeText(CadUsuario.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    }else{
                        Toast.makeText(CadUsuario.this, "Senha e confirmação de senha devem ser iguais!", Toast.LENGTH_SHORT).show();
                    }
                }else
                    Toast.makeText(CadUsuario.this, "Informe os dados para o cadastro!", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // desvincula o usuario da aplicacao atual
                mAuth.signOut();
                Intent intent = new Intent(CadUsuario.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }

}
