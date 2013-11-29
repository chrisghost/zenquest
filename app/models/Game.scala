package models

import anorm._
import anorm.SqlParser._

case class Game(time: Long, karma: Long, position: Long, energy:Long, id: Long)
object Game {
  val simple = {
    get[Long]("game.id") ~
    get[Long]("game.time") ~
    get[Long]("game.karma") ~
    get[Long]("groups.position") ~
    get[Long]("game.energy") map {
      case id ~ time ~ karma ~ position ~ energy =>
        Game(id, time, karma, position, energy)
    }
  }

}
