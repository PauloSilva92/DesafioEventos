package com.example.desafioandroideventos.ui.details

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.desafioandroideventos.R
import com.example.desafioandroideventos.databinding.FragmentEventDetailsBinding
import com.example.desafioandroideventos.ui.events.EventsViewModel


class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: EventDetailsFragmentArgs by navArgs()
    private val viewModel: EventsViewModel by lazy {
        ViewModelProvider(this).get(EventsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val imageEvent: ImageView = view.findViewById(R.id.event_image)
        val uri = args.uri

        imageEvent.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                transitionName = uri
            }

            Glide.with(this)
                .load(uri)
                .error(R.drawable.placeholder)
                .into(this)
        }

        val eventId = args.eventId
        viewModel.getEventById(eventId).subscribe { event ->
            binding.eventDate.text = event.formattedDate
            binding.eventDate.visibility = View.VISIBLE
            binding.eventTitle.text = event.title
            binding.eventDescription.text = event.description
            binding.checkinButton.show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}