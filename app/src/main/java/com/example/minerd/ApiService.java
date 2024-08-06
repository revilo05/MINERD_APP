package com.example.minerd;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface ApiService {

    @POST("minerd/registrar_visita.php")
    Call<Void> registrarIncidencia(@Body Incidencia incidencia);
}