import React from 'react'
import PageLogo from './PageLogo'

import List from '@mui/material/List'
import ListItem from '@mui/material/ListItem'
import ListItemButton from '@mui/material/ListItemButton'
import ListItemIcon from '@mui/material/ListItemIcon'
import ListItemText from '@mui/material/ListItemText'
import StarIcon from '@mui/icons-material/Star'
import HomeIcon from '@mui/icons-material/Home'
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart'
import InventoryIcon from '@mui/icons-material/Inventory'
import GroupIcon from '@mui/icons-material/Group'
import ReviewsIcon from '@mui/icons-material/Reviews'
import LocalOfferIcon from '@mui/icons-material/LocalOffer'
import QueryStatsIcon from '@mui/icons-material/QueryStats'
import SettingsIcon from '@mui/icons-material/Settings'

const QuickSection = () => {
  return (
    <List
      sx={{ width: '100%', maxWidth: 360, marginTop: '2%' }}
      aria-label='contacts'
    >
      <ListItem disablePadding>
        <ListItemButton href='/'>
          <ListItemIcon>
            <HomeIcon />
          </ListItemIcon>
          <ListItemText primary='Home' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/products'>
          <ListItemIcon>
            <InventoryIcon />
          </ListItemIcon>
          <ListItemText primary='Products' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/orders'>
          <ListItemIcon>
            <ShoppingCartIcon />
          </ListItemIcon>
          <ListItemText primary='Orders' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/users'>
          <ListItemIcon>
            <GroupIcon />
          </ListItemIcon>
          <ListItemText primary='Users' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/'>
          <ListItemIcon>
            <ReviewsIcon />
          </ListItemIcon>
          <ListItemText primary='Reviews' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/'>
          <ListItemIcon>
            <LocalOfferIcon />
          </ListItemIcon>
          <ListItemText primary='Discounts' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/'>
          <ListItemIcon>
            <QueryStatsIcon />
          </ListItemIcon>
          <ListItemText primary='Analytics' />
        </ListItemButton>
      </ListItem>

      <ListItem disablePadding>
        <ListItemButton href='/'>
          <ListItemIcon>
            <SettingsIcon />
          </ListItemIcon>
          <ListItemText primary='Settings' />
        </ListItemButton>
      </ListItem>
    </List>
  )
}

export default QuickSection
