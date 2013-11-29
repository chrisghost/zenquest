package daos

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import java.sql.Connection
import play.api.Play.current
import models._
import models.GameState._

import play.api.libs.json._

object GameDao {
  def create(g: Game) = DB.withConnection { implicit conn =>
    g.copy(id=
      SQL(
        """
        INSERT INTO game VALUES
        ({time}, {karma}, {state}, {energy})
        RETURNING id
        """
      ).on(
        "time" -> g.time,
        "karma" -> g.karma,
        "state" -> Json.stringify(Json.toJson(g.state)),
        "energy" -> g.energy
      ).as(scalar[Long].single)
    )
  }

  def byId(id: Long):Option[Game]  = DB.withConnection { implicit conn =>
    SQL(
      """
      SELECT * FROM game WHERE id = {id}
      """
    ).on(
      "id" -> id
    ).as(Game.simple.singleOpt)
  }
}
