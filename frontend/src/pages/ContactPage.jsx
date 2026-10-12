import React, { useState } from 'react';

function ContactPage() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: '',
  });
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // In a real app, you'd send this to a backend
    console.log('Form submitted:', formData);
    setSubmitted(true);
    setFormData({ name: '', email: '', message: '' });
    setTimeout(() => setSubmitted(false), 3000);
  };

  return (
    <section className="section-shell contact-page">
      <div className="section-heading">
        <span className="eyebrow">Visit us</span>
        <h2>Contact Sweet Scoops</h2>
      </div>
      <div className="contact-layout">
        <div className="contact-card">
          <p><strong>Phone:</strong> +91 98765 43210</p>
          <p><strong>Email:</strong> hello@sweetscoops.in</p>
          <p><strong>Address:</strong> 24 Garden Avenue, Bengaluru, India</p>
          <p><strong>Hours:</strong> Mon-Sun, 11:00 AM – 11:30 PM</p>
          <div className="social-links">
            <a href="https://instagram.com" target="_blank" rel="noreferrer">Instagram</a>
            <a href="https://facebook.com" target="_blank" rel="noreferrer">Facebook</a>
            <a href="https://x.com" target="_blank" rel="noreferrer">X</a>
          </div>
        </div>
        <form className="contact-form" onSubmit={handleSubmit}>
          <label>
            Name
            <input type="text" name="name" placeholder="Your name" value={formData.name} onChange={handleChange} required />
          </label>
          <label>
            Email
            <input type="email" name="email" placeholder="you@example.com" value={formData.email} onChange={handleChange} required />
          </label>
          <label>
            Message
            <textarea name="message" placeholder="Tell us what you'd like to know" rows="5" value={formData.message} onChange={handleChange} required />
          </label>
          <button className="primary-btn" type="submit">Send message</button>
          {submitted && <p style={{ color: '#2d9d74', marginTop: '0.5rem' }}>Thank you! Your message has been sent.</p>}
        </form>
      </div>
    </section>
  );
}

export default ContactPage;
