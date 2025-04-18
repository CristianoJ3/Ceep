package br.com.alura.ceep.repository

import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.webclient.NotaWebClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotaRepository(
    private val dao: NotaDao,
    private val webClient: NotaWebClient
) {

    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    private suspend fun atualizaTodas() {
        webClient.buscaTodas()?.let { notas ->
            val notaSincronizada = notas.map { nota ->
                nota.copy(sincronizada = true)
            }
            dao.salva(notaSincronizada)
        }
    }

    suspend fun sincroniza() {
        val notasDesativadas = dao.buscaDesativadas().first()
        notasDesativadas.forEach { notaDesativada ->
            remove(notaDesativada.id)

        }
        val notasNaoSincronizadas = dao.buscaNaoSincronizadas().first()
        notasNaoSincronizadas.forEach { notaNaoSincronizada ->
            salva(notaNaoSincronizada)
        }
        atualizaTodas()
    }

    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        dao.desativa(id)
        if (webClient.remove(id)) {
            dao.remove(id)
        }
    }

    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        extracted(nota)

    }

    private suspend fun extracted(nota: Nota) {
        webClient.salva(nota)
    }

}