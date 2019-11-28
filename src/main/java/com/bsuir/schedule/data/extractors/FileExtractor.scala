package com.bsuir.schedule.data.extractors

import com.bsuir.schedule.data.models.{GroupSchedule, Schedule, TeacherSchedule}

import scala.io.Source
import scala.reflect.io.File

class FileExtractor {
  val serializer: Serializer = new Serializer

  def saveToFile(group: String): Unit = {
    if (!getListOfSaves.contains(group)) {
      File("saves.txt").appendAll(group + "\n");
    }
  }

  def getListOfSaves: List[String] = {
    try {
      val source = Source.fromFile("saves.txt")
      val list = source.getLines().toList
      source.close()
      list
    } catch {
      case _ => List()
    }
  }
  def saveScheduleToFile(who: String, string: String): Unit = {
    try {
      File(who + ".txt").delete();
    } catch {
      case _ => null
    }
    File(who + ".txt").appendAll(string)
  }

  def removeFromFile(stuff: String): Unit = {
    val source = Source.fromFile("saves.txt")
    val list = source.getLines().toList.filter(str => str != stuff)
    source.close()
    File("saves.txt").delete()
    val file = File("saves.txt")
    for (str <- list) {
      file.appendAll(str + "\n")
    }
  }

  def openSchedule(who: String): Schedule = {
    val source = Source.fromFile(who + ".txt")
    val list = source.getLines().toList
    source.close()

    val classes = serializer.getSchedule(list.reduce((a, b) => a + b))
    if (who forall Character.isDigit) {
      new GroupSchedule(classes)
    } else {
      new TeacherSchedule(classes)
    }
  }
}
