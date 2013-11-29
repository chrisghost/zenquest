package controllers

import play.api._
import play.api.mvc._

import models._
import daos._

object Application extends Controller {

  def index = Action { implicit request =>
    println(request.session)
    Ok(views.html.index("Your new application is ready."))
  }

  def create = Action {
    val nGame = GameDao.create(Game())
    Redirect(routes.Application.index)
      .withSession(
        Session(
          Map(
            ("gid", nGame.id+"")
          )
        )
      )
  }
}
