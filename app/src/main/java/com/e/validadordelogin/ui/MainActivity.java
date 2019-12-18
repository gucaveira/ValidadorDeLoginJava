package com.e.validadordelogin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.retrofit.UsuarioRetrofit;
import com.e.validadordelogin.retrofit.service.UsuarioService;
import com.e.validadordelogin.validacpf.ValidadorCpf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
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
    }

    private void btnEnviar() {
        btnEnviar.setOnClickListener(v -> {
            enviaToast();
            //buscaUsuario();
        });
    }

    private void buscaUsuario() {

        UsuarioService service = new UsuarioRetrofit().getUsuarioService();
        Call<Usuario> callUsuario = service.buscaUsuarioApi();

        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario user = response.body();

                    Toast.makeText(MainActivity.this, user.getNome(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Erro" + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
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

    private void enviaToast() {
        if (enviaToastSenha() & enviaToasCpf()) {
            Intent intent = new Intent(this, DadosDoUsuariosActivity.class);
            startActivity(intent);
        }
    }

    private boolean enviaToasCpf() {
        if (ValidadorCpf.isCPF(getCpfConvertido())) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, "CPF inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private boolean enviaToastSenha() {
        if (verificaSenha(senha.getText().toString().trim())) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, " senha inválido", Toast.LENGTH_SHORT).show();
        }
        return false;
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
