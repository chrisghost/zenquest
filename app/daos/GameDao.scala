package daos

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import java.sql.Connection
import play.api.Play.current
import models._

import play.api.libs.json._

object GameDao {
  def create(g: Game) = DB.withConnection { implicit conn =>
    g.copy(id=
      SQL(
        """
        INSERT INTO game VALUES
        ({time}, {karma}, {energy}, {coffee}, {position}, {progress})
        RETURNING id
        """
      ).on(
        "time" -> g.time,
        "karma" -> g.karma,
        "energy" -> g.energy,
        "coffee" -> g.coffee,
        "position" -> g.position,
        "progress" -> g.progress
      ).as(scalar[Long].single)
    )
  }

  def update(g: Game) = DB.withConnection { implicit conn =>
    SQL(
      """
      UPDATE game SET
      time = {time},
      karma = {karma},
      energy = {energy},
      position = {position},
      progress = {progress},
      coffee = {coffee}
      WHERE id = {id}
      """
    ).on(
      "time" -> g.time,
      "karma" -> g.karma,
      "coffee" -> g.coffee,
      "position" -> g.position,
      "progress" -> g.progress,
      "energy" -> g.energy,
      "id" -> g.id
    ).executeUpdate()
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
