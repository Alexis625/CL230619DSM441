package com.example.desafio3

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SolicitudesAPI {
    @GET("resursos_aprendizajes")
    suspend fun getRecursos(): List<Model>

    @POST("resursos_aprendizajes")
    suspend fun createRecurso(@Body recurso: Model): Model

    @PUT("resursos_aprendizajes/{id}")
    suspend fun updateRecurso(@Path("id") id: Long, @Body recurso: Model)

    @DELETE("resursos_aprendizajes/{id}")
    suspend fun deleteRecurso(@Path("id") id: Long)
}
