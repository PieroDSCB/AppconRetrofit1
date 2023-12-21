package com.example.appconretrofti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appconretrofti.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private val dogImages = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lupa.setOnQueryTextListener(this)

        iniciarRecyclerview()

    }

    private fun iniciarRecyclerview() {
        adapter = MyAdapter(dogImages)
        binding.recy.adapter= adapter
        binding.recy.layoutManager = LinearLayoutManager(this)



    }


    private fun getRetrofit():Retrofit{

        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")/*La url fija de la api*/
            .addConverterFactory(GsonConverterFactory.create())//La libreria de Gson converter retrofit
            .build()

    }

    /*METODO ECORRTUINAS*7
     */

    private fun searchByName(query:String){

        CoroutineScope(Dispatchers.IO).launch {
            /**TODX LO QUE HAGAMOS AQUI SE HARA EN UNN HILO SECUNDARIO*/

        val call = getRetrofit().create(ApiService::class.java).getDogsByBreeds("$query/images")/*el.get es la interfaz creada */
        val puppies= call.body() /*osea la respuesta obtenida en call lo guardaremos en la variables puppies */

            /*Para volver al hilo principal apra que se vea el recycler view hecho en el otor hilo se hara lo siguiente*/
            runOnUiThread {

                //todx lo que ejecutemos aca dentro se hara en el hilo principal
                if(call.isSuccessful){
                    dogImages.clear()
                    val images = puppies?.image?: emptyList()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
                hidKeyboard()
            }


        }
    }

    /*Metodo para ocultar los teclado cuadno busques una imagen*/
    private fun hidKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE)as InputMethodManager
        imm.hideSoftInputFromWindow(binding.MainVista.windowToken,0)
    }

    private fun showError(){
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
    return true
    }


}


