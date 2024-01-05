package uz.pdp.courseapp.models

class Group {
    var id: Int? = null
    var name: String? = null
    var mentorId: Int? = null
    var courseId: Int? = null
    var time: String? = null
    var isOpened: Boolean? = null

    constructor(
        id: Int?,
        name: String?,
        mentorId: Int?,
        courseId: Int?,
        time: String?,
        isOpened: Boolean?
    ) {
        this.id = id
        this.name = name
        this.mentorId = mentorId
        this.courseId = courseId
        this.time = time
        this.isOpened = isOpened
    }

    constructor(name: String?, mentorId: Int?, courseId: Int?, time: String?, isOpened: Boolean?) {
        this.name = name
        this.mentorId = mentorId
        this.courseId = courseId
        this.time = time
        this.isOpened = isOpened
    }

    constructor()
}