package com.e.validadordelogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.validacpf.ValidadorCpf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        btnEnviar.setOnClickListener(click -> chamaProximaTela());
    }

    private void chamaProximaTela() {
        if (enviaSenhaverificada() & (enviaCpfVerificado() || enviaEmailverificada())) {
            Intent intent = new Intent(this, DadosDoUsuariosActivity.class);
            startActivity(intent);
        }
    }

    private boolean enviaSenhaverificada() {
        if (verificaSenha(senha.getText().toString().trim())) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, " senha inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean verificaSenha(final String senha) {
        Pattern pattern;
        Matcher matcher;

        final String SENHA_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()_;.,?:])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(SENHA_PATTERN);
        matcher = pattern.matcher(senha);

        return matcher.matches();
    }

    public boolean verificaSeEhEmailOuCpf(final String senhaOuEmail) {
        Pattern patterns;
        Matcher matcher;

        final String test = "^\\w+([.-]?\\w+)@\\w+([.-]?\\w+)(.\\w{1,3})+$";

        patterns = Pattern.compile(test);
        matcher = patterns.matcher(senhaOuEmail);

        return matcher.matches();
    }

    public boolean enviaEmailverificada() {
        if (verificaSeEhEmailOuCpf(cpf.getText().toString())) {
            return true;
        } else {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean enviaCpfVerificado() {
        if (ValidadorCpf.isCPF(cpfConvertido())) {
            return true;
        } else if (!enviaEmailverificada()) {
            Toast.makeText(MainActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void intanciandoUsuario() {
        new Usuario(cpfConvertido(), senha.toString());
    }

    private String cpfConvertido() {
        return cpf.getText().toString().replaceAll("[^0-9]", "");
    }

    private void agrupaIdXml() {
        cpf = findViewById(R.id.editTextCpf);
        senha = findViewById(R.id.editTextSenha);
        btnEnviar = findViewById(R.id.btn_enviar);
    }
}
