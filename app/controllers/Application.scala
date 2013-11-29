package controllers

import play.api._
import play.api.mvc._

import models._
import daos._
import services._

object Application extends Controller {

  def index(menu: Option[String]) = Action { implicit request =>
    request.session.get("gid") match {
      case Some(id) => GameDao.byId(id.toLong).map { game =>
        val choices = MenuDao.byId(menu.getOrElse(""))

        game.state.position match {
          case "start" => Ok(views.html.start(game, choices))
          case "office" => Ok(views.html.office(game, choices))
          case _ => Ok(views.html.index(game, choices))
        }
      }.getOrElse{ NotFound }
      case None => Redirect(routes.Application.create).withSession()
    }
  }

  def create = Action {
    val nGame = GameDao.create(Game())
    Redirect(routes.Application.index(None))
      .withSession(
        Session(
          Map(
            ("gid", nGame.id+"")
          )
        )
      )
  }

  def go(dest: String) = Action { request =>
    request.session.get("gid").map { id =>

      if(dest.contains("-"))
        GameService.make(id.toLong, dest)
      else
        GameService.goTo(id.toLong, dest)

      Redirect(routes.Application.index(None))
    }.getOrElse( Redirect(routes.Application.create).withSession() )
  }
}
