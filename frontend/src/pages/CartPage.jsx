import React from 'react';
import { formatCurrency } from '../utils/helpers';

function CartPage({ cart, onChangeQuantity, onRemoveItem, onNavigate }) {
  const subtotal = cart.reduce((total, item) => total + Number(item.price) * Number(item.quantity), 0);
  const delivery = 45;
  const total = subtotal + delivery;

  return (
    <section className="section-shell cart-page">
      <div className="section-heading">
        <span className="eyebrow">Your order</span>
        <h2>Shopping cart</h2>
      </div>

      {cart.length === 0 ? (
        <div className="state-box">Your cart is empty. Add a few scoops to start ordering.</div>
      ) : (
        <div className="cart-layout">
          <div className="cart-items">
            {cart.map((item) => (
              <div className="cart-item" key={item.id}>
                <img 
                  src={item.imageUrl || 'https://via.placeholder.com/500x500?text=Sweet+Scoops'} 
                  alt={item.name} 
                  onError={(e) => { e.currentTarget.src = 'https://via.placeholder.com/500x500?text=Sweet+Scoops'; }} 
                />
                <div className="cart-item-info">
                  <h3>{item.name}</h3>
                  <p>{formatCurrency(item.price)} each</p>
                </div>
                <div className="quantity-control">
                  <button onClick={() => onChangeQuantity(item.id, -1)}>-</button>
                  <span>{item.quantity}</span>
                  <button onClick={() => onChangeQuantity(item.id, 1)}>+</button>
                </div>
                <div className="line-total">{formatCurrency(item.price * item.quantity)}</div>
                <button className="remove-btn" onClick={() => onRemoveItem(item.id)}>Remove</button>
              </div>
            ))}
          </div>

          <aside className="cart-summary">
            <h3>Order summary</h3>
            <div className="summary-row"><span>Subtotal</span><strong>{formatCurrency(subtotal)}</strong></div>
            <div className="summary-row"><span>Delivery</span><strong>{formatCurrency(delivery)}</strong></div>
            <div className="summary-row total-row"><span>Total</span><strong>{formatCurrency(total)}</strong></div>
            <button className="primary-btn full-width" onClick={() => onNavigate('checkout')}>Proceed to Checkout</button>
          </aside>
        </div>
      )}
    </section>
  );
}

export default CartPage;
