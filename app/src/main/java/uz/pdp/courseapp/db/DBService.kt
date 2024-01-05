package uz.pdp.courseapp.db

import uz.pdp.courseapp.models.Course
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Mentor
import uz.pdp.courseapp.models.Student

interface DBService {

    //course
    fun getAllCourses(): ArrayList<Course>
    fun getCourseById(id: Int): Course
    fun insertCourse(course: Course)

    //group
    fun getAllGroupsByCourseAndMentor(courseId: Int, mentorId: Int): ArrayList<Group>
    fun getAllOpenedGroups(courseId: Int): ArrayList<Group>
    fun getAllOpeningGroups(courseId: Int): ArrayList<Group>
    fun insertGroup(group: Group)
    fun updateGroup(group: Group)
    fun deleteGroup(group: Group)
    fun getGroupById(id: Int): Group

    //mentor
    fun getAllMentors(courseId: Int): ArrayList<Mentor>
    fun insertMentor(mentor: Mentor)
    fun updateMentor(mentor: Mentor)
    fun deleteMentor(mentor: Mentor)
    fun getMentorById(id: Int): Mentor

    //student
    fun getAllStudents(courseId: Int, mentorId: Int, groupId: Int): ArrayList<Student>
    fun insertStudent(student: Student)
    fun updateStudent(student: Student)
    fun deleteStudent(student: Student)
    fun getStudentById(id: Int): Student
}