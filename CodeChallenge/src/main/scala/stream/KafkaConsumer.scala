package stream

import db._
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.KafkaUtils

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.streaming.StreamingQuery

abstract class Kafka {
  def wait_time: Int
  def topic: String

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
}

case class KafkaConsumer(topic: String, wait_time: Int) extends Kafka {
  


  def readStreamData(df_st: DataFrame): DataFrame = {

    val personStringDF = df_st.selectExpr("CAST(value AS STRING)")

    val personDF = personStringDF.select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    return personDF

  }

  def constructStreamingDF(df_out: DataFrame): StreamingQuery = {
    var processingTime = this.wait_time + " seconds"
    val df = df_out.writeStream
      .format("console")
      .outputMode("append")
      .trigger(Trigger.ProcessingTime(processingTime))
      // .trigger(Trigger.Once())
      // .trigger(Trigger.Continuous("10 seconds"))
      .start()

    df
  }

  //store number of visits into mysql
  def storeData_mysql(df_read: StreamingQuery) = {
    while (df_read.isActive) {
      if (df_read.lastProgress != null) {
        val time_visit = df_read.lastProgress.timestamp
        val visit_num = df_read.lastProgress.sink.numOutputRows
        val counting = DAO_visit(time_visit, visit_num)
        counting.insert()
        Thread.sleep(wait_time * 1000 - 1000)
      }
    }

  }

}