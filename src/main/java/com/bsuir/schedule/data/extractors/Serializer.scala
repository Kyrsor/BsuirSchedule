package com.bsuir.schedule.data.extractors

import com.bsuir.schedule.data.models.{ClassObject, ClassPosition, Teacher}
import play.api.libs.json.{JsDefined, JsNull, JsObject, JsUndefined, Json}



class Serializer {

  def getSchedule(rawJSON: String): Array[ClassObject] = {
    val parsed = Json.parse(rawJSON)
    val schedules: List[JsObject] = (parsed\"schedules").as[List[JsObject]]
    schedules.map(json => parseDay(json)).reduce((a,b)=>a++b).toArray
  }

  def parseDay(day: JsObject): List[ClassObject] = {
    val weekDay = (day\"weekDay").as[String] match {
      case "Понедельник" => 0
      case "Вторник" => 1
      case "Среда" => 2
      case "Четверг" => 3
      case "Пятница" => 4
      case "Субота" => 5
    }
    val schedule = (day\"schedule").as[List[JsObject]]

    schedule.map(obj => parseObj(obj, weekDay))
  }

  def parseObj(jsObject: JsObject, weekDay: Int): ClassObject = {
    print(jsObject.toString + "\n")
    val name = (jsObject\"subject").as[String]
    val teacher = if ((jsObject \ "employee").as[List[JsObject]].nonEmpty)
      parseTeacher((jsObject\"employee").as[List[JsObject]].head)
    else {
      null
    }
    val lessonType = (jsObject\"lessonType").as[String] match {
      case "ЛР" => ClassObject.ClassType.LABORATORY
      case "ПЗ" => ClassObject.ClassType.PRACTICE
      case "ЛК" => ClassObject.ClassType.LECTURE
    }
    val classPositions = parseClassPositions(jsObject, weekDay)
    new ClassObject(
      name, teacher, lessonType, classPositions, "750505"
    )
  }

  def parseTeacher(teacherJSON: JsObject): Teacher = {
    val name = (teacherJSON\"lastName").as[String] + " " + (teacherJSON\"firstName").as[String] +
      " " + (teacherJSON\"middleName").as[String]
    val photo_url = if ((teacherJSON\"photoLink").toString == "JsDefined(null)") {
      null
    } else {
      (teacherJSON \ "photoLink").as[String]
    }
    new Teacher(
      name, photo_url
    )
  }
  def parseClassPositions(jsObject: JsObject, weekDay: Int): Array[ClassPosition] = {
    (jsObject\"weekNumber").as[List[Int]].map(number => {
      val auditory = if ((jsObject\"auditory").as[List[String]].isEmpty) {
        ""
      }
      else {
        (jsObject\"auditory").as[List[String]].head
      }
      new ClassPosition(
        weekDay, number,
        (jsObject\"lessonTime").as[String],
        (jsObject\"numSubgroup").as[Int],
        auditory
      )
    }).toArray
  }
}
