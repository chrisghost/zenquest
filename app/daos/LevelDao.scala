package daos

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import java.sql.Connection
import play.api.Play.current
import models._
import models.GameState._

import play.api.libs.json._

object LevelDao {
  def create(l: Level) = DB.withConnection { implicit conn =>
    SQL(
      """
      INSERT INTO level VALUES
      ({id}, {conf})
      """
    ).on(
      "id" -> l.id,
      "conf" -> Json.stringify(Json.toJson(l.conf))
    ).executeInsert()
  }

  def byId(id: String):Option[Level]  = DB.withConnection { implicit conn =>
    SQL(
      """
      SELECT * FROM level WHERE id = {id}
      """
    ).on(
      "id" -> id
    ).as(Level.simple.singleOpt)
  }

  def empty = DB.withConnection { implicit conn =>
    SQL(
      """
      DELETE FROM level
      """
    ).executeUpdate()
  }
}
