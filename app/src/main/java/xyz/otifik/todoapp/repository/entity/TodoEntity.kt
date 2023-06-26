package xyz.otifik.todoapp.repository.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "create_timestamp") val createTimestamp: Long,
    @ColumnInfo(name = "todo_timestamp") val todoTimestamp: Long,
    @ColumnInfo(name = "todo_content") val todoContent: String,
    @ColumnInfo(name = "is_done") val isDone: Boolean,
    @ColumnInfo(name = "is_deleted") val isDeleted: Boolean,
)