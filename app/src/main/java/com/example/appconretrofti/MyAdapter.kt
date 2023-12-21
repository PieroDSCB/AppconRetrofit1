package com.example.appconretrofti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appconretrofti.databinding.CardviewdogBinding
import com.squareup.picasso.Picasso

class MyAdapter(val images:List<String>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.cardviewdog,parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)


    }

    override fun getItemCount(): Int {
        return images.size
    }


    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val binding = CardviewdogBinding.bind(view)

        fun bind(image:String){
        //La libreria picasso hara que cogera la imagen ded internet url y lo pondra en el imagenView
            Picasso.get().load(image).into(binding.ivDog)

        }

    }

}