package services

import models._
import daos._

object GameService {
  def canGoTo(id: Long, dest: String) = {
    GameDao.byId(id).map { g =>
      LevelDao.byId(g.position).map { lvl =>
        println(g.position, lvl.conf.canGoTo)
        lvl.conf.canGoTo.filter(s => g.position.startsWith(s)).length > 0
      }.getOrElse(false)
    }.getOrElse(false)
  }

  def goTo(id: Long, dest: String) = {
    if(canGoTo(id, dest)) {
      println("updates")
      GameDao.byId(id).map { g =>
        GameDao.update(g.goTo(dest))
      }
    }
  }

  def make(id: Long, what: String) = {
    if(canGoTo(id, what)) {
      GameDao.byId(id).map { g =>
        println("do", what, g.position)
        if(what.startsWith(g.position)) {
            val nGame = what match {
              case "start-gotowork_biking" => {
                g.addKarma(1).goTo("office").addTime(1)
              }
              case "start-gotowork_metro" => {
                g.goTo("office")
              }
              case "start-gotowork_croissants" => {
                g.addKarma(2).goTo("office").addTime(2)
              }
              case "office-coffee" => {
                if(g.coffee > 0)
                  g.addEnergy(2).addCoffee(-1)
                else
                  g
              }
              case "office-water" => g.addEnergy(1)
              case _ => g
            }
            GameDao.update(nGame)
        }
      }
    }
  }
}
