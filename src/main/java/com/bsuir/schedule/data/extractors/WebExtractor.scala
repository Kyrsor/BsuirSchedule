package com.bsuir.schedule.data.extractors

import com.bsuir.schedule.data.models.{GroupSchedule, Schedule, Teacher, TeacherSchedule}
import scalaj.http.Http
import play.api.libs.json._

class WebExtractor {

  val serializer: Serializer = new Serializer
  var list_of_groups: List[String] = List[String]()
  var list_of_teachers: List[String] = List[String]()
  var ids_of_teachers: List[Int] = List[Int]()

  def getListGroupsStartAt(startAt: String): List[String] = {
    if (list_of_groups.isEmpty) {
      val raw_json = Http("https://journal.bsuir.by/api/v1/groups").execute().body.toString
      val parsed = Json.parse(raw_json).as[List[JsObject]]
      list_of_groups = parsed.map(group => (group\"name").as[String])
      getListGroupsStartAt(startAt)
    } else {
      list_of_groups.filter(group => group.startsWith(startAt)).sorted
    }
  }

  def getListTeachersContaining(contain: String): List[String] = {
    if (list_of_teachers.isEmpty) {
      val raw_json = Http("https://journal.bsuir.by/api/v1/employees").execute().body.toString
      val parsed = Json.parse(raw_json).as[List[JsObject]]
      list_of_teachers = parsed.map(teacher =>
        (teacher\"lastName").as[String] + " " +
          (teacher\"firstName").as[String] + " " + (teacher\"middleName").as[String])
      ids_of_teachers = parsed.map(teacher =>
        (teacher\"id").as[Int])
      getListTeachersContaining(contain)
    } else {
      list_of_teachers.filter(group => group.contains(contain)).sorted
    }
  }

  def getCurrentWeek: Int = Http("https://journal.bsuir.by/api/v1/week").execute().body.toString.toInt

  def getScheduleOfGroup(group: String): GroupSchedule = {
    val request = Http("https://journal.bsuir.by/api/v1/studentGroup/schedule").
      param("studentGroup", group).execute().body.toString
    new GroupSchedule(
      serializer.getSchedule(request)
    )
  }

  def getIdOfTeacher(name: String, ids: List[Int], list_name: List[String], index: Int = 0): Int = {
    if (list_name(index) == name)
      ids(index)
    else
      getIdOfTeacher(name, ids, list_name, index + 1)
  }

  def getScheduleOfTeacher(teacher: String): TeacherSchedule = {
    val id = getIdOfTeacher(teacher, ids_of_teachers, list_of_teachers)
    getScheduleOfTeacher(id)
  }

  def getScheduleOfTeacher(id: Int): TeacherSchedule = {
    val request = Http("https://journal.bsuir.by/api/v1/portal/employeeSchedule?").param("employeeId", id.toString)
      .execute().body.toString
    new TeacherSchedule(
      serializer.getSchedule(request)
    )
  }

  def getScheduleOfTeacher(teacher: Teacher): TeacherSchedule = {
    getScheduleOfTeacher(teacher.name)
  }

  def getSchedule(who: Object): Schedule = {
    who match {
      case teacher: Teacher => getScheduleOfTeacher(teacher)
      case string: String =>
        if (string forall Character.isDigit) {
          getScheduleOfGroup(string)
        } else {
          getScheduleOfTeacher(string)
        }
      case Int => null
      case _ => null
    }
  }
}
