package com.example.easyin
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.MongoException
object DbConnection {
    @JvmStatic
    fun main(args: Array<String>){
        var mongoClient: MongoClient? = null
        try {
            mongoClient = MongoClient("oneeasyin@easyin.dyfi5.mongodb.net/easyin?retryWrites=true&w=majority", 27017)
            println("Kotlin connected to MongoDB!")
          //  var db = mongoClient.getDatabase("user")
          //  var tbl = db.getCollection("test")
            val document = BasicDBObject()
            document.get("http://oneeasyin.com:8080/users/test")

        } catch (e: MongoException) {
            e.printStackTrace()
        } finally {
            mongoClient!!.close()
        }
    }
}