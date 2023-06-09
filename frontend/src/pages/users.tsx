import React from 'react'
import PageLogo from '../components/PageLogo'
import QuickSection from '../components/QuickSection'
import UsersTable from '../components/UsersTable'

const users = () => {
  return (
    <div style={{ padding: '1.5rem 1.5rem 0.75rem 1.5rem' }}>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <PageLogo />
        <strong style={{ margin: '2% 0% 0% 15%', fontSize: '2rem' }}>
          Users
        </strong>
      </div>
      <div style={{ display: 'flex', flexDirection: 'row' }}>
        <QuickSection />
        <UsersTable />
      </div>
    </div>
  )
}

export default users
