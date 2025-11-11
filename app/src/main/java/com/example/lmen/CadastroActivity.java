package com.example.lmen;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lmen.R;

public class CadastroActivity extends AppCompatActivity {

    EditText edtNome, edtEmail, edtSenha,edtCpf;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtCpf = findViewById(R.id.edtCpf);
        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);



        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = edtNome.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                String cpf = edtCpf.getText().toString().trim();



                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(CadastroActivity.this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
                } else if (senha.length() < 6) {
                    Toast.makeText(CadastroActivity.this, "A senha deve ter no mínimo 6 caracteres!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();


                    // Limpa os campos após o cadastro
                    edtNome.setText("");
                    edtEmail.setText("");
                    edtSenha.setText("");
                }
                if (cpf.length() != 11) {
                    Toast.makeText(CadastroActivity.this, "CPF deve ter 11 dígitos!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

}
