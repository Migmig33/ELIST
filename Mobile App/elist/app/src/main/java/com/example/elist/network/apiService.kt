package com.example.elist.network
import com.example.elist.model.Tasks
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.OutputStreamWriter


object ApiService{

    private var API_URL = "http://192.168.1.24:7037/api/List/active"
    private var API_URL_INACTIVE = "http://192.168.1.24:7037/api/List/inactive"

    private var API_INSERT = "http://192.168.1.24:7037/api/List/SaveTask"



    suspend fun fetchTask(): List<Tasks>? {
        return try{

            val api = API_URL;
            val url = URL(api)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod ="GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if(connection.responseCode == HttpURLConnection.HTTP_OK){
                val response = connection.inputStream.bufferedReader().readText()
                val type = object : TypeToken<List<Tasks>>() {}.type
                Gson().fromJson<List<Tasks>>(response, type)
            }else null
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
    suspend fun fetchInactiveTask(): List<Tasks>? {
        return try{
            val api = API_URL_INACTIVE;
            val url = URL(api)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod="GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            if(connection.responseCode == HttpURLConnection.HTTP_OK){
                val response = connection.inputStream.bufferedReader().readText()
                val type = object : TypeToken<List<Tasks>>() {}.type
                Gson().fromJson<List<Tasks>>(response, type)

            }else null
        }catch(e: Exception){
            e.printStackTrace()
            null
        }

    }




    suspend fun addTask(tasks: Tasks): Boolean{
        return try{
            val api = API_INSERT;
            val url = URL(api)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val jsonBody = Gson().toJson(tasks)

            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(jsonBody)
            outputStreamWriter.flush()
            outputStreamWriter.close()

            val responseCode = connection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED){
                true
            }else{
                val error = connection.errorStream?.bufferedReader()?.readText()
                println("POST failed: $error")
                false

            }

        }catch(e:Exception){
            e.printStackTrace()
            false
        }
    }


    suspend fun deleteTask(tasks: Tasks): Boolean{
        return try{
            val api = "http://10.0.2.2:7037/api/List/delete${tasks.task_id}";
            val url = URL(api)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            connection.connectTimeout = 5000
            connection.readTimeout = 5000

            val jsonBody = Gson().toJson(tasks)

            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(jsonBody)
            outputStreamWriter.flush()
            outputStreamWriter.close()

            val responseCode = connection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED){
                true
            }else{
                val error = connection.errorStream?.bufferedReader()?.readText()
                println("POST failed: $error")
                false

            }

        }catch(e:Exception){
            e.printStackTrace()
            false
        }
    }
}