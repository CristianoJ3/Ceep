package br.com.alura.ceep.webclient

import br.com.alura.ceep.webclient.services.NotaService
import retrofit2.Retrofit

class RetrofitInicializador {

    // inicializador do Retrofit
    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:8080/")
        .build()

    val notaService = retrofit.create(NotaService::class.java)
}