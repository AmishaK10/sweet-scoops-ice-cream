import { useEffect, useMemo, useState } from 'react';
import Header from './components/Header';
import Footer from './components/Footer';
import HomePage from './pages/HomePage';
import MenuPage from './pages/MenuPage';
import CartPage from './pages/CartPage';
import CheckoutPage from './pages/CheckoutPage';
import ConfirmationPage from './pages/ConfirmationPage';
import ContactPage from './pages/ContactPage';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

const defaultCart = () => {
  try {
    const raw = localStorage.getItem('sweet-scoops-cart');
    return raw ? JSON.parse(raw) : [];
  } catch {
    return [];
  }
};

function App() {
  const [route, setRoute] = useState('home');
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [cart, setCart] = useState(defaultCart);
  const [orderConfirmation, setOrderConfirmation] = useState(null);

  useEffect(() => {
    const onHashChange = () => {
      const hash = window.location.hash.replace('#', '') || 'home';
      setRoute(hash);
    };
    onHashChange();
    window.addEventListener('hashchange', onHashChange);
    return () => window.removeEventListener('hashchange', onHashChange);
  }, []);

  useEffect(() => {
    localStorage.setItem('sweet-scoops-cart', JSON.stringify(cart));
  }, [cart]);

  useEffect(() => {
    async function loadProducts() {
      try {
        setLoading(true);
        const response = await fetch(`${API_BASE_URL}/products`);
        if (!response.ok) {
          throw new Error('Failed to load menu');
        }
        const data = await response.json();
        setProducts(Array.isArray(data) ? data : []);
      } catch (loadError) {
        setError(loadError.message || 'Unable to load products');
      } finally {
        setLoading(false);
      }
    }
    loadProducts();
  }, []);

  const navigateTo = (page) => {
    setRoute(page);
    window.location.hash = `#${page}`;
  };

  const addToCart = (product) => {
    setCart((current) => {
      const item = current.find((entry) => entry.id === product.id);
      if (item) {
        return current.map((entry) =>
          entry.id === product.id ? { ...entry, quantity: entry.quantity + 1 } : entry
        );
      }
      return [...current, { ...product, quantity: 1 }];
    });
    navigateTo('cart');
  };

  const changeQuantity = (productId, change) => {
    setCart((current) =>
      current
        .map((item) => (item.id === productId ? { ...item, quantity: item.quantity + change } : item))
        .filter((item) => item.quantity > 0)
    );
  };

  const removeItem = (productId) => {
    setCart((current) => current.filter((item) => item.id !== productId));
  };

  const clearCart = () => {
    setCart([]);
  };

  const renderContent = () => {
    switch (route) {
      case 'menu':
        return <MenuPage products={products} loading={loading} error={error} onAddToCart={addToCart} />;
      case 'cart':
        return <CartPage cart={cart} onChangeQuantity={changeQuantity} onRemoveItem={removeItem} onNavigate={navigateTo} />;
      case 'checkout':
        return <CheckoutPage cart={cart} onNavigate={navigateTo} onOrderConfirmation={setOrderConfirmation} onClearCart={clearCart} />;
      case 'confirmation':
        return <ConfirmationPage order={orderConfirmation} onNavigate={navigateTo} />;
      case 'contact':
        return <ContactPage />;
      default:
        return <HomePage products={products.slice(0, 3)} onNavigate={navigateTo} onAddToCart={addToCart} />;
    }
  };

  return (
    <>
      <Header cart={cart} onNavigate={navigateTo} />
      <main>{renderContent()}</main>
      <Footer />
    </>
  );
}

export default App;
