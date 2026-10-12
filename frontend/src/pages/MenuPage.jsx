import React, { useMemo, useState } from 'react';
import ProductCard from '../components/ProductCard';
import ProductModal from '../components/ProductModal';

function MenuPage({ products, loading, error, onAddToCart }) {
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState('All');
  const [sortOption, setSortOption] = useState('featured');
  const [selectedProduct, setSelectedProduct] = useState(null);

  const categories = useMemo(() => {
    const values = products.map((product) => product.category).filter(Boolean);
    return ['All', ...new Set(values)];
  }, [products]);

  const filteredProducts = useMemo(() => {
    const term = searchTerm.toLowerCase().trim();
    const result = products.filter((product) => {
      const matchesSearch = !term || product.name.toLowerCase().includes(term);
      const matchesCategory = categoryFilter === 'All' || product.category === categoryFilter;
      return matchesSearch && matchesCategory;
    });

    if (sortOption === 'low-to-high') {
      return result.sort((a, b) => a.price - b.price);
    }
    if (sortOption === 'high-to-low') {
      return result.sort((a, b) => b.price - a.price);
    }
    return result;
  }, [products, searchTerm, categoryFilter, sortOption]);

  return (
    <section className="menu-page section-shell">
      <div className="section-heading menu-head">
        <div>
          <span className="eyebrow">Our menu</span>
          <h2>Freshly frozen favourites</h2>
        </div>
      </div>

      <div className="filters-row">
        <input
          type="text"
          placeholder="Search by flavour"
          value={searchTerm}
          onChange={(event) => setSearchTerm(event.target.value)}
        />
        <select value={categoryFilter} onChange={(event) => setCategoryFilter(event.target.value)}>
          {categories.map((category) => (
            <option key={category} value={category}>{category}</option>
          ))}
        </select>
        <select value={sortOption} onChange={(event) => setSortOption(event.target.value)}>
          <option value="featured">Featured</option>
          <option value="low-to-high">Price: Low to High</option>
          <option value="high-to-low">Price: High to Low</option>
        </select>
      </div>

      {loading ? (
        <div className="state-box">Loading menu...</div>
      ) : error ? (
        <div className="state-box error-box">{error}</div>
      ) : filteredProducts.length === 0 ? (
        <div className="state-box">No flavours match your search.</div>
      ) : (
        <div className="product-grid menu-grid">
          {filteredProducts.map((product) => (
            <div key={product.id} onClick={() => setSelectedProduct(product)} style={{ cursor: 'pointer' }}>
              <ProductCard product={product} onAddToCart={onAddToCart} />
            </div>
          ))}
        </div>
      )}

      <ProductModal product={selectedProduct} onClose={() => setSelectedProduct(null)} onAddToCart={onAddToCart} />
    </section>
  );
}

export default MenuPage;
