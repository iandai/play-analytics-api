package controllers

import play.api._
import play.api.mvc._


object Application extends Controller {

    def unAuthorized = Action {
		Unauthorized("You are not authorized to access this api.")
    }
}
