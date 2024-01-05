package uz.pdp.courseapp.models

import java.util.*

class Student {
    var id: Int? = null
    var name: String? = null
    var surname: String? = null
    var fathersName: String? = null
    var regDate: String? = null
    var mentorId: Int? = null
    var groupId: Int? = null
    var courseId: Int? = null
    var typesOfDay: String? = null
    var typesOfTime: String? = null

    constructor(
        id: Int?,
        name: String?,
        surname: String?,
        fathersName: String?,
        regDate: String?,
        mentorId: Int?,
        groupId: Int?,
        courseId: Int?,
        typesOfDay: String?,
        typesOfTime: String?
    ) {
        this.id = id
        this.name = name
        this.surname = surname
        this.fathersName = fathersName
        this.regDate = regDate
        this.mentorId = mentorId
        this.groupId = groupId
        this.courseId = courseId
        this.typesOfDay = typesOfDay
        this.typesOfTime = typesOfTime
    }

    constructor(
        name: String?,
        surname: String?,
        fathersName: String?,
        regDate: String?,
        mentorId: Int?,
        groupId: Int?,
        courseId: Int?,
        typesOfDay: String?,
        typesOfTime: String?
    ) {
        this.name = name
        this.surname = surname
        this.fathersName = fathersName
        this.regDate = regDate
        this.mentorId = mentorId
        this.groupId = groupId
        this.courseId = courseId
        this.typesOfDay = typesOfDay
        this.typesOfTime = typesOfTime
    }

    constructor()
}