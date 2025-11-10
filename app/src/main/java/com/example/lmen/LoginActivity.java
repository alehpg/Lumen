package com.example.lmen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password ;
    private Button Entrar;
    private Button Cadastro;
    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Entrar = findViewById(R.id.entrar);
        Cadastro = findViewById(R.id.Cadastro);

        Entrar.setOnClickListener(v -> loginUsuario());

        Cadastro.setOnClickListener(v -> registrarUsuario());
    }

    /*Verificação do login*/
    private void loginUsuario() {
        String email = Email.getText().toString();// pega o email
        String password = Password.getText().toString();// pdega a senha

        /*verifica se o campo foi preenchido*/
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        /*VERIFICAÇÃO DO FIRE BASE*/
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(this, "Bem-vindo(a) " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Falha no login: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void registrarUsuario() {
        String email = Email.getText().toString();
        String Senha = Password.getText().toString();

        if (email.isEmpty() || Senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, Senha)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Erro ao registrar: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    
}}

