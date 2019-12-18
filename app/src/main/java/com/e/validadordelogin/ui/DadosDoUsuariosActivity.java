package com.e.validadordelogin.ui;

import android.os.Bundle;
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

    private TextView nome;
    private TextView idade;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_do_usuarios);
        buscaUsuario();

        agrupaIdXml();
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

                    Toast.makeText(DadosDoUsuariosActivity.this, user.getNome(), Toast.LENGTH_SHORT).show();

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
    }
}
