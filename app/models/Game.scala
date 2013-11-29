package models

import anorm._
import anorm.SqlParser._

case class Game(time: Long = 0, karma: Long = 0, position: Long = 0, energy:Long = 0, id: Long = 0)
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
