package uz.pdp.courseapp.db

object Constant {
    const val DB_NAME = "course_db"
    const val DB_VERSION = 1

    // course table
    const val COURSE_TABLE = "course"
    const val COURSE_ID = "course_id"
    const val COURSE_TITLE = "course_title"
    const val COURSE_TEXT = "course_text"

    // group table
    const val GROUP_TABLE = "group_table"
    const val GROUP_ID = "group_id"
    const val GROUP_NAME = "group_name"
    const val GROUP_MENTOR_ID = "mentor_id"
    const val GROUP_COURSE_ID = "course_id"
    const val IS_OPENED = "isOpened"
    const val GROUP_TIME = "group_time"

    // mentor table
    const val MENTOR_TABLE = "mentor"
    const val MENTOR_ID = "mentor_id"
    const val MENTOR_NAME = "mentor_name"
    const val MENTOR_SURNAME = "mentor_surname"
    const val MENTOR_FNAME = "mentor_fathers_name"
    const val MENTOR_COURSE_ID = "course_id"

    // student table
    const val STUDENT_TABLE = "student"
    const val STUDENT_ID = "student_id"
    const val STUDENT_NAME = "student_name"
    const val STUDENT_SURNAME = "student_surname"
    const val STUDENT_FNAME = "student_fathers_name"
    const val STUDENT_DATE = "student_date"
    const val STUDENT_MENTOR_ID = "mentor_id"
    const val STUDENT_GROUP_ID = "group_id"
    const val STUDENT_COURSE_ID = "course_id"
    const val STUDENT_TYPE_DAY = "type_of_day"
    const val STUDENT_TIME = "student_time"
}