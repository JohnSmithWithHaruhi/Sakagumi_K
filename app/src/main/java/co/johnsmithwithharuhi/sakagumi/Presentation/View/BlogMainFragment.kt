package co.johnsmithwithharuhi.sakagumi.Presentation.View

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.johnsmithwithharuhi.sakagumi.Domain.GroupType
import co.johnsmithwithharuhi.sakagumi.R
import co.johnsmithwithharuhi.sakagumi.databinding.FragmentBlogMainBinding

class BlogMainFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val binding = DataBindingUtil.inflate<FragmentBlogMainBinding>(
        inflater, R.layout.fragment_blog_main, container, false
    )

    binding.run {
      blogMainViewPager
          .apply {
            adapter = createFragmentPagerAdapter(fragmentManager!!)
          }
          .let {
            blogMainTabLayout.setupWithViewPager(it)
          }
      blogMainTabLayout.setSelectedTabIndicatorHeight(3)
    }

    return binding.root
  }

  private fun createFragmentPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter =
    object : FragmentPagerAdapter(fragmentManager) {
      override fun getPageTitle(position: Int): CharSequence =
        when (convertPagePositionToType(position)) {
          GroupType.NOGI -> "乃木坂"
          GroupType.KEYA -> "欅坂"
          else -> "推しメン"
        }

      override fun getItem(position: Int): Fragment {
        return convertPagePositionToType(position).let {
          BlogPageFragment().newInstance(it)
        }
      }

      override fun getCount(): Int {
        return 3
      }

      private fun convertPagePositionToType(position: Int): GroupType {
        return when (position) {
          1 -> GroupType.NOGI
          2 -> GroupType.KEYA
          else -> GroupType.OSU
        }
      }
    }

}