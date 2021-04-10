package solution

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import stream._
import db._

import java.util.Properties

object Stream_production {
  def main(args: Array[String]): Unit = {

      val schema = new StructType()
    .add("ip", StringType)
    .add("xx", StringType)
    .add("username", StringType)
    .add("date", StringType)
    .add("datepart2", StringType)
    .add("operation", StringType)
    .add("acess", StringType)
    .add("status", LongType)
    .add("size", LongType)
    
    
     val spark:SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("SparkByExample")
      .getOrCreate()

 
    val producer = KafkaProducer("logs2", 10)


    //reading stream of logs
    val dfCSV = spark.readStream.option("sep", " ")
      .option("header", "false")
      .schema(producer.schema/*schema*/)
     // .csv("/home/houssem/bigdata-docker-compose/nifi/stream/file*.csv")
            .csv("/home/houssem/bigdata-docker-compose/nifi/stream")

   
      //dfCSV.printSchema()
    
    //writing in kafka topic
    producer.writelogs_topic(dfCSV)
    
    
    
    
    
    
   

  }
}
