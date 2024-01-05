package uz.pdp.courseapp.models

class Mentor {
    var id: Int? = null
    var name: String? = null
    var surname: String? = null
    var fathersName: String? = null
    var courseId: Int? = null

    constructor(id: Int?, name: String?, surname: String?, fathersName: String?, courseId: Int?) {
        this.id = id
        this.name = name
        this.surname = surname
        this.fathersName = fathersName
        this.courseId = courseId
    }

    constructor(name: String?, surname: String?, fathersName: String?, courseId: Int?) {
        this.name = name
        this.surname = surname
        this.fathersName = fathersName
        this.courseId = courseId
    }

    constructor()
}