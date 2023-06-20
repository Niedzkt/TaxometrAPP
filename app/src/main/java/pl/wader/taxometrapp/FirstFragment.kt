package pl.wader.taxometrapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pl.wader.taxometrapp.databinding.FragmentFirstBinding
import pl.wader.taxometrapp.databinding.FragmentSecondBinding

class FirstFragment : Fragment() {

    //obejście nie działającego bindingu we fragmentach, teraz działą
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(layoutInflater, container, false) //rozdmuchanie bindingu
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setOnClickListener - po kliknięciu wywołuje akcje
        binding.DayTaxiButton.setOnClickListener {
            //do nawigacji wzywamy findNavController().navigate(R.id."stworzona akcja/fragment/activity")
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment2)
            true
        }
        binding.SettingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_settingsFragment)
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}