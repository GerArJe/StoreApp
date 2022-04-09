package com.example.storeapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.R
import com.example.storeapp.databinding.ProductItemBinding
import com.example.storeapp.model.entity.Product

class ProductAdapter(private var products: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var onItemClickListener: ((Product) -> Unit)? = null
    var onItemLongClickListener: ((Product) -> Unit)? = null

    fun refresh(products: ArrayList<Product>) {
        this.products = products
        notifyDataSetChanged()
    }

    class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            product: Product,
            onItemClickListener: ((Product) -> Unit)?,
            onItemLongClickListener: ((Product) -> Unit)?
        ) {
            binding.product = product

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(product)
                }
            }

            binding.root.setOnLongClickListener {
                onItemLongClickListener?.let {
                    it(product)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding: ProductItemBinding =
            DataBindingUtil.inflate(inflate, R.layout.product_item, parent, false)

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(products[position], onItemClickListener, onItemLongClickListener)
    }

    override fun getItemCount(): Int = products.size
}