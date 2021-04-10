package db
import java.sql._
//import com.typesafe.config._


trait DAO {
   //jdbc driver name and database URL
  lazy val JDBC_DRIVER = "com.mysql.jdbc.Driver"
 lazy val DB_URL = "jdbc:mysql://localhost:3306/mylogs"
   //database credentials
 lazy val USER = "root"
 lazy val PASS = "password"
 
  // val appConf = ConfigFactory.parseFile(confFile)
  

  
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




  

