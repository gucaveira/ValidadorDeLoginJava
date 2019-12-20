package com.e.validadordelogin.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.VerificadorDeTexto;
import com.e.validadordelogin.VerificadorSenha;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.validacpf.ValidadorCpf;

public class MainActivity extends AppCompatActivity {

    private EditText editCpfouEmail, editSenha;
    private Button btnEnviar;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntanciandoSharedPreferences();
        verificaUsuarioLogando();
        agrupaIdXml();
        intanciandoUsuario();
        btnEnviar();
    }

    private void IntanciandoSharedPreferences() {
        pref = getApplicationContext().getSharedPreferences("detalhe_usario", MODE_PRIVATE);
    }

    private void verificaUsuarioLogando() {
        String idUser = pref.getString("id", "");
        if (!idUser.equals("")) {
            Intent intent = new Intent(MainActivity.this, DadosDoUsuariosActivity.class);
            startActivity(intent);
            this.finish();
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

    private String pegaEditTextString(EditText editCpfouEmail) {
        return editCpfouEmail.getText().toString();
    }

    private void chamaProximaTela() {
        if (enviaSenhaverificada() & (enviaCpfVerificado() || enviaEmailverificada())) {
            Intent intent = new Intent(this, DadosDoUsuariosActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    private boolean enviaSenhaverificada() {
        if (VerificadorSenha.verificaSenha(pegaEditTextString(editSenha).trim())) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, "Senha inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean enviaEmailverificada() {
        if (VerificadorDeTexto.verificaSeEhEmailOuCpf(pegaEditTextString(editCpfouEmail))) {
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
        return pegaEditTextString(editCpfouEmail).replaceAll("[^0-9]", "");
    }

    private void agrupaIdXml() {
        editCpfouEmail = findViewById(R.id.edit_text_cpf);
        editSenha = findViewById(R.id.edit_text_senha);
        btnEnviar = findViewById(R.id.btn_enviar);
    }
}
