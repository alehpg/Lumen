package com.example.lmen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView saldoText;
    ListView listView;
    ArrayList<String> lancamentos;
    ArrayAdapter<String> adapter;

    double saldoAtual = 0.0;

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saldoText = findViewById(R.id.saldo);
        listView = findViewById(R.id.listView);

        lancamentos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, lancamentos);
        listView.setAdapter(adapter);

        atualizarSaldo();

        // RECEBE OS DADOS DA OUTRA TELA
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            double valor = data.getDoubleExtra("valor", 0.0);
                            String desc = data.getStringExtra("descricao");
                            String tipo = data.getStringExtra("tipo");

                        // REGRAS DE LANÇAMENTO
                            if ("dispensa".equals(tipo)) {
                                // SAÍDA → SUBTRAI
                                saldoAtual -= valor;
                                lancamentos .add(desc + " - R$( - " + valor + " )");
                            } else {
                                // ENTRADA → SOMA
                                saldoAtual += valor;
                                lancamentos .add(desc + " - R$( + " + valor + " )");

                            }

                            atualizarSaldo();
                            adapter.notifyDataSetChanged();

                            atualizarSaldo();

                            // Adiciona na lista

                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        // BOTÃO PARA ABRIR A SEGUNDA TELA
        findViewById(R.id.btnAdicionar).setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, DetalheLancamento.class);
            launcher.launch(i);
        });
    }

    private void atualizarSaldo(){
        saldoText.setText("R$ " + String.format("%.2f", saldoAtual));
        if (saldoAtual < 0) {
            saldoText.setTextColor(Color.RED);
        }

    }

}
