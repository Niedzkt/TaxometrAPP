package pl.wader.taxometrapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import pl.wader.taxometrapp.databinding.FragmentSecondBinding
import pl.wader.taxometrapp.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.SaveSettingsButton.setOnClickListener {
            if (binding.EntrySettingsPrice.text.isNullOrEmpty() || //sprawdzenie czy pole Enter jest Puste bądź NULL
                binding.DrivingSettingsPrice.text.isNullOrEmpty() || //sprawdzenie czy pole Enter jest Puste bądź NULL
                binding.WaitingSettingsPrice.text.isNullOrEmpty() //sprawdzenie czy pole Enter jest Puste bądź NULL
            ) {
                //pokaż toast z prośbą o wypełnienie wszystkich pól
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                //w innym wypadku...
                val EnterEditTextValue = binding.EntrySettingsPrice.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double
                val DrivingEditTextValue = binding.DrivingSettingsPrice.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double
                val WaitingEditTextValue = binding.WaitingSettingsPrice.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double

                mainVm.DailyEnter = EnterEditTextValue //Przekaż wartość do MainVm
                mainVm.DailyDriving = DrivingEditTextValue //Przekaż wartość do MainVm
                mainVm.DailyWaiting = WaitingEditTextValue //Przekaż wartość do MainVm

                Toast.makeText(context, "SAVED", Toast.LENGTH_LONG).show()
            }
        }
        binding.BackToMenuButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_firstFragment)
            true
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
