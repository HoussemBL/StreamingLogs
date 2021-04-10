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

object Stream_consumption {
  def main(args: Array[String]): Unit = {

     val spark:SparkSession = SparkSession.builder()
      .master("local[*]")
      .appName("SparkByExample")
      .getOrCreate()

    
    val consumer = KafkaConsumer("logs1", 10)

    //consuming Kafka topic
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", consumer.topic)
      .option("startingOffsets", "earliest") // From starting
      .load()

    val df_out = consumer.readStreamData(df)

    val df_read = consumer.constructStreamingDF(df_out)

    consumer.storeData_mysql(df_read)

    df_read.awaitTermination()

  }
}
