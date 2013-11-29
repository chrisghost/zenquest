package models

import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class LevelConf(canGoTo: List[String], name: String)
object LevelConf {
  implicit val levelConfReads: Reads[LevelConf]= (
    (__ \ "canGoTo").read[List[String]] and
    (__ \ "name").read[String]
  )(LevelConf.apply _)
  implicit val levelConfWrites: Writes[LevelConf]= (
    (__ \ "canGoTo").write[List[String]] and
    (__ \ "name").write[String]
  )(unlift(LevelConf.unapply _))
}
case class Level(id: String, conf: LevelConf)
object Level {
  import LevelConf._
  val simple = {
    get[String]("level.id") ~
    get[String]("level.conf") map {
      case id ~ conf =>
        Json.parse(conf).validate[LevelConf].map { conf =>
          Level(id, conf)
        }.getOrElse(Level("", LevelConf(Nil, "")))
    }
  }

}
