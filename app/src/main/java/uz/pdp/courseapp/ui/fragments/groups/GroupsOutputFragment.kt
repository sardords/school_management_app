package uz.pdp.courseapp.ui.fragments.groups

import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import uz.pdp.courseapp.R
import uz.pdp.courseapp.adapters.VPAdapter
import uz.pdp.courseapp.databinding.FragmentGroupsOutputBinding
import uz.pdp.courseapp.db.DBHelper
import uz.pdp.courseapp.models.Group
import uz.pdp.courseapp.models.Tab
import uz.pdp.courseapp.ui.activity.MainActivity


class GroupsOutputFragment : Fragment() {
    lateinit var binding: FragmentGroupsOutputBinding
    lateinit var helper: DBHelper
    lateinit var openedGroupsIds: ArrayList<Int>
    lateinit var openingGroupsIds: ArrayList<Int>
    lateinit var openedGroups: ArrayList<Group>
    lateinit var openingGroups: ArrayList<Group>
    lateinit var tabList: ArrayList<Tab>
    lateinit var viewPagerAdapter: VPAdapter
    private var courseID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentGroupsOutputBinding.inflate(inflater, container, false)
        courseID = arguments?.getInt("groupID")
        helper = DBHelper(requireContext())
        openedGroupsIds = ArrayList()
        openingGroupsIds = ArrayList()
        tabList = ArrayList()

        val sentCourse = helper.getCourseById(courseID!!)

        openedGroups = helper.getAllOpenedGroups(courseID!!)
        for (i in openedGroups.indices) {
            val index = openedGroups[i].id
            openedGroupsIds.add(index!!)
        }

        openingGroups = helper.getAllOpeningGroups(courseID!!)
        openingGroups.size
        for (i in openingGroups.indices) {
            val index = openingGroups[i].id
            openingGroupsIds.add(index!!)
        }

        binding.toolbar.title = sentCourse.title
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        tabList.add(Tab("Ochilgan guruhlar", openedGroupsIds))
        tabList.add(Tab("Ochilayotgan guruhlar", openingGroupsIds))

        val mainActivity = requireActivity() as MainActivity
        mainActivity.setSupportActionBar(binding.toolbar)
        return binding.root
    }

    override fun onResume() {
        viewPagerAdapter = VPAdapter(tabList, courseID!!, childFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                invalidateOptionsMenu(requireActivity())
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (binding.viewPager.currentItem == 1) {
            menu.findItem(R.id.addBtn).setVisible(true)
        } else {
            menu.findItem(R.id.addBtn).setVisible(false)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        when (itemId) {
            R.id.addBtn -> {
                val bundle = Bundle()
                bundle.putInt("courseID", courseID!!)
                findNavController().navigate(R.id.addGroupFragment, bundle)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}