package com.example.lmen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        mAuth = FirebaseAuth.getInstance();// inicializa o firebase auth
        Email = findViewById(R.id.Email);// referencia o campo de email
        Password = findViewById(R.id.Password);// referencia o campo de senha
        Entrar = findViewById(R.id.entrar);// referencia o botao entrar
        Cadastro = findViewById(R.id.Cadastro);// referencia o botao cadastro

        Entrar.setOnClickListener(v -> loginUsuario());// chama o metodo de login ao clicar no botao entrar
        Cadastro.setOnClickListener(v -> {

            startActivity(new Intent(LoginActivity.this, CadastroActivity.class));// inicia a activity de cadastro
        });// chama a tela de cadastro ao clicar no botao cadastro

    }


    /*Verificação do login*/
    private void loginUsuario() {
        String email = Email.getText().toString();// pega o email
        String password = Password.getText().toString();// pdega a senha

        /*verifica se o campo foi preenchido*/
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;// sai do metodo se algum campo estiver vazio
        }
        /*VERIFICAÇÃO DO FIRE BASE*/
        mAuth.signInWithEmailAndPassword(email, password)// realiza o login com email e senha
                .addOnCompleteListener(this, task -> {// adiciona um listener para verificar se o login foi bem sucedido
                    if (task.isSuccessful()) {// se o login for bem sucedido
                        FirebaseUser user = mAuth.getCurrentUser();// pega o usuario atual
                        Toast.makeText(this, "Bem-vindo(a) " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));// inicia a activity principal
                        finish();// finaliza a activity de login
                    } else {
                        Toast.makeText(this, "Falha no login: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });





    }






}

