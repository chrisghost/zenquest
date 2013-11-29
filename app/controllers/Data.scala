package controllers

import play.api._
import play.api.mvc._

import models._
import daos._
import services._

object Data extends Controller {

  def resetDb = Action {
    LevelDao.empty
    LevelDao.create(Level("start", LevelConf(List("office"), "Départ")))
    LevelDao.create(Level("office",
      LevelConf(List("office-water", "office-coffee"), "Bureau"))
    )

    MenuDao.empty
    MenuDao.create(Menu("start-bathroom", Choices("Que faites-vous?",
      List(
        Choice("Je me brosse les dents", "/go/start-bathroom-brushtheeth")
      , Choice("Je prend une douche", "/go/start-bathroom-shower")
      )
    )))

    MenuDao.create(Menu("start-closet", Choices("Que mettez-vous?",
      List(
        Choice("Le premier truc qui vient", "/go/start-closet-first")
      , Choice("Quelque chose d'assorti! Et adapté pour la saison", "/go/start-closet-good")
      , Choice("J'y vais en pyjama", "/go/start-closet-pyjama")
      )
    )))

    Ok("ok")
  }

}

