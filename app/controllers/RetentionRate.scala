package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import models._


object RetentionRate extends Controller {

	case class RetentionRateFormat(time: String, value: Option[Float])
	
	def variation(serviceId: String, interval: String) = Action {
		val retentionRates = Chart.getRetentionRate(serviceId: String, interval: String).map(rate => RetentionRateFormat(rate._1, rate._2))
		implicit val retentionRateFormat = Json.format[RetentionRateFormat]
		Ok(Json.toJson(retentionRates))
	}
}
