package repository

import base.BaseRepository
import base.State
import entity.ReqresMapper
import kotlinx.coroutines.flow.Flow
import entity.data.User
import entity.response.ReqresResponse

class ReqresUserRepository : BaseRepository() {

    fun getUser(): Flow<State<User>> {
        return suspend {
            getHttpResponse(urlString = "https://reqres.in/api/users?page=2")
        }.reduce<ReqresResponse, User> {
            val user = ReqresMapper.mapResponseToUser(it)
            State.Success(user)
        }
    }
 }