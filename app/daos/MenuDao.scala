package daos

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import java.sql.Connection
import play.api.Play.current
import models._

import play.api.libs.json._

object MenuDao {
  def create(l: Menu) = DB.withConnection { implicit conn =>
    SQL(
      """
      INSERT INTO menu(id, choices) VALUES
      ({id}, {choices})
      """
    ).on(
      "id" -> l.id,
      "choices" -> Json.stringify(Json.toJson(l.choices))
    ).execute()
  }

  def byId(id: String):Option[Menu]  = DB.withConnection { implicit conn =>
    id match {
      case "" => None
      case _ => SQL(
          """
          SELECT * FROM menu WHERE id = {id}
          """
        ).on(
          "id" -> id
        ).as(Menu.simple.singleOpt)
    }
  }

  def empty = DB.withConnection { implicit conn =>
    SQL(
      """
      DELETE FROM menu
      """
    ).executeUpdate()
  }
}
