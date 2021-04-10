package stream
import org.apache.spark.sql.types._
trait Kafka {
  var wait_time: Long
  var topic: String

 //specify batch interval
   def convertTime :String={
  var processingTime = this.wait_time + " seconds"
  processingTime
  }
  
  
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