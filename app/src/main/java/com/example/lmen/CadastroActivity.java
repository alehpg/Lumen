package com.example.lmen;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome, edtEmail, edtSenha, edtCpf;
    private Button btnCadastrar;
    private FirebaseAuth mAuth; // Firebase Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        // Inicializar Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        btnCadastrar.setOnClickListener(v -> registrarUsuario());//chama o metado registrarUsusario
    }

    private void registrarUsuario() {
        // ---------------------- COLETA DADOS ----------------------
        String nome = edtNome.getText().toString().trim();
        String cpf = edtCpf.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();

        // ---------------------- VALIDAÇÕES ----------------------
        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cpf.length() != 11) {
            Toast.makeText(this, "CPF deve ter 11 dígitos!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "E-mail inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (senha.length() < 6) {
            Toast.makeText(this, "A senha deve ter no mínimo 6 caracteres!", Toast.LENGTH_SHORT).show();
            return;
        }

        // ---------------------- CADASTRO NO FIREBASE ----------------------
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Limpa os campos
                        edtNome.setText("");
                        edtCpf.setText("");
                        edtEmail.setText("");
                        edtSenha.setText("");

                        // Encerra a Activity
                        finish();

                    } else {
                        Toast.makeText(this,
                                "Erro: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
