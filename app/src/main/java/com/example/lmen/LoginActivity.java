package com.example.lmen;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
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
    private Button verSenha;
    private FirebaseAuth mAuth;
    boolean senhaVisivel = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);

        mAuth = FirebaseAuth.getInstance();// inicializa o firebase auth
        Email = findViewById(R.id.Email);// referencia o campo de email
        Password = findViewById(R.id.Password);// referencia o campo de senha
        Entrar = findViewById(R.id.entrar);// referencia o botao entrar
        Cadastro = findViewById(R.id.Cadastro);// referencia o botao cadastro
        verSenha = findViewById(R.id.verSenha);// referencia o botao ver senha

        Entrar.setOnClickListener(v -> loginUsuario());// chama o metodo de login ao clicar no botao entrar
        Cadastro.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, CadastroActivity.class));// inicia a activity de cadastro
        });// chama a tela de cadastro ao clicar no botao cadastro

        /*BLOCO SHOWPASSWORD*/
        verSenha = findViewById(R.id.verSenha);
        verSenha.setOnClickListener(v -> {
            if (senhaVisivel){
                //se a senha estiver visivel, oculta a senha
                Password.setTransformationMethod(PasswordTransformationMethod.getInstance());// oculta a senha
                senhaVisivel = false;// atualiza o estado da visibilidade da senha
            } else {
                //se a senha estiver oculta, mostra a senha
                Password.setTransformationMethod(null);// mostra a senha
                senhaVisivel = true;// atualiza o estado da visibilidade da senha
            }
        });

    }


    /*VERIFICAÇÃO DO USUARIO*/
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








