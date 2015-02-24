import play.api._
import play.api.mvc._
import scalaj.http._


object Global extends GlobalSettings {

	/**
	 * Authenticating before user access api. Only check on production enviroment.
	 * 
	 * User should already logged in admin console to access api.
	 * User logged in with valid session is able to acess api, otherwise return 401. 
	 */
	override def onRouteRequest(request: RequestHeader): Option[Handler] = {
		play.Play.application.configuration.getString("application.env") match {
			case "prod" => {
				val serviceId: String = request.path.split("/")(2)
				val cookie: String = request.headers.get("Cookie").getOrElse("")
				val session: String = getSession(play.Play.application.configuration.getString("application.adminSession"), cookie)
				val authorize = isAuthorized(serviceId: String, session: String)
				if (authorize) super.onRouteRequest(request) else Some(controllers.Application.unAuthorized())
			}
			case _ => super.onRouteRequest(request)
		}
	}			

	def isAuthorized(serviceId: String, session: String) : Boolean = session match {
		case "" => false
		case _  => isAccessable(serviceId, session)
	}
	
	def isAccessable(serviceId: String, session: String) : Boolean = {
		val response: HttpResponse[String] = Http("http://" + play.Play.application.configuration.getString("application.adminApiDomain") + "/services").header("Cookie", session).asString
		val result = if (response.body.toString().contains(serviceId)) true else false
		result
	}
	
	def getSession(sessionName: String, cookie: String) : String = {
		val cookies = cookie.split(";")
		val session = cookies.filter(_ contains sessionName).head.trim		
		session
	}
}
