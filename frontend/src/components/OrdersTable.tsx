import React, { useState, Fragment, useEffect } from 'react'
import {
  Typography,
  Paper,
  TableRow,
  TableHead,
  TableContainer,
  TableCell,
  TableBody,
  Table,
  IconButton,
  Collapse,
  Box,
  TablePagination,
  MenuItem,
  FormControl,
  InputLabel,
  Button,
} from '@mui/material'
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown'
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp'
import FirstPageIcon from '@mui/icons-material/FirstPage'
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft'
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight'
import LastPageIcon from '@mui/icons-material/LastPage'
import { useTheme } from '@mui/material/styles'
import {
  getOrders,
  orderStatusArray,
  updateOrder,
  orderStatusToBeDelivered,
} from './OrdersData'
import Select, { SelectChangeEvent } from '@mui/material/Select'

type clientsDetails = {
  clientName: string
  clientSurname: string
  clientPhoneNumber: string
  clientEmail: string
  clientAddressLine1: string
  clientNotes: string
  paymentOption: string
}

type ordersDetails = {
  createdAt: string
  updatedAt: string
  id: number
  productId: number
  quantity: number
  title: string
  description: string
  price: number
  discount: number
  used: boolean
}

interface TablePaginationActionsProps {
  count: number
  page: number
  rowsPerPage: number
  onPageChange: (
    event: React.MouseEvent<HTMLButtonElement>,
    newPage: number,
  ) => void
}

function TablePaginationActions(props: TablePaginationActionsProps) {
  const theme = useTheme()
  const { count, page, rowsPerPage, onPageChange } = props

  const handleFirstPageButtonClick = (
    event: React.MouseEvent<HTMLButtonElement>,
  ) => {
    onPageChange(event, 0)
  }

  const handleBackButtonClick = (
    event: React.MouseEvent<HTMLButtonElement>,
  ) => {
    onPageChange(event, page - 1)
  }

  const handleNextButtonClick = (
    event: React.MouseEvent<HTMLButtonElement>,
  ) => {
    onPageChange(event, page + 1)
  }

  const handleLastPageButtonClick = (
    event: React.MouseEvent<HTMLButtonElement>,
  ) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1))
  }

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page">
        {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton
        onClick={handleBackButtonClick}
        disabled={page === 0}
        aria-label="previous page">
        {theme.direction === 'rtl' ? (
          <KeyboardArrowRight />
        ) : (
          <KeyboardArrowLeft />
        )}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page">
        {theme.direction === 'rtl' ? (
          <KeyboardArrowLeft />
        ) : (
          <KeyboardArrowRight />
        )}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page">
        {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  )
}

