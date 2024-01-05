package uz.pdp.courseapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.pdp.courseapp.models.Course
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Mentor
import uz.pdp.courseapp.models.Student

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, Constant.DB_NAME, null, Constant.DB_VERSION), DBService {
    override fun onCreate(db: SQLiteDatabase?) {
        var contentValues = ContentValues()

        // course table
        var table1 =
            "CREATE TABLE ${Constant.COURSE_TABLE}(${Constant.COURSE_ID} integer not null primary key autoincrement unique, " +
                    "${Constant.COURSE_TITLE} text not null, " +
                    "${Constant.COURSE_TEXT} text not null)"
        db?.execSQL(table1)

        // mentor table
        var table2 =
            "CREATE TABLE ${Constant.MENTOR_TABLE}(${Constant.MENTOR_ID} integer not null primary key autoincrement unique, " +
                    "${Constant.MENTOR_NAME} text not null," +
                    "${Constant.MENTOR_SURNAME} text not null, " +
                    "${Constant.MENTOR_FNAME} text not null, " +
                    "${Constant.MENTOR_COURSE_ID} integer, " +
                    "FOREIGN KEY (${Constant.MENTOR_COURSE_ID}) REFERENCES ${Constant.COURSE_TABLE} (${Constant.COURSE_ID}))"
        db?.execSQL(table2)

        // group table
        var table3 =
            "CREATE TABLE ${Constant.GROUP_TABLE}(${Constant.GROUP_ID} integer not null primary key autoincrement unique, " +
                    "${Constant.GROUP_NAME} text not null, " +
                    "${Constant.GROUP_MENTOR_ID} integer, " +
                    "${Constant.GROUP_COURSE_ID} integer, " +
                    "${Constant.GROUP_TIME} text not null, " +
                    "${Constant.IS_OPENED} integer not null, " +
                    "FOREIGN KEY (${Constant.GROUP_MENTOR_ID}) REFERENCES ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID}), " +
                    "FOREIGN KEY (${Constant.GROUP_COURSE_ID}) REFERENCES ${Constant.COURSE_TABLE} (${Constant.COURSE_ID}))"
        db?.execSQL(table3)

        //student table
        var table4 =
            "CREATE TABLE ${Constant.STUDENT_TABLE}(${Constant.STUDENT_ID} integer not null primary key autoincrement unique, " +
                    "${Constant.STUDENT_NAME} text not null, " +
                    "${Constant.STUDENT_SURNAME} text not null, " +
                    "${Constant.STUDENT_FNAME} text not null, " +
                    "${Constant.STUDENT_DATE} text not null, " +
                    "${Constant.STUDENT_MENTOR_ID} integer, " +
                    "${Constant.STUDENT_GROUP_ID} integer, " +
                    "${Constant.STUDENT_COURSE_ID} integer, " +
                    "${Constant.STUDENT_TYPE_DAY} text not null, " +
                    "${Constant.STUDENT_TIME} text not null, " +
                    "FOREIGN KEY (${Constant.STUDENT_MENTOR_ID}) REFERENCES ${Constant.MENTOR_TABLE} (${Constant.MENTOR_ID}), " +
                    "FOREIGN KEY (${Constant.STUDENT_GROUP_ID}) REFERENCES ${Constant.GROUP_TABLE} (${Constant.GROUP_ID}), " +
                    "FOREIGN KEY (${Constant.STUDENT_COURSE_ID}) REFERENCES ${Constant.COURSE_TABLE} (${Constant.COURSE_ID}))"
        db?.execSQL(table4)


        contentValues.put(Constant.COURSE_TITLE, "Android Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Frontend Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Java Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Database Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Spring Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Python Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "iOS Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)

        contentValues.put(Constant.COURSE_TITLE, "Flutter Development")
        contentValues.put(
            Constant.COURSE_TEXT,
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean ac eros elit. Vestibulum luctus id orci a condimentum. Fusce id nunc in purus viverra consectetur."
        )
        db?.insert(Constant.COURSE_TABLE, null, contentValues)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${Constant.COURSE_TABLE}")
        db?.execSQL("DROP TABLE IF EXISTS ${Constant.MENTOR_TABLE}")
        db?.execSQL("DROP TABLE IF EXISTS ${Constant.GROUP_TABLE}")
        db?.execSQL("DROP TABLE IF EXISTS ${Constant.STUDENT_TABLE}")

        onCreate(db)
    }


    // courses
    override fun getAllCourses(): ArrayList<Course> {
        val database = this.readableDatabase
        var list = ArrayList<Course>()
        var query = "SELECT * from ${Constant.COURSE_TABLE}"
        var cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val title = cursor.getString(1)
                val text = cursor.getString(2)
                val course = Course(index, title, text)
                list.add(course)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getCourseById(id: Int): Course {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.COURSE_TABLE,
            arrayOf(Constant.COURSE_ID, Constant.COURSE_TITLE, Constant.COURSE_TEXT),
            "${Constant.COURSE_ID} = ?",
            arrayOf("$id"),
            null, null, null
        )

        cursor.moveToFirst()
        val index = cursor.getInt(0)
        val title = cursor.getString(1)
        val text = cursor.getString(2)
        val course = Course(index, title, text)
        return course
    }

    override fun insertCourse(course: Course) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.COURSE_TITLE, course.title)
        contentValues.put(Constant.COURSE_TEXT, course.text)
        database.insert(Constant.COURSE_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllGroupsByCourseAndMentor(courseId: Int, mentorId: Int): ArrayList<Group> {
        val database = this.readableDatabase
        val list = ArrayList<Group>()
        val query =
            "SELECT * FROM " + Constant.GROUP_TABLE + " WHERE " + Constant.GROUP_COURSE_ID + " = ? AND " + Constant.GROUP_MENTOR_ID + " = ?"
        val cursor = database.rawQuery(query, arrayOf("$courseId", "$mentorId"))
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val name = cursor.getString(1)
                val mentorID = cursor.getInt(2)
                val courseID = cursor.getInt(3)
                val time = cursor.getString(4)
                val bool = cursor.getInt(5)
                var group = Group()
                group = if (bool == 0) {
                    Group(index, name, mentorID, courseID, time, false)
                } else {
                    Group(index, name, mentorID, courseID, time, true)
                }
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    // groupsCountLis
    override fun getAllOpenedGroups(courseId: Int): ArrayList<Group> {
        val database = this.readableDatabase
        val list = ArrayList<Group>()
        val query =
            "SELECT * FROM " + Constant.GROUP_TABLE + " WHERE " + Constant.GROUP_COURSE_ID + " = ? AND " + Constant.IS_OPENED + " = ?"
        val cursor = database.rawQuery(query, arrayOf("$courseId", "1"))
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val name = cursor.getString(1)
                val mentorID = cursor.getInt(2)
                val courseID = cursor.getInt(3)
                val time = cursor.getString(4)
                var group = Group(index, name, mentorID, courseID, time, true)
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllOpeningGroups(courseId: Int): ArrayList<Group> {
        val database = this.readableDatabase
        val list = ArrayList<Group>()
        val query =
            "SELECT * FROM " + Constant.GROUP_TABLE + " WHERE " + Constant.GROUP_COURSE_ID + " = ? AND " + Constant.IS_OPENED + " = ?"
        val cursor = database.rawQuery(query, arrayOf("$courseId", "1"))
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val name = cursor.getString(1)
                val mentorID = cursor.getInt(2)
                val courseID = cursor.getInt(3)
                val time = cursor.getString(4)
                var group = Group(index, name, mentorID, courseID, time, false)
                list.add(group)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun insertGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentorId)
        contentValues.put(Constant.GROUP_COURSE_ID, group.courseId)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.IS_OPENED, 0)

        database.insert(Constant.GROUP_TABLE, null, contentValues)
        database.close()
    }

    override fun updateGroup(group: Group) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.GROUP_NAME, group.name)
        contentValues.put(Constant.GROUP_MENTOR_ID, group.mentorId)
        contentValues.put(Constant.GROUP_COURSE_ID, group.courseId)
        contentValues.put(Constant.GROUP_TIME, group.time)
        contentValues.put(Constant.IS_OPENED, group.isOpened)

        database.update(
            Constant.GROUP_TABLE,
            contentValues,
            "${Constant.GROUP_ID} = ?",
            arrayOf("${group.id}")
        )
        database.close()
    }

    override fun deleteGroup(group: Group) {
        val database = this.writableDatabase
        database.delete(Constant.GROUP_TABLE, "${Constant.GROUP_ID} = ?", arrayOf("${group.id}"))
        database.close()
    }

    override fun getGroupById(id: Int): Group {
        val database = this.readableDatabase
        val cursor = database.query(
            Constant.GROUP_TABLE,
            arrayOf(
                Constant.GROUP_ID,
                Constant.GROUP_NAME,
                Constant.GROUP_MENTOR_ID,
                Constant.GROUP_COURSE_ID,
                Constant.GROUP_TIME,
                Constant.IS_OPENED
            ),
            "${Constant.GROUP_ID} = ?",
            arrayOf("$id"),
            null, null, null
        )

        cursor.moveToFirst()
        val index = cursor.getInt(0)
        val name = cursor.getString(1)
        val mentorID = cursor.getInt(2)
        val courseID = cursor.getInt(3)
        val time = cursor.getString(4)
        val isOpened = cursor.getInt(5)
        var boolean = false

        if (isOpened == 1) {
            boolean = true
        }
        val group = Group(index, name, mentorID, courseID, time, boolean)
        return group
    }


    // mentor
    override fun getAllMentors(courseId: Int): ArrayList<Mentor> {
        val readableDatabase = this.readableDatabase
        val list = ArrayList<Mentor>()
        val query =
            "SELECT * FROM ${Constant.MENTOR_TABLE} WHERE ${Constant.MENTOR_COURSE_ID} = $courseId"
        val cursor = readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val name = cursor.getString(1)
                val surname = cursor.getString(2)
                val fathersName = cursor.getString(3)
                val courseID = cursor.getInt(4)
                val mentor = Mentor(index, name, surname, fathersName, courseID)
                list.add(mentor)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun insertMentor(mentor: Mentor) {
        val writableDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.MENTOR_NAME, mentor.name)
        contentValues.put(Constant.MENTOR_SURNAME, mentor.surname)
        contentValues.put(Constant.MENTOR_FNAME, mentor.fathersName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.courseId)
        writableDatabase.insert(Constant.MENTOR_TABLE, null, contentValues)
        writableDatabase.close()
    }

    override fun updateMentor(mentor: Mentor) {
        val writableDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.MENTOR_NAME, mentor.name)
        contentValues.put(Constant.MENTOR_SURNAME, mentor.surname)
        contentValues.put(Constant.MENTOR_FNAME, mentor.fathersName)
        contentValues.put(Constant.MENTOR_COURSE_ID, mentor.courseId)

        writableDatabase.update(
            Constant.MENTOR_TABLE,
            contentValues,
            "${Constant.MENTOR_ID} = ?",
            arrayOf("${mentor.id}")
        )
        writableDatabase.close()
    }

    override fun deleteMentor(mentor: Mentor) {
        val writableDatabase = this.writableDatabase
        writableDatabase.delete(
            Constant.MENTOR_TABLE,
            "${Constant.MENTOR_ID} = ?",
            arrayOf("${mentor.id}")
        )
        writableDatabase.close()
    }

    override fun getMentorById(id: Int): Mentor {
        val readableDatabase = this.readableDatabase
        val cursor = readableDatabase.query(
            Constant.MENTOR_TABLE,
            arrayOf(
                Constant.MENTOR_ID,
                Constant.MENTOR_NAME,
                Constant.MENTOR_SURNAME,
                Constant.MENTOR_FNAME,
                Constant.MENTOR_COURSE_ID
            ),
            "${Constant.MENTOR_ID} = ?",
            arrayOf("$id"),
            null, null, null
        )

        cursor.moveToFirst()
        val index = cursor.getInt(0)
        val name = cursor.getString(1)
        val surname = cursor.getString(2)
        val fathersName = cursor.getString(3)
        val courseID = cursor.getInt(4)
        val mentor = Mentor(index, name, surname, fathersName, courseID)

        return mentor
    }

    // student
    override fun getAllStudents(courseId: Int, mentorId: Int, groupId: Int): ArrayList<Student> {
        val readableDatabase = this.readableDatabase
        val list = ArrayList<Student>()
        val query =
            "SELECT * FROM ${Constant.STUDENT_TABLE} WHERE ${Constant.STUDENT_COURSE_ID} = $courseId AND ${Constant.STUDENT_MENTOR_ID} = $mentorId AND ${Constant.STUDENT_GROUP_ID} = $groupId"
        val cursor = readableDatabase.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val index = cursor.getInt(0)
                val name = cursor.getString(1)
                val surname = cursor.getString(2)
                val fathersName = cursor.getString(3)
                val regDate = cursor.getString(4)
                val mentorID = cursor.getInt(5)
                val groupID = cursor.getInt(6)
                val courseID = cursor.getInt(7)
                val typesOfDay = cursor.getString(8)
                val typesOfTime = cursor.getString(9)
                val student = Student(
                    index,
                    name,
                    surname,
                    fathersName,
                    regDate,
                    mentorID,
                    groupID,
                    courseID,
                    typesOfDay,
                    typesOfTime
                )
                list.add(student)
            } while (cursor.moveToNext())
        }


        return list
    }

    override fun insertStudent(student: Student) {
        val writableDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.STUDENT_NAME, student.name)
        contentValues.put(Constant.STUDENT_SURNAME, student.surname)
        contentValues.put(Constant.STUDENT_FNAME, student.fathersName)
        contentValues.put(Constant.STUDENT_DATE, student.regDate)
        contentValues.put(Constant.STUDENT_MENTOR_ID, student.mentorId)
        contentValues.put(Constant.STUDENT_GROUP_ID, student.groupId)
        contentValues.put(Constant.STUDENT_COURSE_ID, student.courseId)
        contentValues.put(Constant.STUDENT_TYPE_DAY, student.typesOfDay)
        contentValues.put(Constant.STUDENT_TIME, student.typesOfTime)

        writableDatabase.insert(Constant.STUDENT_TABLE, null, contentValues)
        writableDatabase.close()
    }

    override fun updateStudent(student: Student) {
        val writableDatabase = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(Constant.STUDENT_NAME, student.name)
        contentValues.put(Constant.STUDENT_SURNAME, student.surname)
        contentValues.put(Constant.STUDENT_FNAME, student.fathersName)
        contentValues.put(Constant.STUDENT_DATE, student.regDate)
        contentValues.put(Constant.STUDENT_MENTOR_ID, student.mentorId)
        contentValues.put(Constant.STUDENT_GROUP_ID, student.groupId)
        contentValues.put(Constant.STUDENT_COURSE_ID, student.courseId)
        contentValues.put(Constant.STUDENT_TYPE_DAY, student.typesOfDay)
        contentValues.put(Constant.STUDENT_TIME, student.typesOfTime)

        writableDatabase.update(
            Constant.STUDENT_TABLE,
            contentValues,
            "${Constant.STUDENT_ID} = ?",
            arrayOf("${student.id}")
        )
        writableDatabase.close()
    }

    override fun deleteStudent(student: Student) {
        val writableDatabase = this.writableDatabase
        writableDatabase.delete(
            Constant.STUDENT_TABLE,
            "${Constant.STUDENT_ID} = ?",
            arrayOf("${student.id}")
        )
        writableDatabase.close()
    }

    override fun getStudentById(id: Int): Student {
        val readableDatabase = this.readableDatabase
        val cursor = readableDatabase.query(
            Constant.STUDENT_TABLE,
            arrayOf(
                Constant.STUDENT_ID,
                Constant.STUDENT_NAME,
                Constant.STUDENT_SURNAME,
                Constant.STUDENT_FNAME,
                Constant.STUDENT_DATE,
                Constant.STUDENT_MENTOR_ID,
                Constant.STUDENT_GROUP_ID,
                Constant.STUDENT_COURSE_ID,
                Constant.STUDENT_TYPE_DAY,
                Constant.STUDENT_TIME
            ),
            "${Constant.STUDENT_ID} = ?",
            arrayOf("$id"),
            null, null, null
        )

        cursor.moveToFirst()
        val index = cursor.getInt(0)
        val name = cursor.getString(1)
        val surname = cursor.getString(2)
        val fathersName = cursor.getString(3)
        val regDate = cursor.getString(4)
        val mentorID = cursor.getInt(5)
        val groupID = cursor.getInt(6)
        val courseID = cursor.getInt(7)
        val typesOfDay = cursor.getString(8)
        val typesOfTime = cursor.getString(9)

        return Student(
            index,
            name,
            surname,
            fathersName,
            regDate,
            mentorID,
            groupID,
            courseID,
            typesOfDay,
            typesOfTime
        )
    }
}