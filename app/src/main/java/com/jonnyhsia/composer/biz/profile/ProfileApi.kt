package com.jonnyhsia.composer.biz.profile

import com.jonnyhsia.composer.biz.base.Response
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author JonnyHsia on 18/1/1.
 */
interface ProfileApi {

    @POST("user/register")
    @FormUrlEncoded
    fun register(
            @FieldMap user: Map<String, String>
    ): Single<Response<User>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Single<Response<User>>

    /**
     * @since composer
     */
    @POST("profile/index")
    fun getUserProfile(
            @Field("username") username: String
    ): Single<Response<User>>

}