const Row = (props: {
  orderBilling: clientsDetails
  orderStatus: string
  orderLines: Array<ordersDetails>
  id: number
  timestamp: number
  setClientsData
}) => {
  const {
    orderBilling,
    orderStatus,
    orderLines,
    id,
    timestamp,
    setClientsData,
  } = props
  const [open, setOpen] = useState(false)
  const date = new Date(timestamp)

  const orderStatusToBeDisplayd = Object.keys(orderStatusToBeDelivered).find(
    key => orderStatusToBeDelivered[key] === orderStatus,
  )

  const event = date.toLocaleDateString('en-GB')
  const [orderStatusState, setOrderStatuState] = useState('')

  const handleChange = (event: SelectChangeEvent) => {
    if (event.target.value !== orderStatusToBeDisplayd) {
      console.log(event.target.value, orderStatusToBeDisplayd)
      setOrderStatuState(event.target.value as string)
    } else {
      setOrderStatuState('')
    }
  }

  const updateOrderStatus = async () => {
    const res = await updateOrder(id, orderStatusState)
    console.log(res)
    if (res?.details?.pk_value) {
      const res = await getOrders()
      setClientsData(res)
      setOrderStatuState('')
    }
  }

  return (
    <Fragment>
      <TableRow
        sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: '#FFBF00' }}>
        <TableCell align="center">
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}>
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row" align="center">
          {id || 'Empty'}
        </TableCell>
        <TableCell align="center">
          <FormControl sx={{ width: '10rem', m: 1 }}>
            <InputLabel id="demo-simple-select-label">Status</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={orderStatusState || orderStatusToBeDisplayd}
              label="Status"
              onChange={handleChange}>
              {orderStatusArray.map((el, index) => (
                <MenuItem key={index} value={el}>
                  {el}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </TableCell>
        <TableCell align="center">
          {orderStatusState ? (
            <Button
              variant="outlined"
              sx={{ padding: '0.9rem', color: 'black' }}
              onClick={() => updateOrderStatus()}>
              Save Status
            </Button>
          ) : (
            <Button disabled sx={{ padding: '0.9rem' }} variant="outlined">
              Save Status
            </Button>
          )}
        </TableCell>
        <TableCell align="center">{event ?? 'Empty'}</TableCell>
        <TableCell align="center">
          {orderBilling.clientName ?? 'Empty'}
        </TableCell>
        <TableCell align="center">
          {orderBilling.clientSurname ?? 'Empty'}
        </TableCell>
        <TableCell align="center">
          {orderBilling.clientPhoneNumber ?? 'Empty'}
        </TableCell>
        <TableCell align="center">{orderBilling.clientEmail}</TableCell>
        <TableCell align="center">{orderBilling.clientAddressLine1}</TableCell>
        <TableCell align="center">{orderBilling.clientNotes}</TableCell>
        <TableCell align="center">{orderBilling?.paymentOption}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={12}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Order Lines
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Date
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Id
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Product Id
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Quantity
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Title
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Description
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Price
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Discount
                    </TableCell>
                    <TableCell sx={{ fontWeight: 'bold' }} align="center">
                      Used
                    </TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {orderLines.map(historyRow => (
                    <TableRow key={historyRow.createdAt}>
                      <TableCell align="center" component="th" scope="row">
                        {historyRow.createdAt || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.id || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.productId || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.quantity || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.title || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.description || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.price || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.discount || 'Empty'}
                      </TableCell>
                      <TableCell align="center">
                        {historyRow.used || 'Empty'}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </Fragment>
  )
}

const CollapsibleTable = () => {
  const [clientsData, setClientsData] = useState<any[]>([])
  useEffect(() => {
    // fetch data
    const dataFetch = async () => {
      const res = await getOrders()
      // set state when the data received
      // console.log(res[0]?.orderBilling)
      setClientsData(res)
    }
    dataFetch()
  }, [])

  const [page, setPage] = useState(0)
  const [rowsPerPage, setRowsPerPage] = useState(5)

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - clientsData?.length) : 0

  const handleChangePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => {
    setPage(newPage)
  }

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10))
    setPage(0)
  }

  return (
    <Paper sx={{ maxWidth: '100%', maxHeight: '100%' }}>
      <TableContainer>
        <Table aria-label="collapsible table">
          <TableHead sx={{ backgroundColor: 'gray' }}>
            <TableRow>
              <TableCell />
              <TableCell sx={{ color: 'white' }} align="center">
                Order Id
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Order Status
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Save Order Status
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Date
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                First Name
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Last Name
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Tel
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Email
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Address
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Notes
              </TableCell>
              <TableCell sx={{ color: 'white' }} align="center">
                Payment
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {clientsData?.length > 0 &&
              clientsData?.map((row, index) => (
                <Row
                  key={index}
                  orderStatus={clientsData[index]?.orderStatus}
                  orderLines={clientsData[index]?.orderLines}
                  orderBilling={clientsData[index]?.orderBilling}
                  id={clientsData[index]?.id}
                  timestamp={clientsData[index]?.createdAt}
                  setClientsData={setClientsData}
                />
              ))}
          </TableBody>
        </Table>
      </TableContainer>
      <TablePagination
        rowsPerPageOptions={[1, 5, 10, 25, 100]}
        component="div"
        count={clientsData?.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onPageChange={handleChangePage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </Paper>
  )
}

export default CollapsibleTable
