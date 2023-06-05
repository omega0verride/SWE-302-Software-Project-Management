import React from 'react'
import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import HomeIcon from '@mui/icons-material/Home'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart'
import InventoryIcon from '@mui/icons-material/Inventory'
import GroupIcon from '@mui/icons-material/Group'
import LogoutIcon from '@mui/icons-material/Logout'
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts'
import { handleLogOut } from '../store/localStorage/manageStorage'

const QuickSection = () => {
  return (
    <List
      sx={{ width: '50%', maxWidth: '20rem', marginTop: '2%' }}
      aria-label="contacts">
      <ListItem disablePadding>
        <ListItemButton href="/">
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary="Home" />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href="/users">
          <ListItemIcon>
            <GroupIcon />
          </ListItemIcon>
          <ListItemText primary="Users" />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href="/products">
          <ListItemIcon>
            <InventoryIcon />
          </ListItemIcon>
          <ListItemText primary="Products" />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href="/orders">
          <ListItemIcon>
            <ShoppingCartIcon />
          </ListItemIcon>
          <ListItemText primary="Orders" />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href="/">
          <ListItemIcon>
            <ManageAccountsIcon />
          </ListItemIcon>
          <ListItemText primary="Profile" />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding onClick={e => handleLogOut()}>
        <ListItemButton href="/login">
          <ListItemIcon>
            <LogoutIcon />
          </ListItemIcon>
          <ListItemText primary="Log out" />
        </ListItemButton>
      </ListItem>
    </List>
  )
}

export default QuickSection
