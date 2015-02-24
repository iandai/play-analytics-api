package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models._
import java.io._

object User extends Controller {

	case class User(time: String, value: Option[Int])

	def cumulativeTotalUsers(serviceId: String, interval: String) = Action {		  
		val totalUsers = Chart.getCumulativeTotalUsers(serviceId: String, interval: String).map(user => User(user._1, user._2))
		implicit val userFormat = Json.format[User]
		Ok(Json.toJson(totalUsers))
	}
	
	def cumulativeTotalUsersDownload(serviceId: String, interval: String) = Action {
		val totalUsers = Chart.getCumulativeTotalUsers(serviceId: String, interval: String).map(user => User(user._1, user._2))
		
		val file = new File("tmp.csv")
		val writer = new PrintWriter(file)
		writer.write("time,value\n")
		totalUsers.foreach { user => writer.write(user.time + "," + user.value.orNull + "\n")}
		writer.close()
	
		Ok.sendFile(
			content = file,
			fileName = _ => "cumulative_total_users" + "_" + serviceId + "_" + interval + ".csv"
		)
	}
}










