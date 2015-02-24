package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._


object Chart {

	def getCumulativeTotalUsers(serviceId: String, interval: String) : List[(String, Option[Int])] = {	
		DB.withConnection { implicit connection =>
			val sql_statement = "select time, cumulative_total from users_cumulative_total_users where" + " service_id = '" + serviceId + "'" + " and interval_type = '" + interval + "'"
			val totalUsers = SQL(sql_statement)().map(row =>
				row[String]("time") -> row[Option[Int]]("cumulative_total")
			).toList
			totalUsers
		}
	}
	
	def getRetentionRate(serviceId: String, interval: String) : List[(String, Option[Float])] = {
		DB.withConnection { implicit connection =>
			val sql_statement = ("select time, retention_rate from retention_rate"
				+" where interval_type = '" + interval + "'"
				+" and service_id = '" + serviceId + "'")
			val reteionrates = SQL(sql_statement)().map(row =>
				row[String]("time") -> row[Option[Float]]("retention_rate")
			).toList
			reteionrates
		}
	}
}
	








