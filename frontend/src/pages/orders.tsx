import React from 'react'
import PageLogo from '../components/PageLogo'
import QuickSection from '../components/QuickSection'
import OrdersTable from '../components/OrdersTable'

const orders = () => {
  return (
    <div style={{ padding: '1.5rem 1.5rem 0.75rem 1.5rem' }}>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <PageLogo />
        <strong style={{ margin: '2% 0% 0% 15%', fontSize: '2rem' }}>
          Orders
        </strong>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <QuickSection />
        <OrdersTable />
      </div>
    </div>
  )
}

export default orders
