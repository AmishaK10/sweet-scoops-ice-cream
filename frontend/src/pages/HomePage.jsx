import React from 'react';
import ProductCard from '../components/ProductCard';

function HomePage({ products, onNavigate, onAddToCart }) {
  return (
    <>
      <section className="hero section-shell">
        <div className="hero-copy">
          <span className="eyebrow">Freshly scooped daily</span>
          <h1>Happiness Served One Scoop at a Time</h1>
          <p>
            Sweet Scoops Ice Cream Salon brings playful flavours, premium ingredients, and joyful moments to every table.
          </p>
          <div className="button-row">
            <button className="primary-btn" onClick={() => onNavigate('menu')}>Explore Flavours</button>
            <button className="secondary-btn" onClick={() => onNavigate('cart')}>Order Now</button>
          </div>
        </div>
        <div className="hero-visual">
          <img src="https://images.unsplash.com/photo-1563805042-7684c019e1cb?auto=format&fit=crop&w=900&q=80" alt="Ice cream scoop" />
        </div>
      </section>

      <section className="featured section-shell">
        <div className="section-heading">
          <span className="eyebrow">Top picks</span>
          <h2>Featured Ice Creams</h2>
        </div>
        <div className="product-grid">
          {products.map((product) => (
            <ProductCard key={product.id} product={product} onAddToCart={onAddToCart} />
          ))}
        </div>
      </section>

      <section className="offers section-shell">
        <div className="section-heading">
          <span className="eyebrow">Seasonal specials</span>
          <h2>Best Selling Flavours</h2>
        </div>
        <div className="offer-grid">
          <div className="offer-item">
            <span className="offer-tag">Weekend Deal</span>
            <h3>Two scoops + waffle</h3>
            <p>Enjoy a combo from ₹299.</p>
          </div>
          <div className="offer-item">
            <span className="offer-tag">Family Treat</span>
            <h3>Family Pack</h3>
            <p>Bring home our bestselling tub for sharing.</p>
          </div>
          <div className="offer-item">
            <span className="offer-tag">Petite Pick</span>
            <h3>Happy Hour</h3>
            <p>Fresh shakes and sundaes at a sweet discount.</p>
          </div>
        </div>
      </section>

      <section className="testimonials section-shell">
        <div className="section-heading">
          <span className="eyebrow">Our happy customers</span>
          <h2>Sweet Reviews</h2>
        </div>
        <div className="testimonial-grid">
          <blockquote>"The chocolate brownie sundae is pure heaven."</blockquote>
          <blockquote>"The service is warm, the flavours are fresh, and the vibe is lovely."</blockquote>
          <blockquote>"Perfect for evenings with family and friends."</blockquote>
        </div>
      </section>

      <section className="about section-shell" id="about">
        <div className="about-copy">
          <span className="eyebrow">About us</span>
          <h2>Crafted for memorable scoops</h2>
          <p>
            We blend premium dairy, seasonal fruits, and playful creativity to serve each scoop with a smile.
          </p>
          <div className="hours-card">
            <h3>Opening hours</h3>
            <p>Mon–Thu: 11:00 AM – 10:30 PM</p>
            <p>Fri–Sun: 11:00 AM – 11:30 PM</p>
          </div>
        </div>
      </section>
    </>
  );
}

export default HomePage;
