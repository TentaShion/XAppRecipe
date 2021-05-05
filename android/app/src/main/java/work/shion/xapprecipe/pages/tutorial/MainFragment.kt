package work.shion.xapprecipe.pages.tutorial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import work.shion.xapprecipe.NavEntrypointDirections
import work.shion.xapprecipe.R
import work.shion.xapprecipe.databinding.PagesTutorialBinding

/**
 * チュートリアル
 */
class MainFragment : Fragment() {

    private var binding: PagesTutorialBinding? = null
    private val model = MainModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = PagesTutorialBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = binding ?: return

        // 「次へ」ボタン
        root.pagesTutorialActionNext.setOnClickListener {
            binding?.pagesTutorialPager?.apply {
                currentItem += 1
            }
        }

        // 「スキップ」ボタン
        root.pagesTutorialActionSkip.setOnClickListener {
            activity?.let { Navigation.findNavController(it, R.id.entrypoint) }
                ?.navigate(NavEntrypointDirections.navactToTop())
        }

        // 「はじめる」ボタン
        root.pagesTutorialActionStart.setOnClickListener {
            activity?.let { Navigation.findNavController(it, R.id.entrypoint) }
                ?.navigate(NavEntrypointDirections.navactToTop())
        }

        // ページャー関連
        val adapter = MainAdapter(this@MainFragment)
        adapter.displayData = model.loadContents()
        root.pagesTutorialPager.apply {
            this.adapter = adapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val length = binding?.pagesTutorialPager?.adapter?.itemCount ?: 0
                    val isLastContent = length - 1 <= position
                    binding?.pagesTutorialActionNext?.isVisible = !isLastContent
                    binding?.pagesTutorialActionSkip?.isVisible = !isLastContent
                    binding?.pagesTutorialActionStart?.isVisible = isLastContent
                }
            })
        }
        TabLayoutMediator(root.pagesTutorialIndicator.tabs, root.pagesTutorialPager) { _, _ ->
        }.attach()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
