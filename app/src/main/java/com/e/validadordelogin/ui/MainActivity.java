package com.e.validadordelogin.ui;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private EditText editCpfouEmail, editSenha;
    private Button btnEnviar;
    Intent intent;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("detalhe_usario", MODE_PRIVATE);

        verificaUsuarioLogando();
        agrupaIdXml();
        intanciandoUsuario();

        btnEnviar();
    }

    private void verificaUsuarioLogando() {
        String idUser = pref.getString("id", "");
        if (!idUser.equals("")) {
            intent = new Intent(MainActivity.this, DadosDoUsuariosActivity.class);
            startActivity(intent);
        }
    }

    private void btnEnviar() {
        btnEnviar.setOnClickListener(v -> {
            editor = pref.edit();
            editor.putString("cpf_ou_email", editCpfouEmail.getText().toString());
            editor.putString("edit_senha", editSenha.getText().toString());
            editor.commit();
            chamaProximaTela();
        });
    }

    private String editCpfOuEmailToString(EditText editCpfouEmail) {
        return editCpfouEmail.getText().toString();
    }

    private void chamaProximaTela() {
        if (enviaSenhaverificada() & (enviaCpfVerificado() || enviaEmailverificada())) {
            Intent intent = new Intent(this, DadosDoUsuariosActivity.class);
            startActivity(intent);
        }
    }

    private boolean enviaSenhaverificada() {
        if (verificaSenha(editCpfOuEmailToString(editSenha).trim())) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, " editSenha inválido", Toast.LENGTH_SHORT).show();
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
        if (verificaSeEhEmailOuCpf(editCpfOuEmailToString(editCpfouEmail))) {
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
        new Usuario(cpfConvertido(), editSenha.toString());
    }

    private String cpfConvertido() {
        return editCpfOuEmailToString(editCpfouEmail).replaceAll("[^0-9]", "");
    }

    private void agrupaIdXml() {
        editCpfouEmail = findViewById(R.id.editTextCpf);
        editSenha = findViewById(R.id.editTextSenha);
        btnEnviar = findViewById(R.id.btn_enviar);
    }
}
