package uz.pdp.courseapp.ui.fragments.groups

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.GroupRVAdapter
import uz.pdp.courseapp.adapters.MentorSpinnerAdapter
import uz.pdp.courseapp.adapters.TimeSpinnerAdapter
import uz.pdp.courseapp.databinding.EditDialogBinding
import uz.pdp.courseapp.databinding.FragmentRVItemsBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.StudentsCount

private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class RVItemsFragment : Fragment() {
    lateinit var adapter: GroupRVAdapter
    lateinit var helper: DBHelper
    lateinit var groups: ArrayList<StudentsCount>
    lateinit var binding: FragmentRVItemsBinding
    lateinit var groupsList: ArrayList<Group>
    private var param2: Int? = null
    private var param3: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getInt(ARG_PARAM2)
            param3 = it.getInt(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRVItemsBinding.inflate(inflater, container, false)
        groups = ArrayList()
        helper = DBHelper(requireContext())
        return binding.root
    }

    override fun onResume() {
        groups.clear()
        when (param2) {
            0 -> {
                groupsList = helper.getAllOpenedGroups(param3!!)
                for (i in groupsList.indices) {
                    val group = groupsList[i]
                    val count =
                        helper.getAllStudents(group.courseId!!, group.mentorId!!, group.id!!).size
                    val studentsCount = StudentsCount(group, count)
                    groups.add(studentsCount)
                }
            }
            1 -> {
                groupsList = helper.getAllOpeningGroups(param3!!)
                for (i in groupsList.indices) {
                    val group = groupsList[i]
                    val count =
                        helper.getAllStudents(group.courseId!!, group.mentorId!!, group.id!!).size
                    val studentsCount = StudentsCount(group, count)
                    groups.add(studentsCount)
                }
            }
        }

        adapter = GroupRVAdapter(groups, object : GroupRVAdapter.ClickController {
            override fun see(group: Group, position: Int) {
                val bundle = Bundle()
                bundle.putInt("groupID", group.id!!)
                bundle.putInt("courseID", group.courseId!!)
                findNavController().navigate(R.id.groupInformationFragment, bundle)
            }

            override fun edit(group: Group, position: Int) {
                val timesList = arrayListOf("16:30 - 18:30", "19:00 - 21:00")
                var allMentors = helper.getAllMentors(group.courseId!!)
                val alertDialog = AlertDialog.Builder(requireContext())
                val dialogBinding = EditDialogBinding.inflate(layoutInflater, null, false)
                dialogBinding.groupName.setText(group.name)
                dialogBinding.mentorSpinner.adapter = MentorSpinnerAdapter(allMentors)
                val mentorById = helper.getMentorById(group.mentorId!!)
                for (i in allMentors.indices) {
                    if (allMentors[i].id == mentorById.id) {
                        dialogBinding.mentorSpinner.setSelection(i)
                        break
                    }
                }
                dialogBinding.timeSpinner.adapter =
                    TimeSpinnerAdapter(timesList)
                dialogBinding.timeSpinner.setSelection(timesList.indexOf(group.time))
                alertDialog.setView(dialogBinding.root)
                alertDialog.setPositiveButton(
                    "O'zgartirish",
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            if (dialogBinding.groupName.text.isNotBlank()) {
                                val name = dialogBinding.groupName.text.toString()
                                val mentorId =
                                    allMentors[dialogBinding.mentorSpinner.selectedItemPosition].id
                                val times =
                                    timesList[dialogBinding.timeSpinner.selectedItemPosition]
                                val group1 = Group(
                                    group.id,
                                    name,
                                    mentorId,
                                    group.courseId,
                                    times,
                                    group.isOpened
                                )
                                helper.updateGroup(group1)
                                notifyAdapter()
                            }
                        }
                    })
                alertDialog.setNegativeButton("Yopish", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                alertDialog.show()
            }

            override fun delete(group: Group, position: Int) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setTitle("Ushbu guruhni o’chirmoqchimisiz?")
                dialog.setCancelable(false)
                dialog.setPositiveButton(
                    "Xa"
                ) { dialog, which ->
                    helper.deleteGroup(group)
                    adapter.notifyItemRemoved(position)
                    adapter.notifyItemRangeChanged(position, adapter.itemCount)
                    notifyAdapter()
                }
                dialog.setNegativeButton(
                    "Yo'q"
                ) { dialog, which ->
                    dialog.dismiss()
                }
                dialog.show()
            }
        })
        binding.rv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter

        super.onResume()
    }

    companion object {
        fun newInstance(param2: Int, param3: Int) =
            RVItemsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM2, param2)
                    putInt(ARG_PARAM3, param3)
                }
            }
    }

    fun notifyAdapter() {
        groups.clear()
        groupsList.clear()
        when (param2) {
            0 -> {
                groupsList = helper.getAllOpenedGroups(param3!!)
                for (i in groupsList.indices) {
                    val group = groupsList[i]
                    val count =
                        helper.getAllStudents(group.courseId!!, group.mentorId!!, group.id!!).size
                    val studentsCount = StudentsCount(group, count)
                    groups.add(studentsCount)
                }
            }
            1 -> {
                groupsList = helper.getAllOpeningGroups(param3!!)
                for (i in groupsList.indices) {
                    val group = groupsList[i]
                    val count =
                        helper.getAllStudents(group.courseId!!, group.mentorId!!, group.id!!).size
                    val studentsCount = StudentsCount(group, count)
                    groups.add(studentsCount)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}