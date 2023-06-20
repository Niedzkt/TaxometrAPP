package pl.wader.taxometrapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import pl.wader.taxometrapp.databinding.FragmentDailyWaitingBinding

class DailyWaitingFragment : Fragment() {

    //obejście nie działającego bindingu we fragmentach, teraz działą
    private var _binding: FragmentDailyWaitingBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>() //zainicjowanie ViewModel

    private var elapsedTime: Int = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object: Runnable {
        override fun run() {
            elapsedTime++
            binding.TimeTextView.text = elapsedTime.toString()
            handler.postDelayed(this, 1000) // run every second
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDailyWaitingBinding.inflate(layoutInflater, container, false) //rozdmuchanie bindingu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.StartWaitingButton.setOnClickListener {
            handler.postDelayed(runnable, 1000) // start timer
        }

        binding.StopTimerButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            val PriceWaiting = mainVm.DailyWaiting
            mainVm.TempDailyWaiting = elapsedTime.toDouble()
        }

        binding.BackToDrivingButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyWaitingFragment_to_dailyDrivingFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable) // stop timer
        _binding = null
    }
}