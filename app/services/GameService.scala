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
}
