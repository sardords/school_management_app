package uz.pdp.courseapp.models

class Course {
    var id: Int? = null
    var title: String? = null
    var text: String? = null

    constructor(id: Int?, title: String?, text: String?) {
        this.id = id
        this.title = title
        this.text = text
    }

    constructor(title: String?, text: String?) {
        this.title = title
        this.text = text
    }

    constructor()


}