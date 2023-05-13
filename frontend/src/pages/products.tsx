import React from 'react'
import PageLogo from '../components/PageLogo'
import QuickSection from '../components/QuickSection'
import ProductsTable from '../components/ProductsTable'

const products = () => {
  return (
    <div style={{ padding: '1.5rem 1.5rem 0.75rem 1.5rem' }}>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <PageLogo />
        <strong style={{ margin: '2% 0% 0% 15%', fontSize: '2rem' }}>
          Products
        </strong>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <QuickSection />
        <ProductsTable />
      </div>
    </div>
  )
}

export default products
