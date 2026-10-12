import React, { useState } from 'react';
import { formatCurrency } from '../utils/helpers';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

function CheckoutPage({ cart, onNavigate, onOrderConfirmation, onClearCart }) {
  const [form, setForm] = useState({
    customerName: '',
    phone: '',
    deliveryAddress: '',
    orderNotes: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const subtotal = cart.reduce((total, item) => total + Number(item.price) * Number(item.quantity), 0);
  const delivery = 45;
  const total = subtotal + delivery;

  const updateField = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const placeOrder = async () => {
    if (!cart.length) {
      setError('Your cart is empty.');
      return;
    }

    if (!form.customerName || !form.phone || !form.deliveryAddress) {
      setError('Please fill in all required fields.');
      return;
    }

    setLoading(true);
    setError('');

    const payload = {
      customerName: form.customerName,
      phone: form.phone,
      deliveryAddress: form.deliveryAddress,
      orderNotes: form.orderNotes,
      items: cart.map((item) => ({ productId: item.id, quantity: item.quantity })),
    };

    try {
      const response = await fetch(`${API_BASE_URL}/orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      const result = await response.json();

      if (!response.ok) {
        throw new Error(result.message || 'Unable to place order.');
      }

      onOrderConfirmation(result);
      onClearCart();
      onNavigate('confirmation');
    } catch (err) {
      setError(err.message || 'An unexpected error occurred.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="section-shell checkout-page">
      <div className="section-heading">
        <span className="eyebrow">Checkout</span>
        <h2>Place your order</h2>
      </div>

      <div className="checkout-layout">
        <div className="checkout-form-card">
          <label>
            Full Name *
            <input value={form.customerName} onChange={(e) => updateField('customerName', e.target.value)} placeholder="Your full name" />
          </label>
          <label>
            Phone Number *
            <input value={form.phone} onChange={(e) => updateField('phone', e.target.value)} placeholder="10-digit phone number" />
          </label>
          <label>
            Delivery Address *
            <textarea value={form.deliveryAddress} onChange={(e) => updateField('deliveryAddress', e.target.value)} placeholder="Street address, city, postal code" />
          </label>
          <label>
            Order Notes (Optional)
            <textarea value={form.orderNotes} onChange={(e) => updateField('orderNotes', e.target.value)} placeholder="Any special requests or notes" />
          </label>
        </div>

        <aside className="checkout-summary">
          <h3>Order total</h3>
          <div className="summary-row"><span>Subtotal</span><strong>{formatCurrency(subtotal)}</strong></div>
          <div className="summary-row"><span>Delivery</span><strong>{formatCurrency(delivery)}</strong></div>
          <div className="summary-row total-row"><span>Total</span><strong>{formatCurrency(total)}</strong></div>
          {error && <div className="error-box state-box">{error}</div>}
          <button className="primary-btn full-width" disabled={loading} onClick={placeOrder}>
            {loading ? 'Placing order...' : 'Place Order'}
          </button>
        </aside>
      </div>
    </section>
  );
}

export default CheckoutPage;
