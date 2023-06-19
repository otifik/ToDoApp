package xyz.otifik.todoapp.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import xyz.otifik.todoapp.repository.entity.TodoEntity

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE is_deleted = 0")
    fun getUndeletedAll(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insert(vararg todoEntity: TodoEntity)

    @Delete
    suspend fun delete(todoEntity: TodoEntity)

    @Update
    suspend fun update(todoEntity: TodoEntity)
}