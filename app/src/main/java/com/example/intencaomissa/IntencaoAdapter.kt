package com.example.intencaomissa

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intencaomissa.databinding.ItemIntencaoBinding

class IntencaoAdapter(
    private var lista: MutableList<Intencao>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<IntencaoAdapter.IntencaoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(intencao: Intencao)
        fun onItemLongClick(intencao: Intencao): Boolean
    }

    inner class IntencaoViewHolder(val binding: ItemIntencaoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntencaoViewHolder {
        val binding = ItemIntencaoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntencaoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntencaoViewHolder, position: Int) {
        val item = lista[position]
        holder.binding.tvNome.text = item.nome
        holder.binding.tvTipoData.text = "${item.tipo} • ${item.data}"
        holder.binding.tvIntencao.text = item.intencao

        holder.binding.root.setOnClickListener {
            listener.onItemClick(item)
        }

        holder.binding.root.setOnLongClickListener {
            listener.onItemLongClick(item)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun updateList(nova: List<Intencao>) {
        lista.clear()
        lista.addAll(nova)
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        if (position in 0 until lista.size) {
            lista.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

