package com.bsuir.schedule.data.extractors

import scala.io.Source
import scala.reflect.io.File

class FileExtractor {
  def saveToFile(group: String): Unit = {
    File("saves.txt").appendAll(group + "\n");
  }

  def getListOfSaves: List[String] = {
    val source = Source.fromFile("saves.txt")
    val list = source.getLines().toList
    source.close()
    list
  }

  def removeFromFile(stuff: String): Unit = {
    val source = Source.fromFile("saves.txt")
    val list = source.getLines().toList.filter(str => str != stuff)
    source.close()
    File("saves.txt").delete()
    val file = File("saves.txt")
    for (str <- list) {
      file.appendAll(str)
    }
  }
}
