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
import java.io.File

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //nasłuchiwanie na kliknięcie na klawisz
        //pokaż toast z prośbą o wypełnienie wszystkich pól
        var EnterEditTextValue = mainVm.DailyEnter.toString()
        var DrivingEditTextValue = mainVm.DailyDriving.toString()
        var WaitingEditTextValue = mainVm.DailyWaiting.toString()

        binding.EnterEditText.setText(EnterEditTextValue)
        binding.DrivingEditText.setText(DrivingEditTextValue)
        binding.WaitingEditText.setText(WaitingEditTextValue)

        binding.StartTaxiButton.setOnClickListener {
            if (binding.EnterEditText.text.isNullOrEmpty() || //sprawdzenie czy pole Enter jest Puste bądź NULL
                binding.DrivingEditText.text.isNullOrEmpty() || //sprawdzenie czy pole Enter jest Puste bądź NULL
                binding.WaitingEditText.text.isNullOrEmpty() //sprawdzenie czy pole Enter jest Puste bądź NULL
            ) {
                //w innym wypadku...
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else if(mainVm.DailyDriving <= 0 ||
                    mainVm.DailyWaiting <=0 ||
                    mainVm.DailyEnter <=0) {

                val Enter1EditTextValue = binding.EnterEditText.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double
                val Driving1EditTextValue = binding.DrivingEditText.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double
                val Waiting1EditTextValue = binding.WaitingEditText.text.toString().toDouble() //pobierz wartość z EditText i przekształć ją ze string w Double

                mainVm.DailyEnter = Enter1EditTextValue //Przekaż wartość do MainVm
                mainVm.DailyDriving = Driving1EditTextValue //Przekaż wartość do MainVm
                mainVm.DailyWaiting = Waiting1EditTextValue //Przekaż wartość do MainVm


                findNavController().navigate(R.id.action_secondFragment2_to_dailyDrivingFragment) //zmień scenę i przeskocz na następny fragment
                true

            }
            else{
                findNavController().navigate(R.id.action_secondFragment2_to_dailyDrivingFragment) //zmień scenę i przeskocz na następny fragment
                true
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}