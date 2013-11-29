package controllers

import play.api._
import play.api.mvc._

import models._
import daos._

object Application extends Controller {

  def index = Action { implicit request =>
    request.session.get("gid") match {
      case Some(id) => GameDao.byId(id.toLong).map { game =>
        Ok(views.html.index(game))
      }.getOrElse{ NotFound }
      case None => Redirect(routes.Application.index).withSession()
    }
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
