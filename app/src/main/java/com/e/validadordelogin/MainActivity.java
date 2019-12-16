package com.e.validadordelogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.validacpf.ValidadorCpf;

public class MainActivity extends AppCompatActivity {

    private EditText cpf;
    private EditText senha;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agrupaIdXml();
        intanciandoUsuario();
        btnEnviar();
    }

    private void btnEnviar() {
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazVerificacaoEEnviaToast();
            }
        });
    }

    private void fazVerificacaoEEnviaToast() {
        if (ValidadorCpf.isCPF(getCpfConvertido())) {
            Toast.makeText(MainActivity.this, ValidadorCpf.imprimeCpf(getCpfConvertido()),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void intanciandoUsuario() {
        new Usuario(getCpfConvertido(), senha.toString());

    }

    private String getCpfConvertido() {
        return cpf.getText().toString().replaceAll("[^0-9]", "");
    }

    private void agrupaIdXml() {
        cpf = findViewById(R.id.editTextCpf);
        senha = findViewById(R.id.editTextSenha);
        btnEnviar = findViewById(R.id.btn_enviar);
    }
}
