package db
import java.sql._
//import com.typesafe.config._
import java.util.Properties
import scala.io.Source

trait DAO {
   //jdbc driver name and database URL
  val   DBparameters=readMYSQLProperties()    

  lazy val JDBC_DRIVER = /*"com.mysql.jdbc.Driver"*/DBparameters.getProperty("jdbc_driver")
 lazy val DB_URL = /*"jdbc:mysql://localhost:3306/mylogs"*/DBparameters.getProperty("db_url")
   //database credentials
 lazy val USER = /*"root"*/DBparameters.getProperty("mysql_user")
 lazy val PASS = /*"password"*/DBparameters.getProperty("mysql_pass")
 

  
      //read properties of mysql specified in src.main.resources  
  def readMYSQLProperties(): Properties=
  {
     val url = getClass.getResource("/db.properties") 
 val source = Source.fromURL(url)
    val mysqlparameters = new Properties
mysqlparameters.load(source.bufferedReader())

mysqlparameters

  }
  
}

case class DAO_visit(timestamp: String, count: Long) extends DAO {


    val insertSql = """
    |insert into visits_stats(timestamp,number_visit)
    |values (?,?)
""".stripMargin


def insert()={
  var conn: Connection = null
  var stmt: Statement = null

  try {
    Class.forName(JDBC_DRIVER)
    conn = DriverManager.getConnection(DB_URL, USER, PASS)

    stmt = conn.createStatement

    val preparedStmt: PreparedStatement = conn.prepareStatement(insertSql)

    preparedStmt.setString(1, timestamp)
    preparedStmt.setLong(2, count)
    preparedStmt.execute

    preparedStmt.close()

    // cleanup
    stmt.close
    conn.close
  } catch {
    case se: SQLException => se.printStackTrace
    case e: Exception => e.printStackTrace
  } finally {
    try {
      if (stmt != null) stmt.close
    } catch {
      case se2: SQLException => // nothing we can do
    }
    try {
      if (conn != null) conn.close
    } catch {
      case se: SQLException => se.printStackTrace
    } //end finally-try
  } //end try

 // println("the end")
  }

    
    
    

}




  

