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

    @Query("SELECT * FROM todo WHERE is_done = 1 AND is_deleted = 0")
    fun getDoneAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE is_done = 0 AND is_deleted = 0")
    fun getUndoneAll(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insert(vararg todoEntity: TodoEntity)

    //在回收站中永久删除
    @Delete
    suspend fun delete(todoEntity: TodoEntity)

    //包括is_deleted和is_completed的更改
    @Update
    suspend fun update(todoEntity: TodoEntity)
}