import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '../store/store'
import React, { useEffect, useState } from 'react'
import LoginButton from '../components/LoginButton'
import UsernameTextField from '../components/UsernameTextField'
import TextFieldLabel from '../components/TextFieldLabel'
import FormTitle from '../components/FormTitle'
import PageLogo from '../components/PageLogo'
import ShopName from '../components/ShopName'
import RedBorderButton from '../components/RedBorderButton'
import Header from '../components/LoginHeader'
import LoginMain from '../components/LoginMain'
import Footer from '../components/LoginFooter'
import RegisterPage from './register'
import { ProductControllerService } from '../services/services/ProductControllerService'
import { GetModerateProductDTO } from '../services/models/GetModerateProductDTO'


export default function Home() {

  const user = useSelector((state: RootState) => state.user)
  const [isExpandedCategory, setIsExpandedCategory] = useState(false);
  const [isExpandedSort, setIsExpandedSort] = useState(false);

  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedSort, setSelectedSort] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [products, setProducts] = useState<GetModerateProductDTO[]>([]);

  const itemsPerPage = 9;

  const nextPage = () => {
    setCurrentPage(currentPage + 1);
  };

  const prevPage = () => {
    setCurrentPage(currentPage - 1);
  };

  const toggleExpandCategory = () => {
    setIsExpandedCategory(!isExpandedCategory);
  };
  const toggleExpandSort = () => {
    setIsExpandedSort(!isExpandedSort);
  };

  const handleCategoryChange = (event) => {
    setSelectedCategory(event.target.value);
  };
  const handleSortChange = (event) => {
    setSelectedSort(event.target.value);
  };

  const dispatch = useDispatch()

  useEffect(() => { }, [user]);

  useEffect(() => {
    ProductControllerService.getAllProducts().then(response => {
      setProducts(response.items || []);
    }).catch(error => {
      console.error('Error fetching products:', error);
    });
  }, []);

  return (
    <div style={{
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      width: '100vw',
      backgroundColor: '#F5F5F5',
      margin: 0,
      padding: 0,
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0
    }}>
      <div //Header
        style={{
          height: '10%',
          width: '100vw',
          paddingLeft: 42,
          paddingRight: 42,
          paddingTop: 25,
          paddingBottom: 25,
          display: "flex",
          flexDirection: 'row',
          alignItems: "center",
          justifyContent: "space-between",
        }}>
        <div //Logo and Shop name
          style={{ display: "flex", flexDirection: 'row', }}>
          <PageLogo></PageLogo>
          <ShopName></ShopName>
        </div>
        <div //Search bar
          style={{
            display: 'flex',
            alignItems: 'center',
            backgroundColor: '#EDEDF0',
            borderRadius: '100px',
            padding: '4px',
            width: '700px'
          }}
        >
          <input
            type="text"
            placeholder="Search..."
            style={{
              border: 'none',
              backgroundColor: 'transparent',
              padding: '8px',
              outline: 'none',
              fontSize: '16px',
              width: '100%',
            }}
          />
          <span
            style={{
              display: 'flex',
              alignItems: 'center',
              padding: '8px',
              color: '#888888',
            }}
          >
          </span>
        </div>
        <div //Red buttons
          style={{ display: "flex", flexDirection: 'row', }}>
          <RedBorderButton content="Login" />
        </div>

      </div>
      <div //Main 
        style={{ display: 'flex', flexDirection: 'row', height: '70vh', justifyContent: 'space-evenly' }} >
        <div style={{ display: 'flex', flexDirection: 'column', width: '10vw', alignItems: 'center' }}>
          <div>
            <div onClick={toggleExpandCategory}>
              <button>Categories</button>
            </div>
            {isExpandedCategory && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <label>
                  <input
                    type="radio"
                    value="Category 1"
                    checked={selectedCategory === 'Category 1'}
                    onChange={handleCategoryChange}
                  />
                  Category 1
                </label>
                <label>
                  <input
                    type="radio"
                    value="Category 2"
                    checked={selectedCategory === 'Category 2'}
                    onChange={handleCategoryChange}
                  />
                  Category 2
                </label>
                <label>
                  <input
                    type="radio"
                    value="Category 3"
                    checked={selectedCategory === 'Category 3'}
                    onChange={handleCategoryChange}
                  />
                  Category 3
                </label>
                <label>
                  <input
                    type="radio"
                    value="Category 4"
                    checked={selectedCategory === 'Category 4'}
                    onChange={handleCategoryChange}
                  />
                  Category 4
                </label>
                <label>
                  <input
                    type="radio"
                    value="Category 5"
                    checked={selectedCategory === 'Category 5'}
                    onChange={handleCategoryChange}
                  />
                  Category 5
                </label>
              </div>
            )}

          </div>
          <div>
            <div onClick={toggleExpandSort}>
              <button>Sort</button>
            </div>
            {isExpandedSort && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <label>
                  <input
                    type="radio"
                    value="Sort 1"
                    checked={selectedSort === 'Sort 1'}
                    onChange={handleSortChange}
                  />
                  Sort 1
                </label>
                <label>
                  <input
                    type="radio"
                    value="Sort 2"
                    checked={selectedSort === 'Sort 2'}
                    onChange={handleSortChange}
                  />
                  Sort 2
                </label>
                <label>
                  <input
                    type="radio"
                    value="Sort 3"
                    checked={selectedSort === 'Sort 3'}
                    onChange={handleSortChange}
                  />
                  Sort 3
                </label>
                <label>
                  <input
                    type="radio"
                    value="Sort 4"
                    checked={selectedSort === 'Sort 4'}
                    onChange={handleSortChange}
                  />
                  Sort 4
                </label>
                <label>
                  <input
                    type="radio"
                    value="Sort 5"
                    checked={selectedSort === 'Sort 5'}
                    onChange={handleSortChange}
                  />
                  Sort 5
                </label>
              </div>
            )}
          </div>
        </div>
        <div style={{ backgroundColor: 'white', width: '80%', height: '100%', borderRadius: '8px' }}>
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            {products.map(product => (
              <div
                key={product.id}
                style={{
                  width: '30%',
                  height: '30%',
                  border: '1px solid black',
                  margin: '10px',
                  display: 'flex',
                  flexDirection: 'column',
                  justifyContent: 'center',
                  alignItems: 'center',
                }}
              >
                <h4>{product.title}</h4>
                <p>{product.description}</p>
              </div>
            ))}

            <div //Page Indicators
              style={{ width: '100%', display: 'flex', flexDirection: 'row', justifyContent: 'space-evenly' }}>
              <button onClick={prevPage} disabled={currentPage === 1}>
                Previous Page
              </button>
              <p>{currentPage}</p>
              <button onClick={nextPage} disabled={currentPage === 3}>
                Next Page
              </button>
            </div>
          </div>
        </div>
      </div>
      <div //Footer
        style={{
          height: '20vh',
          width: '100vw',
          display: "flex",
          flexDirection: 'row',
          alignItems: "center",
          justifyContent: 'space-evenly',
          bottom: 0,
          left: 0,
          right: 0,
        }}>
        <div>
          <h1 style={{ fontFamily: "Red Hat Display", fontSize: "18px", fontWeight: "500", color: "#D12222" }}>RED SCOOTER</h1>
          <p style={{ margin: 0, padding: 0, color: '#535A56', fontFamily: 'Manrope', fontSize: '10px' }}>Motorra Elektrik</p>
          <p style={{ margin: 0, padding: 0, color: '#535A56', fontFamily: 'Manrope', fontSize: '10px' }}>Dedikuar pasionit dhe adrenalinës</p>
        </div>
        <div style={{ display: "flex", flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>About us</p>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', display: 'block', textAlign: 'left' }}>About</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Products</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Promotion</button>
        </div>
        <div style={{ display: "flex", flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>Explore</p>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', display: 'block', textAlign: 'left' }}>Career</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Privacy</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Terms & Conditions</button>
        </div>
        <div style={{ display: "flex", flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>Contact</p>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', display: 'block', textAlign: 'left' }}>(+355) 0696867903</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Rruga Hoxha Tasim,</button>
          <button style={{ backgroundColor: "transparent", border: 'none', cursor: 'pointer', marginTop: '5px', display: 'block', textAlign: 'left' }}>Tiranë, Albania</button>
        </div>
      </div>
    </div>

  )
}
