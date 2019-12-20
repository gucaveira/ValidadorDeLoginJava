package com.e.validadordelogin.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.validadordelogin.R;
import com.e.validadordelogin.model.Usuario;
import com.e.validadordelogin.retrofit.UsuarioRetrofit;
import com.e.validadordelogin.retrofit.service.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DadosDoUsuariosActivity extends AppCompatActivity {

    private TextView nome, idade, login, email;
    private Button sair;
    SharedPreferences prf;
    Intent intent;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_do_usuarios);
        agrupaIdXml();
        buscaUsuario();

        prf = getSharedPreferences("detalhe_usario", MODE_PRIVATE);
        login.setText(prf.getString("cpf_ou_email", ""));
        btnSair();
    }

    private void btnSair() {
        sair.setOnClickListener(v -> {
            editor = prf.edit();
            editor.clear();
            editor.commit();
            intent = new Intent(DadosDoUsuariosActivity.this, MainActivity.class);
            startActivity(intent);
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

                    nome.setText(user.getNome());
                    idade.setText(user.getIdade());
                    email.setText(user.getEmail());
                    String idUsuario = user.getIdUsuario();
                    editor = prf.edit();
                    editor.putString("id", idUsuario);
                    editor.commit();

                } else {
                    Toast.makeText(DadosDoUsuariosActivity.this, "Erro" + response.code(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(DadosDoUsuariosActivity.this, "Erro" + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agrupaIdXml() {
        nome = findViewById(R.id.text_view_nome);
        idade = findViewById(R.id.text_view_idade);
        email = findViewById(R.id.text_view_email);
        login = findViewById(R.id.text_view_login);
        sair = findViewById(R.id.btn_sair);
    }
}
