package xyz.otifik.todoapp.repository.serializer

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import xyz.otifik.todoapp.TodoAppData
import java.io.InputStream
import java.io.OutputStream

//序列化器
object TodoAppDataSerializer: Serializer<TodoAppData> {

    override val defaultValue: TodoAppData
        get() = TodoAppData()

    override suspend fun readFrom(input: InputStream): TodoAppData {
        return try {
            Json.decodeFromString(
                deserializer = TodoAppData.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: TodoAppData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = TodoAppData.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}