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
import Link from 'next/link'
import Image from 'next/image'
import { getProducts } from '../components/ProductsData'

export default function Home() {
  const user = useSelector((state: RootState) => state.user)
  const [isExpandedCategory, setIsExpandedCategory] = useState(false)
  const [isExpandedSort, setIsExpandedSort] = useState(false)

  const [selectedCategory, setSelectedCategory] = useState('')
  const [selectedSort, setSelectedSort] = useState('')
  const [currentPage, setCurrentPage] = useState(1)
  const [products, setProducts] = useState<GetModerateProductDTO[]>([])

  const itemsPerPage = 9
  const rowsPerPage = 3

  const nextPage = () => {
    setCurrentPage(currentPage + 1)
  }

  const prevPage = () => {
    setCurrentPage(currentPage - 1)
  }

  const toggleExpandCategory = () => {
    setIsExpandedCategory(!isExpandedCategory)
  }
  const toggleExpandSort = () => {
    setIsExpandedSort(!isExpandedSort)
  }

  const handleCategoryChange = event => {
    setSelectedCategory(event.target.value)
  }
  const handleSortChange = event => {
    setSelectedSort(event.target.value)
  }

  const dispatch = useDispatch()

  useEffect(() => {}, [user])

  useEffect(() => {
    // fetch data
    const dataFetch = async () => {
      const res = await getProducts()
      // set state when the data received
      setProducts(res)
    }

    dataFetch()
  }, [])

  return (
    <div
      style={{
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
        bottom: 0,
      }}>
      <div // Header
        style={{
          height: '10%',
          paddingLeft: 42,
          paddingRight: 42,
          paddingTop: 25,
          paddingBottom: 25,
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'space-between',
        }}>
        <div // Logo and Shop name
          style={{
            display: 'flex',
            flexDirection: 'row',
            alignItems: 'center',
          }}>
          <PageLogo></PageLogo>
          <ShopName></ShopName>
        </div>
        <div // Search bar and Red button wrapper
          style={{
            display: 'flex',
            alignItems: 'center',
            width: '70%',
            justifyContent: 'space-between',
          }}>
          <div // Search bar
            style={{
              width: '550px',
              display: 'flex',
              alignItems: 'center',
              backgroundColor: '#EDEDF0',
              borderRadius: '100px',
              padding: '4px',
              marginRight: '10px',
            }}>
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
              }}></span>
          </div>
          <div // Red button
            style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'flex-start',
              marginLeft: '10px',
            }}>
            <div>
              <Link
                style={{ textDecoration: 'none', color: 'inherit' }}
                href={'./login'}>
                <button
                  type="button"
                  style={{
                    width: '104px',
                    height: '40px',
                    borderRadius: '4px',
                    borderWidth: '1px',
                    borderColor: '#D12222',
                    borderStyle: 'solid',
                    backgroundColor: 'transparent',
                    color: '#D12222',
                    fontFamily: 'Quicksand',
                    fontWeight: '500',
                    fontSize: '20px',
                    marginRight: '15px',
                    cursor: 'pointer',
                  }}>
                  Login
                </button>
              </Link>

              <Link
                style={{ textDecoration: 'none', color: 'inherit' }}
                href={'./register'}>
                <button
                  type="button"
                  style={{
                    width: '104px',
                    height: '40px',
                    borderRadius: '4px',
                    borderWidth: '1px',
                    borderColor: '#D12222',
                    borderStyle: 'solid',
                    backgroundColor: 'transparent',
                    color: '#D12222',
                    fontFamily: 'Quicksand',
                    fontWeight: '500',
                    fontSize: '20px',
                    marginRight: '15px',
                    cursor: 'pointer',
                  }}>
                  Register
                </button>
              </Link>
            </div>
          </div>
        </div>
      </div>

      <div //Main
        style={{
          display: 'flex',
          flexDirection: 'row',
          height: '70vh',
          justifyContent: 'space-evenly',
        }}>
        <div
          style={{
            display: 'flex',
            flexDirection: 'column',
            width: '10vw',
            alignItems: 'center',
          }}>
          <div style={{ marginBottom: '20px' }}>
            <div
              onClick={toggleExpandCategory}
              style={{
                marginBottom: '10px',
                display: 'flex',
                alignItems: 'center',
                cursor: 'pointer',
              }}>
              <button
                style={{
                  backgroundColor: '#D12222',
                  color: 'white',
                  borderRadius: '4px',
                  border: 'none',
                  padding: '8px 12px',
                  fontSize: '16px',
                  fontWeight: 'bold',
                  cursor: 'pointer',
                }}>
                Categories
              </button>
              <span
                style={{
                  marginLeft: '10px',
                  fontSize: '14px',
                  color: '#D12222',
                }}>
                {isExpandedCategory ? '▲' : '▼'}
              </span>
            </div>
            {isExpandedCategory && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Category 1"
                    checked={selectedCategory === 'Category 1'}
                    onChange={handleCategoryChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedCategory === 'Category 1' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedCategory === 'Category 1'
                            ? '#D12222'
                            : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Category 1
                  </span>
                </label>

                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Category 2"
                    checked={selectedCategory === 'Category 2'}
                    onChange={handleCategoryChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedCategory === 'Category 2' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedCategory === 'Category 2'
                            ? '#D12222'
                            : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Category 2
                  </span>
                </label>

                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Category 3"
                    checked={selectedCategory === 'Category 3'}
                    onChange={handleCategoryChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedCategory === 'Category 3' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedCategory === 'Category 3'
                            ? '#D12222'
                            : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Category 3
                  </span>
                </label>

                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Category 4"
                    checked={selectedCategory === 'Category 4'}
                    onChange={handleCategoryChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedCategory === 'Category 4' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedCategory === 'Category 4'
                            ? '#D12222'
                            : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Category 4
                  </span>
                </label>

                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Category 5"
                    checked={selectedCategory === 'Category 5'}
                    onChange={handleCategoryChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedCategory === 'Category 5' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedCategory === 'Category 5'
                            ? '#D12222'
                            : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Category 5
                  </span>
                </label>
              </div>
            )}
          </div>

          <div>
            <div
              onClick={toggleExpandSort}
              style={{
                marginBottom: '10px',
                display: 'flex',
                alignItems: 'center',
                cursor: 'pointer',
              }}>
              <button
                style={{
                  backgroundColor: '#D12222',
                  color: 'white',
                  borderRadius: '4px',
                  border: 'none',
                  padding: '8px 12px',
                  fontSize: '16px',
                  fontWeight: 'bold',
                  cursor: 'pointer',
                }}>
                Sort
              </button>
              <span
                style={{
                  marginLeft: '10px',
                  fontSize: '14px',
                  color: '#D12222',
                }}>
                {isExpandedSort ? '▲' : '▼'}
              </span>
            </div>
            {isExpandedSort && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Sort 1"
                    checked={selectedSort === 'Sort 1'}
                    onChange={handleSortChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedSort === 'Sort 1' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedSort === 'Sort 1' ? '#D12222' : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Sort 1
                  </span>
                </label>

                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Sort 2"
                    checked={selectedSort === 'Sort 2'}
                    onChange={handleSortChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedSort === 'Sort 2' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedSort === 'Sort 2' ? '#D12222' : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Sort 2
                  </span>
                </label>
                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Sort 3"
                    checked={selectedSort === 'Sort 3'}
                    onChange={handleSortChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedSort === 'Sort 3' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedSort === 'Sort 3' ? '#D12222' : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Sort 3
                  </span>
                </label>
                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Sort 4"
                    checked={selectedSort === 'Sort 4'}
                    onChange={handleSortChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedSort === 'Sort 4' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedSort === 'Sort 4' ? '#D12222' : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Sort 4
                  </span>
                </label>
                <label
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginBottom: '10px',
                  }}>
                  <input
                    type="radio"
                    value="Sort 5"
                    checked={selectedSort === 'Sort 5'}
                    onChange={handleSortChange}
                    style={{ position: 'absolute', opacity: 0 }}
                  />
                  <span
                    style={{
                      position: 'relative',
                      display: 'inline-block',
                      width: '16px',
                      height: '16px',
                      borderRadius: '50%',
                      border: '2px solid #D12222',
                      transition: 'background-color 0.2s ease',
                    }}>
                    <span
                      style={{
                        content: '""',
                        position: 'absolute',
                        top: '50%',
                        left: '50%',
                        transform: `translate(-50%, -50%) scale(${
                          selectedSort === 'Sort 5' ? '1' : '0'
                        })`,
                        width: '8px',
                        height: '8px',
                        borderRadius: '50%',
                        backgroundColor:
                          selectedSort === 'Sort 5' ? '#D12222' : 'transparent',
                        transition: 'transform 0.2s ease',
                      }}></span>
                  </span>
                  <span
                    style={{
                      marginLeft: '5px',
                      fontFamily: 'Arial',
                      fontSize: '14px',
                      color: '#333',
                    }}>
                    Sort 5
                  </span>
                </label>
              </div>
            )}
          </div>
        </div>
        <div
          style={{
            backgroundColor: 'white',
            width: '80%',
            height: '100%',
            borderRadius: '8px',
          }}>
          <div
            style={{
              display: 'flex',
              flexDirection: 'column',
              height: '70vh',
              justifyContent: 'space-between',
            }}>
            <div
              style={{
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'left',
                maxHeight: 'calc(70vh - 40px)',
              }}>
              {products
                .slice(
                  (currentPage - 1) * itemsPerPage,
                  currentPage * itemsPerPage,
                )
                .map(product => (
                  <div
                    key={product.id}
                    style={{
                      width: '30%',
                      height: '30%',
                      border: '1px solid #D12222',
                      padding: '15px',
                      margin: '5px',
                      display: 'flex',
                      flexDirection: 'column',
                      justifyContent: 'center',
                      alignItems: 'center',
                    }}>
                    <Image
                      src={product?.thumbnail || ''}
                      alt="Product Picture"
                      width={40}
                      height={40}
                    />
                    <h4>{product.title}</h4>
                    <p>
                      ALL {product.price} Range: {product.range} km
                    </p>
                  </div>
                ))}
            </div>
            <div
              style={{
                width: '100%',
                display: 'flex',
                flexDirection: 'row',
                justifyContent: 'space-evenly',
                paddingBottom: '0px',
              }}>
              <button
                onClick={prevPage}
                disabled={currentPage === 1}
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  cursor: 'pointer',
                  display: 'flex',
                  alignItems: 'center',
                  fontSize: '16px',
                  color: '#D12222',
                }}>
                &#9668;&nbsp;Prev
              </button>
              <p>
                {currentPage} / {Math.ceil(products.length / itemsPerPage)}
              </p>
              <button
                onClick={nextPage}
                disabled={
                  currentPage === Math.ceil(products.length / itemsPerPage)
                }
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  cursor: 'pointer',
                  display: 'flex',
                  alignItems: 'center',
                  fontSize: '16px',
                  color: '#D12222',
                }}>
                Next &#9658;
              </button>
            </div>
          </div>
        </div>
      </div>
      <div //Footer
        style={{
          height: '20vh',
          width: '100vw',
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'space-evenly',
          bottom: 0,
          left: 0,
          right: 0,
        }}>
        <div>
          <h1
            style={{
              fontFamily: 'Red Hat Display',
              fontSize: '18px',
              fontWeight: '500',
              color: '#D12222',
            }}>
            RED SCOOTER
          </h1>
          <p
            style={{
              margin: 0,
              padding: 0,
              color: '#535A56',
              fontFamily: 'Manrope',
              fontSize: '10px',
            }}>
            Motorra Elektrik
          </p>
          <p
            style={{
              margin: 0,
              padding: 0,
              color: '#535A56',
              fontFamily: 'Manrope',
              fontSize: '10px',
            }}>
            Dedikuar pasionit dhe adrenalinës
          </p>
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>About us</p>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              display: 'block',
              textAlign: 'left',
            }}>
            About
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Products
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Promotion
          </button>
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>Explore</p>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              display: 'block',
              textAlign: 'left',
            }}>
            Career
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Privacy
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              cursor: 'pointer',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Terms & Conditions
          </button>
        </div>
        <div style={{ display: 'flex', flexDirection: 'column' }}>
          <p style={{ display: 'block', marginLeft: '4px' }}>Contact</p>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              display: 'block',
              textAlign: 'left',
            }}>
            (+355) 0696867903
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Rruga Hoxha Tasim,
          </button>
          <button
            style={{
              backgroundColor: 'transparent',
              border: 'none',
              marginTop: '5px',
              display: 'block',
              textAlign: 'left',
            }}>
            Tiranë, Albania
          </button>
        </div>
      </div>
    </div>
  )
}
