package models

import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class GameState(position: String = "start", coffee: Long = 10)
object GameState {
  implicit val gameStateReads: Reads[GameState]= (
    (__ \ "position").read[String] and
    (__ \ "coffee").read[Long]
  )(GameState.apply _)
  implicit val gameStateWrites: Writes[GameState]= (
    (__ \ "position").write[String] and
    (__ \ "coffee").write[Long]
  )(unlift(GameState.unapply _))
}
case class Game(time: Long = 0, karma: Long = 0, state: GameState = GameState(), energy:Long = 0, id: Long = 0)
object Game {
  val simple = {
    get[Long]("game.id") ~
    get[Long]("game.time") ~
    get[Long]("game.karma") ~
    get[String]("game.state") ~
    get[Long]("game.energy") map {
      case id ~ time ~ karma ~ state ~ energy =>
        Json.parse(state).validate[GameState].map { state =>
          Game(time, karma, state, energy, id)
        }.getOrElse(Game())
    }
  }

}
