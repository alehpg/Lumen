package com.example.lmen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class DetalheLancamento extends AppCompatActivity {

    EditText edtValor, edtDescricao;
    RadioButton rbReceita, rbDispensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lancamento_detalhe);

        edtValor = findViewById(R.id.edtValor);
        edtDescricao = findViewById(R.id.edtDescricao);

        rbReceita = findViewById(R.id.rbReceita);
        rbDispensa = findViewById(R.id.rbDespesa);

        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(v -> {

            double valor = Double.parseDouble(edtValor.getText().toString());
            String desc = edtDescricao.getText().toString();

            String tipo;
            if (rbDispensa.isChecked()) {
                tipo = "dispensa";   // SAÍDA
            } else {
                tipo = "receita";    // ENTRADA
            }

            Intent intent = new Intent();
            intent.putExtra("valor", valor);
            intent.putExtra("descricao", desc);
            intent.putExtra("tipo", tipo);

            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
