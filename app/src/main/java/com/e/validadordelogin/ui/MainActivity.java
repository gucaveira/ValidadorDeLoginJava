package com.e.validadordelogin.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.asynctask.BaseAsyncTask;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.retrofit.UsuarioRetrofit;
import com.e.validadordelogin.retrofit.service.UsuarioService;
import com.e.validadordelogin.validacpf.ValidadorCpf;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

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
        buscaUsuario();
    }

    private void buscaUsuario() {
        UsuarioService service = new UsuarioRetrofit().getUsuarioService();
        Call<List<Usuario>> call = service.buscaUsuarioApi("79");

        new BaseAsyncTask<>(() -> {
            try {
                Response<List<Usuario>> respota = call.execute();
                List<Usuario> usuarioApi = respota.body();
                return usuarioApi;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }, usuarioApi -> {
            if (usuarioApi != null) {
                Toast.makeText(this, usuarioApi.get(0).getCpf(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        "Não possível buscar os produtos da API",
                        Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    private void btnEnviar() {
        btnEnviar.setOnClickListener(v -> {
            verificaEEnviaToast();

            if (verificaSenha(senha.getText().toString().trim())) {
                Toast.makeText(MainActivity.this, "senha válida", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "inválido", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verificaSenha(final String senha) {

        Pattern pattern;
        Matcher matcher;

        final String SENHA_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(SENHA_PATTERN);
        matcher = pattern.matcher(senha);

        return matcher.matches();
    }

    private void verificaEEnviaToast() {
        if (ValidadorCpf.isCPF(getCpfConvertido())) {
            Toast.makeText(MainActivity.this, "CPF válido",
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
