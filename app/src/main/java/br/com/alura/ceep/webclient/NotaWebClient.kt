package br.com.alura.ceep.webclient

import br.com.alura.ceep.model.Nota

class NotaWebClient {

    suspend fun buscaTodas() : List<Nota>{

        val notaResposta = RetrofitInicializador().notaService
            .buscaTodas()

        return notaResposta.map {  notaResposta ->
            notaResposta.nota
        }
    }

}