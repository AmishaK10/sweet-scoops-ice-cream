import React from 'react';

function Header({ cart, onNavigate }) {
  const cartCount = cart.reduce((sum, item) => sum + item.quantity, 0);

  return (
    <header className="topbar">
      <div className="brand-wrap" onClick={() => onNavigate('home')} style={{ cursor: 'pointer' }}>
        <div className="brand-mark">S</div>
        <div>
          <div className="brand-name">Sweet Scoops</div>
          <div className="brand-sub">Ice Cream Salon</div>
        </div>
      </div>
      <nav className="nav-links">
        <button onClick={() => onNavigate('home')}>Home</button>
        <button onClick={() => onNavigate('menu')}>Menu</button>
        <button onClick={() => onNavigate('cart')}>Cart</button>
        <button onClick={() => onNavigate('contact')}>Contact</button>
      </nav>
      <button className="primary-btn cart-pill" onClick={() => onNavigate('checkout')}>
        Cart ({cartCount})
      </button>
    </header>
  );
}

export default Header;
