package daos

import anorm._
import play.api.db.DB
import anorm.SqlParser._
import java.sql.Connection
import play.api.Play.current
import models._

object GameDao {
  def create(g: Game) = DB.withConnection { implicit conn =>
    g.copy(id=
      SQL(
        """
        INSERT INTO game VALUES
        ({time}, {karma}, {position}, {energy})
        RETURNING id
        """
      ).on(
        "time" -> g.time,
        "karma" -> g.karma,
        "position" -> g.position,
        "energy" -> g.energy
      ).as(scalar[Long].single)
    )
  }

  def byId(id: Long) = DB.withConnection { implicit conn =>
    SQL(
      """
      SELECT * FROM game WHERE id = {id}
      """
    ).on(
      "id" -> id
    ).as(Game.simple *)
  }
}
