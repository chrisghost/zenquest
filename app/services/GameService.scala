package services

import models._
import daos._

object GameService {
  def canGoTo(id: Long, dest: String) = {
    GameDao.byId(id).map { g =>
      LevelDao.byId(g.position).map { lvl =>
        (lvl.conf.canGoTo.filter(s => g.position.startsWith(s)).length > 0) && (dest match {
          case "devoffice" => g.progress == "afterdaily" || g.progress == "afternoon"
          case "designoffice"|"fp2office"|"ic2office" => g.progress == "afternoon"
          case _ => true
        })
      }.getOrElse(false)
    }.getOrElse(false)
  }

  def goTo(id: Long, dest: String) = {
    if(canGoTo(id, dest)) {
      GameDao.byId(id).map { g =>
        GameDao.update(g.addTime(1).goTo(dest))
      }
    }
  }

  def make(id: Long, what: String) = {
    if(canGoTo(id, what)) {
      GameDao.byId(id).map { g =>
        println("do", what, g.position)
        if(what.startsWith(g.position)) {
            val nGame = what match {
              case "start-gotowork_biking" => g.addKarma(1).goTo("office").addTime(1)
              case "start-gotowork_metro" => g.goTo("office")
              case "start-gotowork_croissants" => g.addKarma(2).goTo("office").addTime(2)
              case "office-daily" => g.setProgress("afterdaily")
              case "office-coffee" => {
                if(g.coffee > 0)
                  g.addEnergy(2).addCoffee(-1).addTime(1)
                else
                  g
              }
              case "office-water" => g.addEnergy(1).addTime(1)
              case _ => g
            }
            GameDao.update(nGame)
        }
      }
    }
  }
}
