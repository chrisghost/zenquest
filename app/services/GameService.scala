package services

import models._
import daos._

object GameService {
  def canGoTo(id: Long, dest: String) = {
    GameDao.byId(id).map { g =>
      println(g.state.position)
      LevelDao.byId(g.state.position).map { lvl =>
        lvl.conf.canGoTo.contains(dest)
      }.getOrElse(false)
    }.getOrElse(false)
  }

  def goTo(id: Long, dest: String) = {
    if(canGoTo(id, dest)) {
      println("updates")
      GameDao.byId(id).map { g =>
        GameDao.update(g.copy(state=g.state.copy(position=dest)))
      }
    }
  }

  def make(id: Long, what: String) = {
    if(canGoTo(id, what)) {
      GameDao.byId(id).map { g =>
        if(what.startsWith(g.state.position)) {
            val nGame = what match {
              case "start-gotowork_biking" => {
                g
              }
              case "start-gotowork_metro" => {
                g
              }
              case "start-gotowork_croissants" => {
                g
              }
              case "office-coffee" => {
                if(g.state.coffee > 0)
                  g.copy(energy=g.energy+2).copy(state=g.state.copy(coffee=g.state.coffee-1))
                else
                  g
              }
              case "office-water" => g.copy(energy=g.energy+1)
              case _ => g
            }
            GameDao.update(nGame)
        }
      }
    }
  }
}
