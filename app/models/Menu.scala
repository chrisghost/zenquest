package models

import anorm._
import anorm.SqlParser._

import play.api.libs.json._
import play.api.libs.functional.syntax._
case class Choice(text: String, link: String)
object Choice {
  implicit val choiceReads: Reads[Choice]= (
    (__ \ "text").read[String] and
    (__ \ "link").read[String]
  )(Choice.apply _)
  implicit val choiceWrites: Writes[Choice]= (
    (__ \ "text").write[String] and
    (__ \ "link").write[String]
  )(unlift(Choice.unapply _))
}

case class Choices(desc: String, choices: List[Choice])
object Choices {
  implicit val choicesReads: Reads[Choices]= (
    (__ \ "desc").read[String] and
    (__ \ "choices").read[List[Choice]]
  )(Choices.apply _)
  implicit val choicesWrites: Writes[Choices]= (
    (__ \ "desc").write[String] and
    (__ \ "choices").write[List[Choice]]
  )(unlift(Choices.unapply _))
}
case class Menu(id: String, choices: Choices)
object Menu {
  val simple = {
    get[String]("menu.id") ~
    get[String]("menu.choices") map {
      case id ~ choices =>
        Json.parse(choices).validate[Choices].map { choices =>
          Menu(id, choices)
        }.getOrElse(Menu("", Choices("", Nil)))
    }
  }

}
