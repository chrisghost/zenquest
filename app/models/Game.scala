package models

import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Game(time: Long = 0, karma: Long = 0, energy:Long = 0, coffee: Long = 10, position: String = "start", id: Long = 0) {

  def addKarma(i: Int) = copy(karma=karma+i)
  def addTime(i: Int) = copy(time=time+i)
  def addEnergy(i: Int) = copy(energy=energy+i)
  def addCoffee(i: Int) = copy(coffee=coffee+i)
  def goTo(pos: String) = copy(position=pos)

}
object Game {
  val simple = {
    get[Long]("game.id") ~
    get[Long]("game.time") ~
    get[Long]("game.karma") ~
    get[Long]("game.coffee") ~
    get[String]("game.position") ~
    get[Long]("game.energy") map {
      case id ~ time ~ karma ~ coffee ~ position ~ energy =>
        Game(time, karma, energy, coffee, position, id)
    }
  }
}
