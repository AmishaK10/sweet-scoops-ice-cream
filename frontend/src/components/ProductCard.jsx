import React from 'react';
import { formatCurrency } from '../utils/helpers';

function ProductCard({ product, onAddToCart, onViewDetails }) {
  return (
    <div className="product-card">
      <div className="image-wrap">
        <img 
          src={product.imageUrl || 'https://via.placeholder.com/500x500?text=Sweet+Scoops'} 
          alt={product.name} 
          onError={(e) => { e.currentTarget.src = 'https://via.placeholder.com/500x500?text=Sweet+Scoops'; }} 
        />
      </div>
      <div className="card-body">
        <div className="card-topline">
          <h3>{product.name}</h3>
          <span>{formatCurrency(product.price)}</span>
        </div>
        <p>{product.description}</p>
        <button className="primary-btn small-btn" onClick={() => onAddToCart(product)}>Add to cart</button>
      </div>
    </div>
  );
}

export default ProductCard;
