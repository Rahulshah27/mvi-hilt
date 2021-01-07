package shah.rahul.mvihilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import shah.rahul.mvihilt.R
import shah.rahul.mvihilt.databinding.ActivityMainBinding
import shah.rahul.mvihilt.model.Blog
import shah.rahul.mvihilt.util.DataState
import java.lang.StringBuilder


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val TAG: String = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState->
            when(dataState) {
                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)

                }
            }
        })
    }

    private fun displayError(message: String?) {
        if (message != null ) {
            binding.text.text = message
        }
        else {
            binding.text.text = "Unknown Error"
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.progressBar.visibility = if(isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<Blog>){
        val sb = StringBuilder()
        for (blog in blogs) {
            sb.append(blog.title + "\n\n")
        }
        binding.text.text = sb.toString()
    }
}