package uz.pdp.courseapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.pdp.courseapp.models.Tab
import uz.pdp.courseapp.ui.fragments.groups.RVItemsFragment

class VPAdapter(var list: List<Tab>, var courseID: Int, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        var fragment = RVItemsFragment.newInstance(position, courseID)
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list[position].title
    }
}