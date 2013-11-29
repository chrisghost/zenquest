package controllers

import play.api._
import play.api.mvc._

import models._
import daos._
import services._

object Application extends Controller {

  def index = Action { implicit request =>
    request.session.get("gid") match {
      case Some(id) => GameDao.byId(id.toLong).map { game =>
        game.state.position match {
          case "start" => Ok(views.html.start(game))
          case "office" => Ok(views.html.office(game))
          case _ => Ok(views.html.index(game))
        }
      }.getOrElse{ NotFound }
      case None => Redirect(routes.Application.create).withSession()
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

  def go(dest: String) = Action { request =>
    request.session.get("gid").map { id =>
      GameService.goTo(id.toLong, dest)
      Redirect(routes.Application.index)
    }.getOrElse( Redirect(routes.Application.create).withSession() )
  }


  def resetDb = Action {
    LevelDao.empty
    LevelDao.create(Level("start", LevelConf(List("office"), "Départ")))
    LevelDao.create(Level("office", LevelConf(List(""), "Bureau")))

    Ok("ok")
  }
}
