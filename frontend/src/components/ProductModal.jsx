import React from 'react';
import { formatCurrency } from '../utils/helpers';

function ProductModal({ product, onClose, onAddToCart }) {
  if (!product) return null;

  return (
    <div className="modal-backdrop" onClick={onClose}>
      <div className="modal-card" onClick={(e) => e.stopPropagation()}>
        <button className="close-btn" onClick={onClose}>×</button>
        <div className="modal-layout">
          <img 
            src={product.imageUrl || 'https://via.placeholder.com/500x500?text=Sweet+Scoops'} 
            alt={product.name} 
            onError={(e) => { e.currentTarget.src = 'https://via.placeholder.com/500x500?text=Sweet+Scoops'; }} 
          />
          <div>
            <span className="eyebrow">{product.category}</span>
            <h3>{product.name}</h3>
            <p>{product.description}</p>
            <div className="price-line">
              <strong>{formatCurrency(product.price)}</strong>
              <span className={product.available ? 'status available' : 'status unavailable'}>
                {product.available ? 'Available now' : 'Unavailable'}
              </span>
            </div>
            <button className="primary-btn" onClick={() => { onAddToCart(product); onClose(); }}>Add to cart</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductModal;
