import React from 'react';

function Footer() {
  return (
    <footer className="site-footer">
      <div className="footer-grid">
        <div>
          <h3>Sweet Scoops</h3>
          <p>Premium scoops, warm service, and joyful flavours.</p>
        </div>
        <div>
          <h4>Quick links</h4>
          <ul>
            <li><a href="#menu">Menu</a></li>
            <li><a href="#contact">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
        </div>
        <div>
          <h4>Find us</h4>
          <p>24 Garden Avenue, Bengaluru</p>
          <p>+91 98765 43210</p>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
