package pl.wader.taxometrapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import pl.wader.taxometrapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding //utworzenie prywatnej zmiennej ActivityMainBinding
    private val mainVM by viewModels<MainViewModel>()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater) //Dostęp do widoków za pomocą ID (dynamiczny dostęp z poziomu MainActivity)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // navController.navigate(R.id.action_firstFragment_to_secondFragment2)
        /*    binding.MyButton.setOnClickListener {
            val textData = binding.myEditText.text.toString()
            val explicitIntent = Intent(applicationContext,SecondActivity::class.java)
            explicitIntent.putExtra("TEXT_DATA", textData)
            startActivity(explicitIntent)
        }*/

        //adapter długiej listy dla createContacs
        // val adapter = myFirstAdapter(createContacts())
        // binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        // binding.recyclerView.adapter = adapter

    }
}