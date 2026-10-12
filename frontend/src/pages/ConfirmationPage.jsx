import React from 'react';
import { formatCurrency } from '../utils/helpers';

function ConfirmationPage({ order, onNavigate }) {
  if (!order) {
    return (
      <section className="section-shell confirmation-page">
        <div className="confirmation-card">
          <p>No order information available.</p>
          <button className="primary-btn" onClick={() => onNavigate('menu')}>Continue shopping</button>
        </div>
      </section>
    );
  }

  return (
    <section className="section-shell confirmation-page">
      <div className="confirmation-card">
        <span className="eyebrow">Order confirmed</span>
        <h2>Your order is on the way</h2>
        <p>Order ID: <strong>#{order.id}</strong></p>
        <p>Status: <strong>{order.orderStatus}</strong></p>
        <p>Confirmation Token: <strong>{order.confirmationToken}</strong></p>
        <p>Total: <strong>{formatCurrency(order.totalAmount)}</strong></p>
        
        <div className="confirmation-items">
          <h4>Order Items</h4>
          {order.items?.map((item, idx) => (
            <div key={idx} className="confirmation-item">
              <span>{item.productName}</span>
              <span>{item.quantity} x {formatCurrency(item.unitPrice)}</span>
            </div>
          ))}
        </div>
        
        <button className="primary-btn" onClick={() => onNavigate('menu')}>Continue shopping</button>
      </div>
    </section>
  );
}

export default ConfirmationPage;
