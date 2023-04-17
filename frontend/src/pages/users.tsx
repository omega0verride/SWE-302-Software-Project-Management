import React from 'react'
import PageLogo from '../components/PageLogo'
import WidgetWrapper from '../components/WidgetWrapper'
import QuickSection from '../components/QuickSection'
import Table from '../components/Table'
import Button from '@mui/material/Button';
import AddIcon from '@mui/icons-material/Add';

const users = () => {
  return (
    <div style={{ padding: "1.5rem 1.5rem 0.75rem 1.5rem",
     }} >
        <div style={{ display: 'flex', flexDirection: 'row'}}>
          <PageLogo />
            <strong style={{ margin: '2% 0% 0% 15%', fontSize: '2rem'}} >Users</strong>
        </div>         
        <div style={{ display: 'flex', flexDirection: 'row'}}>
            <QuickSection />
            <Table />
        </div>  
    </div>
  )
}

export default users